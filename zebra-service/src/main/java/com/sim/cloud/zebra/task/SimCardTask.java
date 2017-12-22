package com.sim.cloud.zebra.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimCardMapper;
import com.sim.cloud.zebra.model.SimCard;
import com.sim.cloud.zebra.service.SimCardService;
import com.simclouds.unicom.jasper.JasperClient;

/**
 * 
 * @author henrylv
 *
 */
@Service
@Transactional
public class SimCardTask extends AbstractService<SimCardMapper, SimCard> {

	private static Logger log = Logger.getLogger(SimCardTask.class);
	
	@Autowired
	private SimCardService simCardService;
	
	private Date lastSyncTime = null;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
	
	@Scheduled(initialDelay=1000 * 60 * 1, fixedRate=1000 * 60 * 30) // 30 minutes
	public void syncUnicomSimCards() {
		log.info("############## Start sync simcard data. #######################");
		
		// get users
		Map<String, String> accounts = ZebraConfig.getAccounts();
		
		// start time
		//String startTime = null;
//		if (lastSyncTime != null) {
//			startTime = this.getStartTime(lastSyncTime);
//		} 
//		lastSyncTime = new Date();
		
		// get cards
		for (String value : accounts.values()) {
			try {
				JasperClient jasperClient = JasperClient.getInstance(value);
				
				List<SimCard> sims = jasperClient.getTerminals(null);
				System.out.println(value + ": sim card number:" + sims.size() + "   ################################");
				
				// save to db
				for (SimCard sim : sims) {
					/**
					 *  TODO get sim by iccid
					 *  
					 *  if exist then update
					 *  or not save
					 */
					try {
						// sim.setId(id);
						sim.setId(simCardService.selectIdByIccid(sim.getIccid()));
						this.insertOrUpdate(sim);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				System.out.println("error insert sim card: " + e.getMessage() + "   ################################");
				e.printStackTrace();
			}
		}
		
		log.info("############## End sync simcard data. #######################");
	}
	
	/**
	 * get start time
	 * 
	 * @return
	 */
	private String getStartTime(Date lastSyncTime) {
		// start time
//		Calendar c = Calendar.getInstance();
//		c.setTime(lastSyncTime);
		//c.add(Calendar.MINUTE, -40);
		String startTime = dateFormat.format(lastSyncTime);
		
		return startTime;
	}
}
