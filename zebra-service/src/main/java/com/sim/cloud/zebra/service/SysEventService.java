package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SysEventMapper;
import com.sim.cloud.zebra.model.SysEvent;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月28日 上午11:08:08 
* 类说明 
*/
@Service
@Transactional
public class SysEventService extends AbstractService<SysEventMapper, SysEvent>{

}
