package com.sim.cloud.zebra.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.model.Bill;
import com.sim.cloud.zebra.model.BillRecord;
import com.sim.cloud.zebra.model.Package;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.SimcardPackageView;
import com.sim.cloud.zebra.model.SysUser;
import com.sim.cloud.zebra.service.BillRecordService;
import com.sim.cloud.zebra.service.BillService;
import com.sim.cloud.zebra.service.PackageService;
import com.sim.cloud.zebra.service.SimcardPackViewService;
import com.sim.cloud.zebra.service.SysUserService;
import com.sim.cloud.zebra.service.TariffPlanService;
import com.simclouds.unicom.jasper.JasperClient;

/**
 * Month Fee task
 * 
 * @author henrylv
 *
 */
@Service
@Transactional
public class MonthBillTask {

	private static Logger log = Logger.getLogger(MonthBillTask.class);
	
	@Autowired
	private SimcardPackViewService simCardPackViewService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private PackageService packageService;

	@Autowired
	private BillService billService;
	
	@Autowired
	private BillRecordService billRecordService;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
	
	/**
	 * TODO 延迟计费，等到联通出账单后，这边再出账单；上月账单流量数据直接从联通Jasper API获取
	 * 用户：套餐：卡：流量：计费规则
	 * 
	 */
	@Scheduled(cron="0 10 1 1 * *") // 00:10:00 every month 
	//@Scheduled(fixedRate=1000 * 60 * 3) // only for test
	public void calMonthBill() {
		// get users
		Map<String, String> accounts = ZebraConfig.getAccounts();
		JasperClient jasperClient = null;
		
		// 获取所有用户
		List<SysUser> sysUsers = sysUserService.selectCustomers(); // 获取所有用户
		
		for (SysUser sysUser : sysUsers) {
			Bill bill = new Bill();
			
			Float dataBill = 0.0f;
			
			// 获取所有资费计划
			// TODO 获取当前用户名下所有的资费计划，方法？
			List<Package> packageList = packageService.selectCompanyPacks(sysUser.getCid());
			
			for (Package tariffPlan : packageList) {
				if (tariffPlan.getCardType() == 0) { // 独享，一个流量卡一个费用记录
					// TODO 根据用户和资费计划获取所有流量卡
					List<SimcardPackageView> simCards = simCardPackViewService.selectCompanyPackCards(sysUser.getCid(), tariffPlan.getId()); // TODO 获取当前客户和当前资费计划条件下所有流量卡
					
					for (SimCard simCard : simCards) {
						BillRecord billRecord = new BillRecord();
						// 计算每个sim卡的费用，保存到DB消费记录表中
						// Jasper api: GetTerminalUsage 获取联通账单上个月卡流量信息
						jasperClient = JasperClient.getInstance(accounts.get(simCard.getAccount()));
						Float flowData = jasperClient.getTerminalUsage(simCard.getIccid(), getLastMouthTime());
						
						// 根据流量信息计算费用
						// 1. 套餐费用；
						dataBill = tariffPlan.getExternalQuote();
						// 2. 超出流量费用；
						if (billRecord.getFlow() - tariffPlan.getFlow() > 0.0f) {
							dataBill += dataBill(billRecord.getFlow() - tariffPlan.getFlow());
						}
						
						billRecord.setFlow(flowData);
						billRecord.setCost(dataBill);
						billRecord.setIccid(simCard.getIccid());
						billRecord.setMonth(getLastMouthTime());
						billRecord.setType(0); // 单卡
						
						// 账单
						bill.setCost(bill.getCost() + billRecord.getCost());
						bill.setFlow(bill.getFlow() + billRecord.getFlow());
						billRecordService.insert(billRecord);
					}
				} else if (tariffPlan.getCardType() == 1) { // 共享，每个流量池一个费用记录
					int validCardNum = 0;
					
					BillRecord billRecord = new BillRecord();
					billRecord.setType(1); // 共享
					billRecord.setMonth(getLastMouthTime());
					
					Map<String, String> jsonMap = new HashMap<String, String>();
					
					// TODO 根据用户和资费计划获取所有流量卡
					List<SimcardPackageView> simCards = simCardPackViewService.selectCompanyPackCards(sysUser.getCid(), tariffPlan.getId());
					
					// TODO 计算所有卡的总流量
					//Float sum = 0.0f;
					if (simCards != null && simCards.size() > 0) {	
						for (SimCard simCard : simCards) {
							// jasper api: GetTerminalUsage，获取联通账单上个月卡流量信息
							jasperClient = JasperClient.getInstance(accounts.get(simCard.getAccount()));
							Float flowData = jasperClient.getTerminalUsage(simCard.getIccid(), getLastMouthTime() + "-01");
							
							if (flowData != null) {
								// 计算每个sim卡的流量并记录到jsonMap中
								jsonMap.put(simCard.getIccid(), flowData.toString());
								billRecord.setFlow(flowData + billRecord.getFlow());
								bill.setFlow(bill.getFlow() + billRecord.getFlow());
								
								validCardNum++;
							}
						}
					} else {
						break; // 无卡，不记消费记录
					}
					
					// 根据总流量计算流量池费用，保存到DB消费记录表中
					// 1. 套餐费用；
					dataBill = tariffPlan.getExternalQuote() * validCardNum;
					// 2. 超出流量费用；
					if (billRecord.getFlow() - tariffPlan.getFlow() * validCardNum > 0.0f) {
						dataBill += dataBill(billRecord.getFlow() - tariffPlan.getFlow() * validCardNum);
					}
					
					billRecord.setDetail("jsonMap"); // TODO
					billRecord.setCost(dataBill);
					bill.setCost(bill.getCost() + dataBill);
					
					billRecordService.insert(billRecord);
				}
			}
			
			// 计算出总费用后，保存到DB账单表中
			bill.setMonth(getLastMouthTime());
			bill.setUid(sysUser.getId());
			bill.setCid(sysUser.getCid());
			billService.insert(bill);
		}
	}
	
	/**
	 * 流量计费
	 */
	private Float dataBill(Float data) {
		
		return data * 0.0003f;
	}
	
	/**
	 * get last month time
	 * 
	 * @return
	 */
	private String getLastMouthTime() {
		// start time
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		String startTime = dateFormat.format(c.getTime());
		
		return startTime;
	}
}
