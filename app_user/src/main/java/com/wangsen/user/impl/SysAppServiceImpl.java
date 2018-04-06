package com.wangsen.user.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.wangsen.user.api.SysAppService;
import com.wangsen.user.entity.SysApp;
import com.wangsen.user.mapper.SysAppMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应用表 服务实现类
 * </p>
 *
 * @author wangsen
 * @since 2018-03-25
 */
@Service
public class SysAppServiceImpl extends ServiceImpl<SysAppMapper, SysApp> implements SysAppService {

}
