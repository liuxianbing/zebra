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

	public static Map<String, Integer> getStatusIntegerMap() {
		Map<String, Integer> statusMap = new HashMap<String, Integer>();
		
		statusMap.put("TEST_READY_NAME", 1); // 可测试
		statusMap.put("INVENTORY_NAME", 2); // 库存
		statusMap.put("ACTIVATION_READY_NAME", 3); // 可激活
		//statusMap.put("TRIAL_NAME", 3); // ？？？
		statusMap.put("ACTIVATED_NAME", 4);  // 已激活
		statusMap.put("DEACTIVATED_NAME", 5); // 已失效
		statusMap.put("RETIRED_NAME", 6); // 已停用
		//statusMap.put("PURGED_NAME", 7); // ???
		
		return statusMap;
	}
	
	public static Map<String, String> getStatusStringMap() {
		Map<String, String> statusMap = new HashMap<String, String>();
		
		statusMap.put("STATUS_1", "TEST_READY_NAME"); // 可测试
		statusMap.put("STATUS_2", "INVENTORY_NAME"); // 库存
		statusMap.put("STATUS_3", "ACTIVATION_READY_NAME"); // 可激活
		
		//statusMap.put("STATUS_3", "TRIAL_NAME"); // ？？？
		statusMap.put("STATUS_4", "ACTIVATED_NAME");  // 已激活
		statusMap.put("STATUS_5", "DEACTIVATED_NAME"); // 已失效
		statusMap.put("STATUS_6", "RETIRED_NAME"); // 已停用
		//statusMap.put("STATUS_7", "PURGED_NAME"); // ???
		
		return statusMap;
	}
}
