package com.wangsen.server;

import com.wangsen.user.api.SysUserService;
import com.wangsen.user.entity.SysUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppServerApplicationTests {

	@Autowired
	private SysUserService sysUserServiceImpl;


	@Test
	public void contextLoads() {
		SysUser sysUser = new SysUser();
		sysUser.setUserName("w");
		sysUser.setPassword("2423432");
		sysUserServiceImpl.insert(sysUser);

	}

}
