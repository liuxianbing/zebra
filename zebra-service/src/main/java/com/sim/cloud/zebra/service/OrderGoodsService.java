package com.sim.cloud.zebra.service;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.common.util.CartCardEnum;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.FinanceEnum;
import com.sim.cloud.zebra.common.util.NumberUtils;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.OrderGoodsMapper;
import com.sim.cloud.zebra.model.CartCard;
import com.sim.cloud.zebra.model.OrderGoods;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月3日 上午10:21:15 
* 类说明 
*/
@Service
@Transactional
public class OrderGoodsService extends AbstractService<OrderGoodsMapper, OrderGoods> {

	@Autowired
	private CartCardService cartCardService;
	@Autowired
	private FinanceService financeService;
	
	
	public boolean updateOrderStatus(OrderGoods order){
		String now=DateUtil.getDateTime();
		if(order.getType()==CartCardEnum.PAYOK_ORDER.getStatus()){
			order.setPayTime(now);
			OrderGoods tmp=selectById(order.getId());
			if(!financeService.insertFinance(tmp, FinanceEnum.BUY_CARD.getType())){
				return false;
			}
			//批量更新卡片列表状态 为完成
			cartCardService.updateBatchById(
			cartCardService.selectByOrderId(order.getId()).stream()
			.map(m->{
				CartCard tt=new CartCard();
				tt.setId(m.getId());
				tt.setType(CartCard.ORDERE_OK);
				return tt;
			}).collect(Collectors.toList()));
		}else if(order.getType()==CartCardEnum.CHECKOK_ORDER.getStatus()){
			order.setAuditTime(now);
		}else if(order.getType()==CartCardEnum.DELIVEROK_ORDER.getStatus()){
			order.setOuterTime(now);
		}else if(order.getType()==CartCardEnum.SUCCESS_ORDER.getStatus()){
			order.setSucTime(now);
		}else if(order.getType()==CartCardEnum.CHECKFAIL_ORDER.getStatus()){
			order.setAuditTime(now);
		}
		updateById(order);
		return true;
	}
	
	/**
	 * 第一步提交order
	 * @param list
	 * @param order
	 */
	public void saveOrder(OrderGoods order,List<Long> params){
		order.setCreateTime(DateUtil.getDateTime());
		order.setOrderTime(DateUtil.getDateTime());
		order.setType(CartCardEnum.SUBMIT_ORDER.getStatus());
		//if(null!=order.getDeliverCost())
		//order.setTotalCost(NumberUtils.formatFloatNum(order.getTotalCost()+order.getDeliverCost()));
		order.setOrderCode(DateUtil.getDateTime(DateUtil.DATE_PATTERN.YYYYMMDDHHMMSS)+"_"+order.getUid());
		insertOrUpdate(order);
		List<CartCard> list=cartCardService.selectCarts(order.getUid(),CartCard.INCART).stream()
		.filter(f->params.contains(f.getId()) && (f.getOrderId()==null || f.getOrderId()==0l)).map(e->{
			e.setOrderId(order.getId());
			e.setType(CartCard.ORDERED);
			return e;
		}).collect(Collectors.toList());
		if(list.size()!=params.size()){
			throw new RuntimeException("提交订单失败，请刷新页面重新提交");
		}
		cartCardService.insertOrUpdateBatch(list);
	}
}
