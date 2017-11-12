package com.sim.cloud.zebra.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年11月11日 上午11:52:26 
* 类说明 
*/
public enum FinanceEnum {

	CHONGZHI(0,"充值"),
	BUY_CARD(1,"购卡");
	
	
	private int type;
	private String typeStr;
	
	private FinanceEnum(int type, String typeStr) {
		this.type = type;
		this.typeStr = typeStr;
	}
	
	public int getType() {
		return type;
	}

	public String getTypeStr() {
		return typeStr;
	}

	private static Map<Integer, FinanceEnum> map = new HashMap<Integer, FinanceEnum>();//将code和name存到map，用于根据code获取name
    private static List<FinanceEnum> list = new ArrayList<FinanceEnum>();
    static {
        for (FinanceEnum spaceEnum : FinanceEnum.values()) {
            map.put(spaceEnum.type, spaceEnum);
            list.add(spaceEnum);
        }
    }
    
    public static FinanceEnum getEnumByStatus(int status) {
        return map.get(status);
    }
}
