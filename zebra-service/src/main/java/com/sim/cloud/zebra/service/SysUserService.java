package com.sim.cloud.zebra.service;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SysUserMapper;
import com.sim.cloud.zebra.model.SysUser;

/**
 * SysUser服务实现类
 * 
 * @author ShenHuaJie
 * @version 2016-08-27 22:39:42
 */
@Service
@Transactional
public class SysUserService extends AbstractService<SysUserMapper, SysUser> {

	public List<SysUser> selectUsers(SysUser users) {
		return getBaseMapper().selectUsers(users);
	}
	
	public void addSysUserTransaction(SysUser users){
		super.insert(users);
		//throw new RuntimeException("roll back");
	}
	
	/**
	 * 查询普通客户
	 * @return
	 */
	public List<SysUser> selectCustomers() {
		EntityWrapper<SysUser> wrapper=new EntityWrapper<>();
		wrapper.gt("create_user_id", 0);
		return selectList(wrapper);
	}
	
	public SysUser selectByPhone(String phone){
		EntityWrapper<SysUser> wrapper=new EntityWrapper<>();
		wrapper.eq("phone", phone);
		List<SysUser> list=selectList(wrapper);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}

	// public List<SysUser> selectMutiTablePage(Pagination
	// pagination,EntityWrapper wrapper){
	// return getBaseMapper().selectMutiTablePage(pagination,wrapper);
	// }

}
