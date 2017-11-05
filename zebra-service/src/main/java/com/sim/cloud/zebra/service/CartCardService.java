package com.sim.cloud.zebra.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.CartCardMapper;
import com.sim.cloud.zebra.model.CartCard;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月3日 下午4:15:03 
* 类说明 
*/
@Service
@Transactional
public class CartCardService  extends AbstractService<CartCardMapper, CartCard> {

}
