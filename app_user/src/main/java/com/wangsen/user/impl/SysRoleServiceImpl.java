package com.wangsen.user.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangsen.user.api.SysRoleService;
import com.wangsen.user.entity.SysRole;
import com.wangsen.user.mapper.SysRoleMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author wangsen
 * @since 2018-03-25
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
