package com.sim.cloud.zebra.service;

import java.util.List;
import java.util.Map;

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

	@Autowired
	private SimCardMapper simCardMapper;
	
	
	
	
	
	
	@Scheduled(fixedRate=1000 * 60 * 30) // 30 minutes
	private void syncUnicomSimCards() {
		System.out.println("aaaaaaaaaaa: ####################################################");
		// get users
		Map<String, String> accounts = ZebraConfig.getAccounts();
		// get cards
		String username = null;
		String password = null;
		String licenseKey = null;
		String apiKey = null;
		String[] ss = null;
		for (String value : accounts.values()) {
			ss = value.split(":");
			username = ss[0];
			password = ss[1];
			licenseKey = ss[2];
			apiKey = ss[3];
			
			JasperClient jasperClient = JasperClient.getInstance(licenseKey, username, password);
			
			List<SimCard> sims = jasperClient.getTerminals(null);

			// TODO save to db
			
			
		}
		
		
		
		
	}
}
