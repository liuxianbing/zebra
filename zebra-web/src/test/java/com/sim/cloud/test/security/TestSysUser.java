//package com.sim.cloud.test.security;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.baomidou.mybatisplus.mapper.EntityWrapper;
//import com.baomidou.mybatisplus.plugins.Page;
//import com.sim.cloud.zebra.model.SysUser;
//import com.sim.cloud.zebra.service.security.SysUserService;
//
//@ContextConfiguration(locations = {"classpath:spring/datasource-config-druid.xml"})
//@RunWith(SpringJUnit4ClassRunner.class)
//public class TestSysUser {
//
//	@Autowired
//	SysUserService sysUserService;
//	
//
//	 @Test
//	  public void testMutiPage() throws InterruptedException{
//		 SysUser user=new SysUser();
//		 user.setId(1l);
//		 Page<SysUser> page=new Page<>(1,5);
//		  EntityWrapper<SysUser> wrapper=new EntityWrapper<SysUser>(user);
//		 // wrapper.between("id", 5, 8);
//		  //page.setOrderByField("id");
//		  page.setAsc(false);
//		 sysUserService.queryMutiTablePage(page,user);
//		  System.out.println(page.getRecords());
//		  System.out.println(page.getTotal());
//	}
//	  @Test
//	  public void testList() throws InterruptedException{
//		  SysUser user=new SysUser();
//		 // user.setPasswd("ttt2");
////		  System.out.println(sysUserService.selectUsers(user));
////		  Thread.sleep(2000l);
////		  
////		  System.out.println(sysUserService.selectUsers(user));
//		  
//		  Page<SysUser> page=new Page<>(1,5);
//		  EntityWrapper<SysUser> wrapper=new EntityWrapper<SysUser>(user);
//		  wrapper.between("id", 5, 8);
//		  page.setOrderByField("id");
//		  page.setAsc(false);
//		  Page<SysUser> pageRes=sysUserService.selectPage(page,wrapper);
//		  System.out.println(pageRes.getRecords());
////		  
////		  page=new Page<>(2,5);
////		 pageRes=sysUserService.selectPage(page);
////		  System.out.println(pageRes.getRecords());
//	  }
//	
//	  @Test
//	public void testAddUser(){
//		  List<SysUser> list=new ArrayList<>();
//			SysUser user=new SysUser();
//			user.setUserName("lxb_"+11);
//			user.setPasswd("gggggg"+11);
//			list.add(user);
//		//myService.insertBatch(list);
//	}
//	  @Test
//	  public void testUpdate(){
//		  SysUser user=new SysUser();
//		  user.setId(1l);
//		  user.setPasswd("ttt2");
//		  System.out.println(sysUserService.selectById(1l));
//	  }
//	  
//	  
//	
//}
