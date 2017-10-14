package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.CompanyMapper;
import com.sim.cloud.zebra.model.Company;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午5:03:13 
* 类说明 
*/
@Service
@Transactional
public class CompanyService  extends AbstractService<CompanyMapper, Company> {

}
