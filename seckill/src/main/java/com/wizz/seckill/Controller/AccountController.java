package com.wizz.seckill.Controller;

import com.wizz.seckill.Model.SysUser;
import com.wizz.seckill.Model.reqRes;
import com.wizz.seckill.Model.reqResWithToken;
import com.wizz.seckill.Service.Impl.RedisService;
import com.wizz.seckill.Service.SysUserService;
import com.wizz.seckill.Util.jwt.JwtToken;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author pjmike
 * @create 2018-05-19 15:05
 */

@CrossOrigin(origins = "*")
@RestController
public class AccountController{

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisService redisService;

    /**
     * 注册
     *
     * @param map
     * @return
     */
    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public reqRes signUp(@RequestBody Map<String, String> map) {
        return new reqRes(0,"暂不开放");
        /*
        String username = map.get("userName");
        String password = map.get("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return new reqRes(1, "username or password is empty");
        }
        if (userService.findUserByName(username) != null) {
            return new reqRes(1, "username already exists");
        }
        SysUser user = userService.addSysUser(new SysUser(username, password));
        return new reqRes(0, String.valueOf(user.getId()));
        */
    }

    /**
     * 登录
     *
     * @param map
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public reqResWithToken signIn(@RequestBody Map<String, String> map, HttpServletResponse response) throws UnsupportedEncodingException {
        String username = map.get("userName");
        String password = map.get("password");
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return new reqResWithToken(1, "username or password is empty", -1);
        }
        SysUser user = userService.findUserByName(username);
        if (user == null) {
            return new reqResWithToken(1, "account does not exists", -1);
        }
        if (!StringUtils.equals(password, user.getPassword())) {
            return new reqResWithToken(1, "password is wrong", -1);
        }
        //生成jwt
        String token = JwtToken.createToken(user.getId());
        //将jwt放在redis中，设置jwt过期时间的两倍，即refresh刷新时间
        long refreshTime = 36000L;
        redisService.set("JWT-" + user.getId(), token, refreshTime);
        //设置响应头
        return new reqResWithToken(0,token,user.getId());
    }
}
