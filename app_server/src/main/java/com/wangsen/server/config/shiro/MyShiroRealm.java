package com.wangsen.server.config.shiro;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.wangsen.user.api.SysUserService;
import com.wangsen.user.entity.SysUser;
import com.wangsen.user.enums.LockEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangsen
 * @data 2018/3/11 12:20
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }


    /**
     * 从数据库查询出来的账号名和密码,与用户输入的账号和密码对比
     *当用户执行登录时,在方法处理上要实现subject.login(token);
     *然后会自动进入这个类进行认证
     *交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得shiro自带的不好可以自定义实现
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = (String)token.getPrincipal();
        SysUser sysUser = new SysUser();
        sysUser.setUserName(userName);
        SysUser userInfo = sysUserService.selectOne(new EntityWrapper<SysUser>(sysUser));
        if(userInfo == null){
            // 没找到帐号
            throw new UnknownAccountException();
        }
        if (userInfo.getStatus()== LockEnum.LOCK_ON.getIndex()) {
            // 帐号被锁定
            throw new LockedAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                // 用户对象
                userInfo,
                // 密码
                userInfo.getPassword(),
                // salt
                ByteSource.Util.bytes(userInfo.getSalt()),
                // realm name
                getName()
        );
        // 当验证都通过后，把用户信息放在session里
        Session session = SecurityUtils.getSubject().getSession();
//        userInfo.setDept(deptService.selectById(userInfo.getDeptId()));
        // 用户对象
        session.setAttribute("userSession", userInfo);
        return authenticationInfo;
    }


}
