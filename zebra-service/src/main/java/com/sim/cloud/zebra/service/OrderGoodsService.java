package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.OrderGoodsMapper;
import com.sim.cloud.zebra.model.OrderGoods;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月3日 上午10:21:15 
* 类说明 
*/
@Service
@Transactional
public class OrderGoodsService extends AbstractService<OrderGoodsMapper, OrderGoods> {

}
