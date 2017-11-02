package com.sim.cloud.zebra.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.model.TariffPlan;
import com.sim.cloud.zebra.service.SimCardService;
import com.sim.cloud.zebra.service.SysUserService;
import com.sim.cloud.zebra.service.TariffPlanService;

/**
 * Month Fee task
 * 
 * @author henrylv
 *
 */
@Service
@Transactional
public class MonthBillTask {


	private static Logger log = Logger.getLogger(DataStatsTask.class);
	
	@Autowired
	private SimCardService simCardService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private TariffPlanService tariffPlanService;

	// total data yesterday
	Map<String, Float> totalDataMap = new HashMap<String, Float>();
	
	/**
	 * TODO 延迟计费，等到联通出账单后，这边再出账单；上月账单流量数据直接从联通Jasper API获取
	 * 用户：套餐：卡：流量：计费规则
	 * 
	 */
	//@Scheduled(cron="0 10 0 * * *") // 00:10:00 every day
	public void syncDayData() {
		
		// 获取所有用户
		List<SysUser> sysUsers = sysUserService.queryList(null);
		
		for (SysUser sysUser : sysUsers) {
			Float dataBill = 0.0f;
			
			// 获取所有资费计划
			List<TariffPlan> tariffPlans = tariffPlanService.queryList(null);
			
			for (TariffPlan tariffPlan : tariffPlans) {
				if (tariffPlan.getType() == 0) { // 独享，一个流量卡一个费用记录
					// TODO 根据用户和资费计划获取所有流量卡
					List<SimCard> simCards = null;
					
					for (SimCard simCard : simCards) {
						// TODO 计算每个sim卡的费用，保存到DB消费记录表中
						// TODO 可以实现一个子方法
						// TODO Jasper api: GetTerminalUsage
						
						// TODO dataBill += 
					}
				} else if (tariffPlan.getType() == 1) { // 共享，每个流量池一个费用记录
					// TODO 根据用户和资费计划获取所有流量卡
					List<SimCard> simCards = null;
					
					// TODO 计算所有卡的总流量
					Float sum = 0.0f;
					for (SimCard simCard : simCards) {
						// TODO 计算每个sim卡的流量
						
					}
					
					// TODO 根据总流量计算流量池费用，保存到DB消费记录表中
					// TODO 可以实现一个子方法
					// TODO jasper api: GetTerminalUsage
					
					// TODO dataBill += 
				}
			}
			
			// TODO 计算出总费用后，保存到DB账单表中
			
		}
	}
	
	/**
	 * 单卡套餐
	 */
	private void singleCardBill() {
		
	}
	
	/**
	 * 共享套餐
	 */
	private void poolCardBill() {
		
	}
	
	/**
	 * 流量计费
	 */
	private void dataBill() {
		
	}
}
