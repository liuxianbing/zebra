package com.sim.cloud.zebra.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 下午2:54:41 
* 类说明 
*/
public enum CardDeviceStatusEnum {

	TEST_READY_NAME(1, "TEST_READY_NAME", "可测试"),
	INVENTORY_NAME(2, "INVENTORY_NAME", "库存"),
	ACTIVATION_READY_NAME(3, "ACTIVATION_READY_NAME", "可激活"),
	ACTIVATED_NAME(4, "ACTIVATED_NAME", "已激活"),
	DEACTIVATED_NAME(5, "DEACTIVATED_NAME", "已失效"),
	RETIRED_NAME(6, "RETIRED_NAME", "已停用");
	
	
	private int status;
	private String jasperStatus;
	private String simStatus;
	
	private CardDeviceStatusEnum(int status, String jasperStatus, String simStatus) {
		this.status = status;
		this.jasperStatus = jasperStatus;
		this.simStatus = simStatus;
	}
	
	public static Map<Integer, CardDeviceStatusEnum> map = new HashMap<Integer, CardDeviceStatusEnum>();//将code和name存到map，用于根据code获取name
	private static Map<String, CardDeviceStatusEnum> vrMap = new HashMap<String, CardDeviceStatusEnum>();//将code和name存到map，用于根据code获取name
	private static List<CardDeviceStatusEnum> list = new ArrayList<CardDeviceStatusEnum>();
    static {
        for (CardDeviceStatusEnum spaceEnum : CardDeviceStatusEnum.values()) {
            map.put(spaceEnum.status, spaceEnum);
            vrMap.put(spaceEnum.jasperStatus, spaceEnum);
            list.add(spaceEnum);
        }
    }
    
    public static CardDeviceStatusEnum getEnumByStatus(int status) {
        return map.get(status);
    }
    
    public static CardDeviceStatusEnum getEnumByJasperStatus(String jasperStatus) {
        return vrMap.get(jasperStatus);
    }

	public int getStatus() {
		return status;
	}

	public String getJasperStatus() {
		return jasperStatus;
	}

	public String getSimStatus() {
		return simStatus;
	}
	
	
}
