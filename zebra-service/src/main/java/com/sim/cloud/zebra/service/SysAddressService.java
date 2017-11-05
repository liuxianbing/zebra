package com.sim.cloud.zebra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SysAddressMapper;
import com.sim.cloud.zebra.mapper.SysAreaMapper;
import com.sim.cloud.zebra.model.SysAddress;
import com.sim.cloud.zebra.model.SysArea;

import java.util.List;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 下午6:48:00 
* 类说明 
*/
@Service
@Transactional
public class SysAddressService extends AbstractService<SysAddressMapper, SysAddress> {

	@Autowired
	private SysAreaMapper sysAreaMapper;
	
	/**
	 * 查询所有省份
	 * @return
	 */
	public List<SysArea> selectAllProvinces(){
		EntityWrapper<SysArea> wrapper=new EntityWrapper<>();
		wrapper.eq("deep", 1);
		return sysAreaMapper.selectList(wrapper);
	}
	/**
	 * 获取市或者县
	 * @param pid
	 * @param deep
	 * @return
	 */
	public List<SysArea> selectParentId(Long pid,int deep){
		EntityWrapper<SysArea> wrapper=new EntityWrapper<>();
		wrapper.eq("deep", deep);
		wrapper.eq("parent_id", pid);
		return sysAreaMapper.selectList(wrapper);
	}
	/**
	 * 查询自己的收货地址
	 * @param uid
	 * @return
	 */
	public List<SysAddress> selectSelfAddress(Long uid){
		EntityWrapper<SysAddress> wrapper=new EntityWrapper<>();
		wrapper.eq("uid", uid);
		return selectList(wrapper);
	}
	/**
	 * 设置默认地址
	 * @param uid
	 * @param id
	 */
	public void saveDefaultAddress(Long uid,Long id){
		EntityWrapper<SysAddress> wrapper=new EntityWrapper<>();
		wrapper.eq("uid", uid);
		SysAddress sad=new SysAddress();
		sad.setUid(uid);
		sad.setOpt(0);
	   this.getBaseMapper().update(sad, wrapper);
	   sad=new SysAddress();
	   sad.setId(id);
	   sad.setOpt(1);
	   super.updateById(sad);
	}
}
