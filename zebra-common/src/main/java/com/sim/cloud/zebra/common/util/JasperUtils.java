package com.sim.cloud.zebra.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * jasper client utils
 * 
 * @author henrylv
 *
 */
public class JasperUtils {

	public static Map<String, Integer> getStatusMap() {
		Map<String, Integer> statusMap = new HashMap<String, Integer>();
		
		statusMap.put("ACTIVATION_READY_NAME", 0); // 已激活
		// TODO
		
		
		return statusMap;
	}
}
