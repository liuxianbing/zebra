package com.sim.cloud.zebra.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimCardMapper;
import com.sim.cloud.zebra.model.SimCard;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午1:03:18 
* 类说明 
*/
@Service
@Transactional
public class SimCardService extends AbstractService<SimCardMapper, SimCard> {

	private static Logger log = Logger.getLogger(SimCardService.class);
	
	
	
}
