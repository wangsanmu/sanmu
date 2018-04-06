package com.wangsen.server.controller;

import com.wangsen.common.Result;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wangsen
 * @data 2018/3/25 22:58
 */
@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
@RestController
public class LoginController {

    private static Logger logger = LogManager.getLogger(LoginController.class.getName());

    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    @PostMapping(value = {"/login"})
    public Object Login(@RequestParam(required = true) String username, @RequestParam(required = true) String password){
        Result result = new Result();
        UsernamePasswordToken token  = new UsernamePasswordToken(username,password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            logger.info("{ "+username+" }登陆成功");
            result.setSuccess(true);
            result.setSuccessMsg("登陆成功");
        } catch(UnknownAccountException e){
            result.setErrorMsg("账户或者密码有误");
        }catch(IncorrectCredentialsException e){
            result.setErrorMsg("账户或者密码有误");
        }catch (LockedAccountException e) {
            result.setErrorMsg("您的账户已被锁定,请与管理员联系");
        }catch(ExcessiveAttemptsException e){
            result.setErrorMsg("登录失败次数过多,请稍后再试！");
        }catch(Exception e){
            result.setErrorMsg("系统发生错误，请联系管理员！");
        }
        return result;
    }

    @PostMapping(value = "/logout")
    public String logout() {
        // 注销登录
        SecurityUtils.getSubject().logout();
        return null;

    }
}
