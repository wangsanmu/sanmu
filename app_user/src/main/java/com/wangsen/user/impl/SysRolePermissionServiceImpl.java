package com.wangsen.user.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangsen.user.api.SysRolePermissionService;
import com.wangsen.user.entity.SysRolePermission;
import com.wangsen.user.mapper.SysRolePermissionMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author wangsen
 * @since 2018-03-25
 */
@Service
public class SysRolePermissionServiceImpl extends ServiceImpl<SysRolePermissionMapper, SysRolePermission> implements SysRolePermissionService {

}
