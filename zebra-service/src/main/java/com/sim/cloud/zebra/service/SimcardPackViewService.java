package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimcardPackViewMapper;
import com.sim.cloud.zebra.model.SimcardPackageView;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月18日 下午6:29:27 
* 类说明  页面查询物联网卡服务类
*/
@Service
@Transactional
public class SimcardPackViewService extends AbstractService<SimcardPackViewMapper, SimcardPackageView>{

}
