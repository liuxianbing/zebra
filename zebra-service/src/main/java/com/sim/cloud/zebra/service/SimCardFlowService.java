package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimCardFlowMapper;
import com.sim.cloud.zebra.model.SimCardFlow;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月22日 下午2:50:52 
* 类说明 
*/
@Service
@Transactional
public class SimCardFlowService  extends AbstractService<SimCardFlowMapper, SimCardFlow> {

}
