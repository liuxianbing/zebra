package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.StatisCardFlowMapper;
import com.sim.cloud.zebra.model.StatisCardFlow;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月26日 下午4:16:27 
* 类说明 
*/
@Service
@Transactional
public class StatisCardFlowService extends AbstractService<StatisCardFlowMapper, StatisCardFlow> {

}
