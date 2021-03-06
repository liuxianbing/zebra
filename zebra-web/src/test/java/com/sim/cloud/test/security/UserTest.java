package com.sim.cloud.test.security;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.sim.cloud.zebra.model.StatisCardFlow;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.SimCardService;
import com.sim.cloud.zebra.service.SimcardPackViewService;
import com.sim.cloud.zebra.service.StatisCardFlowService;
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
	@Autowired
	SimCardService simCardService;
	@Autowired
	StatisCardFlowService statisCardFlowService;
	@Autowired
	SimcardPackViewService simcardPackViewService;
	
	 @Test
	  public void testAdd2() throws InterruptedException{
//		 EntityWrapper<StatisCardFlow> wrapper=new EntityWrapper<>();
//			wrapper.ge("day", "2017-10-21");
//			wrapper.le("day", "2017-10-27");
//			wrapper.eq("iccid", "89860617030017575948");
		 
//		 SimPoolRelation spr=new SimPoolRelation();
//		 spr.setSimCardId(332l);
//		 spr.setFlowPoolId(2222l);
//		 simPoolRelMapper.insert(spr);
//		 System.out.println(spr.getId());
	 }
	
	 @Test
	  public void testAdd() throws InterruptedException{
		 EntityWrapper<SysUser> wrapper=new EntityWrapper<>();
			wrapper.gt("create_user_id", 0);
//			Map<String,Object> par=new HashMap<>();
//			par.put("create_user_id", 3l);
//			System.out.println(sysUserService.selectByMap(par));
			System.out.println(sysUserService.selectList(wrapper));
//		 for(int i=10;i<30;i++){
//		 SysUser user=new SysUser();
//		 user.setAccount("lxb2"+i);
//		 user.setUserName("lxb2..."+i);
//		 user.setPhone("1888888882"+i);
//		 user.setEmail("ddd@233.com"+i);
//		 user.setAddress("ffffffff2"+i);
//		 user.setPasswd("ggggggggg2"+i);
//		 user.setCreateTime(DateUtil.getDateTime());
//		 sysUserService.insert(user);
//		 }
	}
	 
	 @Test
	  public void testPage() throws InterruptedException{
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
