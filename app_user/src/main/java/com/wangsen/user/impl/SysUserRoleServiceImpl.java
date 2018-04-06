package com.wangsen.user.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangsen.user.api.SysUserRoleService;
import com.wangsen.user.entity.SysUserRole;
import com.wangsen.user.mapper.SysUserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author wangsen
 * @since 2018-03-25
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
