package com.sim.cloud.zebra.service;

import java.util.List;
import java.util.stream.Collectors;

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
	}
	
	/**
	 * 批量更新客户的认证信息
	 * @param su
	 */
	public void batchUpdateUserAuth(SysUser su){
		    updateById(su);
			EntityWrapper<SysUser> en=new EntityWrapper<>();
			en.eq("cid", su.getCid());
			updateBatchById(selectList(en).stream().map(m->{
				SysUser tmp=new SysUser();
				tmp.setId(m.getId());
				tmp.setAuth(su.getAuth());
				return tmp;
			}).collect(Collectors.toList()));
	}
	
	/**
	 * 查询普通客户
	 * @return
	 */
	public List<SysUser> selectCustomers() {
		EntityWrapper<SysUser> wrapper=new EntityWrapper<>();
		wrapper.eq("role", SysUser.ROLE_MANAGER);
		return selectList(wrapper);
	}
	
	/**
	 * 根据cid查询用户
	 * @return
	 */
	public SysUser selectManager(Long cid) {
		EntityWrapper<SysUser> wrapper=new EntityWrapper<>();
		wrapper.eq("role", SysUser.ROLE_MANAGER);
		wrapper.eq("cid", cid);
		return selectOne(wrapper);
	}
	
	public List<SysUser> selectCommonUser(Long cid) {
		EntityWrapper<SysUser> wrapper=new EntityWrapper<>();
		wrapper.eq("cid", cid);
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
