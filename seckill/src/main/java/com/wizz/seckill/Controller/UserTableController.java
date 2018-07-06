package com.wizz.seckill.Controller;

import com.wizz.seckill.Mapper.actAttrMapper;
import com.wizz.seckill.Mapper.actInfoMapper;
import com.wizz.seckill.Mapper.reqInfoMapper;
import com.wizz.seckill.Model.actAtr;
import com.wizz.seckill.Model.actInfo;
import com.wizz.seckill.Model.reqInfo;
import com.wizz.seckill.Model.reqRes;
import com.wizz.seckill.Service.Impl.RedisService;
import com.wizz.seckill.Service.Impl.textService;
import com.wizz.seckill.Util.jwt.JwtToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@CrossOrigin(origins = "*")
@RestController
public class UserTableController {
    private static Logger logger = Logger.getLogger(UserTableController.class.getName());

    @Autowired
    reqInfoMapper infomap;

    @Autowired
    actAttrMapper actamap;

    @Autowired
    actInfoMapper actimap;

    @Autowired
    RedisService cacheservice;

    private String apikey = "ce3c2012349aded98a7b765bbfffbdbd";

    @RequestMapping(value = "/activity", method = RequestMethod.POST)
    reqRes addact(@RequestBody Map<String, Object> params) {
        String imgUrl = (String) params.get("imgUrl");
        String begTime = (String) params.get("begTime");
        String endTime = (String) params.get("endTime");
        String actStartTime = (String) params.get("actStartTime");
        String Content = (String) params.get("Content");
        String name = (String) params.get("name");
        String des = (String) params.get("description");
        String token = (String)params.get("token");

        if(StringUtils.isEmpty(token)){
            return new reqRes(1,"token 不能为空");
        }

        reqRes rq = JwtToken.handleToken(token);
        if(rq.getResult() != 0){
            return rq;
        }

        int theme = Integer.valueOf((String)params.get("theme"));
        int tickets = Integer.valueOf((String)params.get("tickets"));

        actInfo ai = new actInfo(imgUrl,theme,name,des,Content,1);
        actAtr aa = new actAtr(begTime,endTime,actStartTime,tickets,1);

        actamap.addActAttr(aa);
        ai.setActid(aa.getActid());
        actimap.addActInfo(ai);

        infomap.backUp("/var/lib/mysql-files/BACKUP" + aa.getActid() + ".xls");
        infomap.clear();

        cacheservice.set("ticketsSum","" + aa.getTickets(),24 * 3600);
        cacheservice.set("begtime", aa.getSeckilltime(),24 * 3600);

        return new reqRes(0, "" + ai.getActid());
    }

    @RequestMapping(value = "/activity", method = RequestMethod.DELETE)
    reqRes delact(@RequestBody Map<String, Object> params){
        String token = (String)params.get("token");
        Integer actid = Integer.valueOf((String)params.get("actid"));

        if(StringUtils.isEmpty(token) || actid == null){
            return new reqRes(1,"参数不能为空");
        }

        reqRes rq = JwtToken.handleToken(token);

        if(rq.getResult() != 0){
            return rq;
        }

        boolean updateRedis = (actid == actamap.recent()) ? true : false;

        actamap.delActAttr(actid);
        actimap.delActInfo(actid);

        if(updateRedis) {
            Integer id = actamap.recent();
            if (id != null) {
                actAtr aa = actamap.getActAttrById(id);
                cacheservice.set("ticketsSum", "" + aa.getTickets(), 24 * 3600);
                cacheservice.set("begtime", aa.getSeckilltime(), 24 * 3600);
                infomap.clear();
            }
        }

        return rq;
    }

    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    activity getact(int id) {
        actAtr actatr = actamap.getActAttrById(id);
        actInfo actInfo = actimap.getActInfoById(id);
        if(actatr == null || actInfo == null){
            return new activity(null,null);
        }
        actatr.setBegtime(activity.Date2TimeStamp(actatr.getBegtime(),"yyyy-MM-dd HH:mm:ss") + "000");
        actatr.setEndtime(activity.Date2TimeStamp(actatr.getEndtime(),"yyyy-MM-dd HH:mm:ss") + "000");
        actatr.setSeckilltime(activity.Date2TimeStamp(actatr.getSeckilltime(),"yyyy-MM-dd HH:mm:ss") + "000");

        return new activity(actatr,actInfo);
    }

    @RequestMapping(value = "/usertbls", method = RequestMethod.GET)
    List<activity> getallact(){
        Integer max_id = actamap.recent();
        List<activity> ret = new ArrayList<>();
        for(int i = 0; i <= max_id; i++){
            actAtr aa = actamap.getActAttrById(i);
            actInfo ai = actimap.getActInfoById(i);
            if(aa == null || ai == null ) continue;
            ret.add(new activity(aa,ai));
        }
        return ret;
    }

    @RequestMapping(value = "/message", method = RequestMethod.POST)
    reqRes sendMessage(@RequestBody Map<String,String> map){
        String token = map.get("token");
        Integer actid = Integer.valueOf(map.get("actid"));

        reqRes rq = JwtToken.handleToken(token);
        if(rq.getResult() != 0){
            return rq;
        }
        int tickets = actamap.getActAttrById(actid).getTickets();
        String content = actimap.getActInfoById(actid).getTextdetail();
        List<reqInfo> l = infomap.getHeadK(tickets);

        for (reqInfo ri: l) {
            String curtext = content.replace("#{姓名}", ri.getStuName());
            textService.sendSingle(apikey,ri.getPhoneNum(),curtext);
        }

        return new reqRes(0,"成功发送 " + l.size() + " 条短信");
    }

}


class activity {
    private actAtr actatr;
    private actInfo actinfo;

    public activity(actAtr actatr, actInfo actinfo) {
        this.actatr = actatr;
        this.actinfo = actinfo;
    }

    public activity() {
    }

    public actAtr getActatr() {
        return actatr;
    }

    public void setActatr(actAtr actatr) {
        this.actatr = actatr;
    }

    public actInfo getActinfo() {
        return actinfo;
    }

    public void setActinfo(actInfo actinfo) {
        this.actinfo = actinfo;
    }

    public static String Date2TimeStamp(String dateStr, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(dateStr).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}





