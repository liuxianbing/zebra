package com.sim.cloud.zebra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.CompanyMapper;
import com.sim.cloud.zebra.mapper.SysUserMapper;
import com.sim.cloud.zebra.model.Company;
import com.sim.cloud.zebra.model.SysUser;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午5:03:13 
* 类说明 
*/
@Service
@Transactional
public class CompanyService  extends AbstractService<CompanyMapper, Company> {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	/**
	 * 认证企业信息
	 * @param company
	 */
	public void saveAuthInfo(Company company){
		boolean isupd=company.getId()!=null && company.getId()>0l;
		this.insertOrUpdate(company);
		if(!isupd){
			SysUser su=new SysUser();
			su.setId(company.getUid());
			su.setCid(company.getId());
			sysUserMapper.updateById(su);
		}
	}
}
