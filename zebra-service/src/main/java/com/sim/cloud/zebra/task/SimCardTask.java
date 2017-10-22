package com.sim.cloud.zebra.task;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sim.cloud.zebra.common.util.ZebraConfig;
import com.sim.cloud.zebra.core.AbstractService;
import com.sim.cloud.zebra.mapper.SimCardMapper;
import com.sim.cloud.zebra.model.SimCard;
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
	
	
	//@Scheduled(fixedRate=1000 * 60 * 1) // 30 minutes
	public void syncUnicomSimCards() {
		log.info("############## Start sync simcard data. #######################");
		
		// get users
		Map<String, String> accounts = ZebraConfig.getAccounts();
		
		// get cards
		String username = null;
		String password = null;
		String licenseKey = null;
		String apiKey = null; // TODO
		
		String[] ss = null;
		for (String value : accounts.values()) {
			try {
				ss = value.split(":");
				username = ss[0];
				password = ss[1];
				licenseKey = ss[2];
				apiKey = ss[3];
				System.out.println("#################test ######################: " + username + ", " + password + ", " + licenseKey);
				JasperClient jasperClient = JasperClient.getInstance(licenseKey, username, password);
				
				List<SimCard> sims = jasperClient.getTerminals(null);
				System.out.println("sim card number:" + sims.size() + "   ################################");
				
				// save to db
				for (SimCard sim : sims) {
					/**
					 *  TODO get sim by iccid
					 *  
					 *  if exist then update
					 *  or not save
					 */
					try {
						// TODO sim.setId(id);
					
						this.insertOrUpdate(sim);
					} catch (Exception e) {
						// TODO 
					}
				}
			} catch (Exception e) {
				System.out.println("error insert sim card: " + e.getMessage() + "   ################################");
				e.printStackTrace();
			}
		}
		
		log.info("############## End sync simcard data. #######################");
	}
}
