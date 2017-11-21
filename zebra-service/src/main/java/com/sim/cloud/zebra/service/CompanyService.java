package com.sim.cloud.zebra.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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
	private SysUserService sysUserService;
	
	/**
	 * 认证企业信息
	 * @param company
	 */
	public void saveAuthInfo(Company company){
		this.insertOrUpdate(company);
			SysUser su=new SysUser();
			su.setId(company.getUid());
			su.setCid(company.getId());
			company=selectById(company.getId());
			try {
				su.setAuth((company.getLegalAuth()+company.getBusinessAuth())/2);
			} catch (Exception e) {
				su.setAuth(0);
			}
			sysUserService.batchUpdateUserAuth(su);
	}
}
