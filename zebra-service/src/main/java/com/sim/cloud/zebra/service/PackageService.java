package com.sim.cloud.zebra.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.PackageMapper;
import com.sim.cloud.zebra.mapper.TariffPlanMapper;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.TariffPlan;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午1:04:11 
* 类说明 
*/
@Service
@Transactional
public class PackageService extends AbstractService<PackageMapper, Package> {
	@Autowired
	private TariffPlanMapper tariffPlanMapper;
	
	/**
	 * 添加或修改系统套餐
	 * @param pack
	 */
	public void insertOrUpd(Package pack){
		Long pid=pack.getPlanId();
		TariffPlan tp =tariffPlanMapper.selectById(pid);
		pack.setPlatQuote(tp.getCost());
		pack.setFlow(tp.getFlow());
		pack.setAccount(tp.getAccount());
		pack.setOperator(tp.getOperator());
		pack.setCardType(tp.getType());
		super.insertOrUpdate(pack);
	}
	
	public List<Package> selectSystemPacks(){
		EntityWrapper<Package> wrapper=new EntityWrapper<>();
		wrapper.ge("type", 1);
		return selectList(wrapper);
	}
	
}
