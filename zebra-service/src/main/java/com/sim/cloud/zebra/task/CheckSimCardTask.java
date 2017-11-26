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
public class CheckSimCardTask extends AbstractService<SimCardMapper, SimCard> {

	private static Logger log = Logger.getLogger(CheckSimCardTask.class);
	
	@Autowired
	private SimCardService simCardService;
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	@Scheduled(cron="0 30 0 * * *") // 00:30:00 every day
	//@Scheduled(initialDelay=1000 * 60 * 10, fixedRate=1000 * 60 * 30) // 30 minutes
	public void syncUnicomSimCards() {
		log.info("############## Start check simcard status. #######################");
		
		// get users
		Map<String, String> accounts = ZebraConfig.getAccounts();
				
		// TODO get sim card has expired
		List<SimCard> simcardList = simCardService.selectNearlyExpireCards(getExpireDay());
		
		// change status
		for (SimCard simcard : simcardList) {
			simcard.setStatus(6); // 已停用 JasperUtils.getStatusIntegerMap().get("RETIRED_NAME")
			
			// set status to jasper api
			JasperClient jasperClient = JasperClient.getInstance(accounts.get(simcard.getAccount()));
			jasperClient.changeStatus(simcard.getIccid(), "RETIRED_NAME");
			
			this.insertOrUpdate(simcard);
		}
		
		log.info("############## End check simcard status. #######################");
	}
	
	/**
	 * get expire day 
	 * 
	 * @return
	 */
	private String getExpireDay() {
		String expireDay = dateFormat.format(new Date());
		
		return expireDay;
	}
}
