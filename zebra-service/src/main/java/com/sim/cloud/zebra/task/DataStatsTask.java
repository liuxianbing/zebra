package com.sim.cloud.zebra.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Autowired
	private SimCardService simCardService;

	// total data yesterday
	Map<String, Float> totalDataMap = new HashMap<String, Float>();

	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Scheduled(cron="0 10 0 * * *") // 00:10:00 every day
	public void syncDayData() {
		logger.info("############## Start sync day data. #######################");
		
		// get users
		Map<String, String> accounts = ZebraConfig.getAccounts();
		
		String cycleStartDate = getYesterday();
		StatisCardFlow dayData = null;
		// get cards
		for (String value : accounts.values()) {
			try {
				JasperClient jasperClient = JasperClient.getInstance(value);
				
				List<SimCard> cardList = simCardService.selectByAccount("unicom." + value.split(":")[0]);
				
				// save to db
				for (SimCard sim : cardList) {
					try {
						Float data = jasperClient.getTerminalUsageDataDetails(sim.getIccid(), cycleStartDate);
						
						// save day data
						if (data != 0.0f) {
							dayData = new StatisCardFlow();
							dayData.setDay(cycleStartDate);
							dayData.setFlow(data / 1024);
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
		
		logger.info("############## End sync day data. #######################");
	}
	
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
	
}
