package com.sim.cloud.zebra.dao.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.SysUserService;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年9月27日 下午12:48:52 
* 类说明 
*/
@ContextConfiguration(locations = {"classpath:spring/datasource-config-druid.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

	@Autowired
	SysUserService sysUserService;
	@Test
	public void testAdd(){
		 SysUser user=new SysUser();
		 user.setUserName("liuxianbing_roolback");
		 user.setPasswd("123456a");
		 sysUserService.addSysUserTransaction(user);
	}

	 @Test
	  public void testMutiPage() throws InterruptedException{
		 SysUser user=new SysUser();
		 user.setId(1l);
		 Page<SysUser> page=new Page<>(1,5);
		  EntityWrapper<SysUser> wrapper=new EntityWrapper<SysUser>(user);
		 // wrapper.between("id", 5, 8);
		  //page.setOrderByField("id");
		  page.setAsc(false);
		 sysUserService.queryMutiTablePage(page,user);
		  System.out.println(page.getRecords());
		  System.out.println(page.getTotal());
	}
}
