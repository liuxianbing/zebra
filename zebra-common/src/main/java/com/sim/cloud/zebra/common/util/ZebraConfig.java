package com.sim.cloud.zebra.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/** 
* @author liuxianbing: 
* @version 创建时间：2017年10月14日 下午2:45:27 
* 类说明 
*/
public class ZebraConfig {
	
	private static final Object monitor=new Object();
	
	private static Map<String,String> accountMaps=null;
	
	public static  Map<String,String> getAccounts(){
		if(accountMaps==null){
			synchronized(monitor){
				if(accountMaps==null){
					accountMaps=new HashMap<>();
					Properties pro=PropertiesFileReader.getProperties("account.properties");
					pro.entrySet().stream().forEach(e->{
						accountMaps.put(e.getKey().toString(), e.getValue().toString());
					});
				}
			}
		}
		return accountMaps;
		
	}
}
