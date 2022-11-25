package com.crm.settings.web.controller;

import com.crm.commons.constant.Constant;
import com.crm.commons.domain.ReturnObject;
import com.crm.commons.utils.DateUtils;
import com.crm.settings.domain.User;
import com.crm.settings.service.UserService;
import com.crm.settings.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("settings/qx/user/toLogin")
    public String toLogin(){
        return "settings/qx/user/login";
    }
    @RequestMapping("settings/qx/user/login")
    public @ResponseBody Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request, HttpSession session, HttpServletResponse response){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        //调用service层方法，查询用户
        User user =userService.queryUserByLoginActPwd(map);
        //根据查询结果，生成响应信息
        ReturnObject returnObject =new ReturnObject();
        if (user==null){
            //登陆失败，用户名或密码错误
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或密码错误");
        }else {//判断账号是否合法
            if (DateUtils.formatDateTime(new Date()).compareTo(user.getCreateTime())<0){
                //登陆失败，账号已过期
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }else if ("0".equals(user.getLockState())){
                //登陆失败，账号被锁定
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号被锁定");
            }else if (!user.getAllowIps().contains(request.getRemoteAddr())){
                //登陆失败，ip受限
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip受限");
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                session.setAttribute(Constant.SESSION_USER,user);
                if (isRemPwd.equals("true")){
                    Cookie c1 = new Cookie("loginAct",loginAct);
                    c1.setMaxAge(60*60*24*10);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(60*60*24*10);
                    response.addCookie(c2);
                }else {
                    Cookie c1 = new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }
        return returnObject;
    }
    @RequestMapping("settings/qx/user/logout")
    public String logout(HttpSession session,HttpServletResponse response){
        //清除cookie
        Cookie c1 = new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);
        //销毁session
        session.invalidate();
        return "redirect:/";
    }
}
