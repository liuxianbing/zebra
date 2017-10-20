package com.sim.cloud.zebra.service;

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
import com.simclouds.unicom.jasper.JasperClient;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午1:03:18 
* 类说明 
*/
@Service
@Transactional
public class SimCardService extends AbstractService<SimCardMapper, SimCard> {

	private static Logger log = Logger.getLogger(SimCardService.class);
	
	/**
	 * change SimCard status
	 */
	public void changeStatus() {
		// TODO
	}
	
	/**
	 * change SimCard rate plan
	 */
	public void changeRatePlan() {
		// TODO
	}
	
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
