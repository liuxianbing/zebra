package com.sim.cloud.zebra.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
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

	public List<CartCard> selectByOrderId(Long id){
		EntityWrapper<CartCard> wrapper=new EntityWrapper<>();
		wrapper.eq("order_id", id);
		return selectList(wrapper);
	}
	
	public List<CartCard> selectCarts(Long uid,int type){
		EntityWrapper<CartCard> wrapper=new EntityWrapper<>();
		wrapper.eq("uid", uid);
		wrapper.eq("type", type);
		return selectList(wrapper);
	}
	
	public List<CartCard> selectUserAllocPacks(Long cid){
	EntityWrapper<CartCard> wrapper=new EntityWrapper<>();
	wrapper.eq("cid", cid);
	wrapper.eq("status", 1);
	wrapper.eq("type", CartCard.ORDERE_OK);
	return selectList(wrapper).stream().
	filter(f->f.getNum()>f.getAllocNum()).collect(Collectors.toList());
	}
}
