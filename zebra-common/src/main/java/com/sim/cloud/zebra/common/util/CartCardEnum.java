package com.sim.cloud.zebra.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月31日 下午2:54:41 
* 类说明  提交订单 –> 待付款->支付成功 –> 待审核 –> 审核成功 -> 待发货 –> 发货->完成
*/
public enum CartCardEnum {
	CHECKFAIL_ORDER(-1,"审核未通过"),
	BASIC_ORDER(0,"购物车清单"),
	SUBMIT_ORDER(1,"提交订单"),
	WAITPAY_ORDER(2,"待付款"),
	PAYOK_ORDER(3,"支付成功"),
	WAITCHECK_ORDER(4,"待审核"),
	CHECKOK_ORDER(5,"审核成功"),
	WAITDELIVER_ORDER(6,"待发货 "),
	DELIVEROK_ORDER(7,"发货 "),
	SUCCESS_ORDER(8,"完成 ");
	
	
	
	private int status;
	private String statusStr;
	
	private CartCardEnum(int status, String statusStr) {
		this.status = status;
		this.statusStr = statusStr;
	}
	
	public String getStatusStr() {
		return statusStr;
	}

	private static Map<Integer, CartCardEnum> map = new HashMap<Integer, CartCardEnum>();//将code和name存到map，用于根据code获取name
    private static List<CartCardEnum> list = new ArrayList<CartCardEnum>();
    static {
        for (CartCardEnum spaceEnum : CartCardEnum.values()) {
            map.put(spaceEnum.status, spaceEnum);
            list.add(spaceEnum);
        }
    }
    
    public static CartCardEnum getEnumByStatus(int status) {
        return map.get(status);
    }
    

	public int getStatus() {
		return status;
	}
	
}
