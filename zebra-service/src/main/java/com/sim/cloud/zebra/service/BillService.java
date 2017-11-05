package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.BillMapper;
import com.sim.cloud.zebra.model.Bill;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月5日 下午12:45:54 
* 类说明 
*/
@Service
@Transactional
public class BillService   extends AbstractService< BillMapper,  Bill>{

}
