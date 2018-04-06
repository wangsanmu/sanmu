package com.wangsen.user.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangsen.user.api.SysMenuService;
import com.wangsen.user.entity.SysMenu;
import com.wangsen.user.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangsen
 * @since 2018-03-25
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

}
