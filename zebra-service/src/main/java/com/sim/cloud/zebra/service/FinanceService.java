package com.sim.cloud.zebra.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.common.util.DateUtil;
import com.sim.cloud.zebra.common.util.FinanceEnum;
import com.sim.cloud.zebra.common.util.NumberUtils;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.FinanceMapper;
import com.sim.cloud.zebra.model.Finance;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月11日 上午11:55:19 
* 类说明 
*/
@Service
@Transactional
public class FinanceService   extends AbstractService<FinanceMapper, Finance>{

	/**
	 * 查询用户余额
	 * @param uid
	 * @return
	 */
	public float selectBance(Long uid){
		EntityWrapper<Finance> wrapper=new EntityWrapper<>();
		wrapper.eq("uid", uid);
		wrapper.orderBy("id", false);
		List<Finance> list=selectList(wrapper);
		float maxMoney=0.0f;
		if(list!=null && list.size()>0){
			maxMoney=list.get(0).getBalance();
		}
		return maxMoney;
	}
	
	/**
	 * 充值或者购卡
	 * @param uid
	 * @param type
	 * @param money
	 * @return
	 */
	public boolean insertFinance(Long uid,int type,float money){
		float maxMoney=selectBance(uid);
		Finance fa=new Finance();
		fa.setUid(uid);
		fa.setType(type);
		fa.setMoney(money);
		fa.setCreateTime(DateUtil.getDateTime());
		if(type==FinanceEnum.CHONGZHI.getType()){
			fa.setBalance(NumberUtils.formatFloatNum(money+maxMoney));
		}else{
			fa.setBalance(NumberUtils.formatFloatNum(maxMoney-money));
			if(fa.getBalance()<0.0f){
				return false;
			}
			fa.setMoney(fa.getMoney()*-1);
		}
		insert(fa);
		return true;
	}
}
