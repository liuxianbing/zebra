package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.PackageUserMapper;
import com.sim.cloud.zebra.model.PackageUser;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午1:04:11 
* 类说明 
*/
@Service
@Transactional
public class PackageUserService extends AbstractService<PackageUserMapper, PackageUser> {

}
