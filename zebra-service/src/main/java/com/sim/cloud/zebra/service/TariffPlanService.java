package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.TariffPlanMapper;
import com.sim.cloud.zebra.model.TariffPlan;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 上午11:41:01 
* 类说明  资费计划服务实现
*/
@Service
@Transactional
public class TariffPlanService extends AbstractService<TariffPlanMapper, TariffPlan> {

}
