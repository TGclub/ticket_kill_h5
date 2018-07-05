package com.wizz.seckill.Controller;


import com.wizz.seckill.Mapper.actAttrMapper;
import com.wizz.seckill.Model.reqRes;
import com.wizz.seckill.Mapper.reqInfoMapper;
import com.wizz.seckill.Service.Impl.RedisService;
import com.wizz.seckill.Util.ip.IpUtil;
import com.wizz.seckill.Util.jwt.JwtToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

@CrossOrigin(origins = "*")
@RestController
class seckillController{

    private static Logger logger = Logger.getLogger(seckillController.class.getName());

    @Autowired
    private actAttrMapper actamap;

    @Autowired
    private reqInfoMapper infomap;

    @Autowired
    private RedisService cacheService;

    private Long ipLimit = new Long(100);
    private String filePre = "/var/lib/mysql-files/backup";
    private Integer version = 0;
    private Pattern pattern = Pattern.compile("^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$");
    @RequestMapping(value = "/tickets" , method = RequestMethod.POST)
    reqRes resHandler(HttpServletRequest request, @RequestBody Map<String, Object> params){

        String phoneNum = (String)params.get("phoneNum");
        String stuId = (String)params.get("stuId");
        String stuName = (String)params.get("stuName");

        if(StringUtils.isEmpty(phoneNum)|| StringUtils.isEmpty(stuId)
                || StringUtils.isEmpty(stuName)){
            return new reqRes(1,"参数不能为空");
        }

        if(!pattern.matcher(phoneNum).matches()){
            return new reqRes(1, "手机号码格式错误");
        }

        String ip = IpUtil.getIpAddr(request);

        logger.log(Level.INFO,"收到请求:" + "  : phoneNum = " + phoneNum
                + " ,stuId = " + stuId
                + " ,name = " + stuName + "  from :  " + ip);

        String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
        String begtime = (String)cacheService.get("begtime");

        if(time.compareTo(begtime) < 0){
            return new reqRes(1,"抢票尚未开始");
        }

        Long ipCnt = cacheService.increment(ip);
        if(ipCnt  >= ipLimit){
            logger.log(Level.INFO, "丢弃请求: IP<" + ip + "> 请求次数过多 :" + ipCnt);
            return new reqRes(1,"请考虑使用4G网络抢票");
        }

        Long sum = Long.valueOf((String)cacheService.get("ticketsSum"));
        Long curCnt = cacheService.incrCnt("curCnt");

        if(curCnt >= sum){
            logger.log(Level.INFO, "抢票失败: " + "  : phoneNum = " + phoneNum
                    + " ,stuId = " + stuId
                    + " ,name = " + stuName);
            return new reqRes(1,"手慢了,没票了");
        }

        logger.log(Level.INFO,"存储第< " +  curCnt + "> 条抢票记录"
                + "  : phoneNum = " + phoneNum
                + ",stuId = " + stuId
                + ",name = " + stuName);
        infomap.updateInfo(phoneNum, stuId, stuName);

        return new reqRes(0,"");
    }

    @RequestMapping(value = "/sql" , method = RequestMethod.POST)
    ResponseEntity<Object> reqqInfoGet(@RequestBody Map<String, String> map){
        String token = map.get("token");
        Integer actid = Integer.valueOf(map.get("actid"));
        logger.log(Level.INFO, "收到备份数据请求");
        ResponseEntity responseEntity;
        if(JwtToken.handleToken(token).getResult() != 0){
            logger.log(Level.INFO,"TOKEN 无效");
            responseEntity = ResponseEntity.badRequest().build();
        }
        else try {
            String fileUrl = filePre + version + ".xls";
            version++;
            infomap.backUp(fileUrl);
            logger.log(Level.INFO,"备份文件 : " + fileUrl);
            FileInputStream file = new FileInputStream(fileUrl);
            InputStreamResource resource = new InputStreamResource(file);
            responseEntity = ResponseEntity
                    .ok()
                    .contentLength(file.available())
                    .header("Content-Disposition", "attachment; filename=backup.xls")
                    .header("Content-Type", "application/vnd.ms-excel; charset=UTF-8")
                    .cacheControl(CacheControl.noCache())
                    .body(resource);

        }
        catch (Exception e){
            logger.log(Level.INFO,e.toString());
            responseEntity =  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return responseEntity;
    }

}
