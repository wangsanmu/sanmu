package com.wangsen.server.controller;

import com.wangsen.common.Result;
import com.wangsen.server.common.EndecryptUtil;
import com.wangsen.user.api.SysUserService;
import com.wangsen.user.entity.SysUser;
import com.wangsen.user.enums.LockEnum;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangsen
 * @data 2018/3/24 10:49
 * @des 用户信息的管理
 */
@RestController
@RequestMapping(value = "/user")
public class UserCenter {

    private static Logger logger = LogManager.getLogger(UserCenter.class.getName());


    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/insertUser")
    public Object  register(SysUser user){
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 注册用户 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        Result result = new Result();
        if(StringUtils.isNoneBlank(user.getUserName()) && StringUtils.isNoneBlank(user.getPassword())){
            //随机盐
            String salt = EndecryptUtil.getRadomSalt();
            //密码加密
            String  password = EndecryptUtil.getEncryPassword("SHA",user.getPassword(),salt,10);
            //保存用户
            SysUser sysUser = new SysUser();
            sysUser.setUserName(user.getUserName());
            sysUser.setPassword(password);
            sysUser.setSalt(salt);
            sysUser.setStatus(LockEnum.LOCK_OFF.getIndex());
            sysUserService.insert(sysUser);
            result.setSuccess(true);
            result.setSuccessMsg("注册成功!!!");
            logger.info("{}注册成功");
            logger.info(">>>>>>>>>>>>>>>>>>>>>>>>> 注册结束 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        }else{
            result.setErrorMsg("用户名或者密码为空");
        }
        return result;
    }
}
