package com.sim.cloud.zebra.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.StatisCardFlowMapper;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.model.StatisCardFlow;
import com.sim.cloud.zebra.service.SimCardService;
import com.simclouds.unicom.jasper.JasperClient;

/**
 * Data statistic
 * 
 * @author henrylv
 *
 */
@Service
@Transactional
public class DataStatsTask extends AbstractService<StatisCardFlowMapper, StatisCardFlow> {
	
	private static Logger log = Logger.getLogger(DataStatsTask.class);
	
	@Autowired
	private SimCardService simCardService;

	// total data yesterday
	Map<String, Float> totalDataMap = new HashMap<String, Float>();
	
	@Scheduled(cron="0 10 0 * * *") // 00:10:00 every day
	//@Scheduled(fixedRate=1000 * 60 * 30) // 30 minutes
	public void syncDayData() {
		log.info("############## Start sync day data. #######################");
		
		// get users
		Map<String, String> accounts = ZebraConfig.getAccounts();
		
		Map<String, Object> params = new HashMap<String, Object>();
		String cycleStartDate = getYesterday();
		StatisCardFlow dayData = null;
		// get cards
		for (String value : accounts.values()) {
			try {
				JasperClient jasperClient = JasperClient.getInstance(value);
				
				params.put("account", "unicom" + value.split(":")[0]);
				List<SimCard> cardList = simCardService.queryList(params);
				
				// save to db
				for (SimCard sim : cardList) {
					try {
						Float data = jasperClient.getTerminalUsageDataDetails(sim.getIccid(), cycleStartDate);
						
						// save day data
						if (data != 0.0f) {
							dayData = new StatisCardFlow();
							dayData.setDay(cycleStartDate);
							dayData.setFlow(data);
							dayData.setIccid(sim.getIccid());
							
							this.insert(dayData);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				System.out.println("error insert day data: " + e.getMessage() + "   ################################");
				e.printStackTrace();
			}
		}
		
		log.info("############## End sync day data. #######################");
		
	}
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * get yesterday 
	 * 
	 * @return
	 */
	private static String getYesterday() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -1);
		
		return dateFormat.format(c.getTime());
	}
	
	// test
	public static void main(String[] args) {
		System.out.println(getYesterday());
	}
	
}
