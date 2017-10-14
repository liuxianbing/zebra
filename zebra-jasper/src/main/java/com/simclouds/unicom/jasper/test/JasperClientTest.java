package com.simclouds.unicom.jasper.test;

import java.util.List;
import java.util.Map;

import com.simclouds.unicom.jasper.JasperClient;

public class JasperClientTest {
	/**
     * test 
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        String key = "2e13a4e9-343a-4c5b-b2b0-17ac3607e0b0";
        String username = "huangjianjun29622";
        String password = "Bjzdc2017@";
        String iccid = "89860617030017575948"; // test
        
        JasperClient jasperClient = JasperClient.getInstance(key, username, password);
        
//        List<String> list = jasperClient.getTerminals("2017-10-14T00:00:00Z");
//        
//        System.out.println(list.size());
//        List<Map<String, String>> detailsList = jasperClient.getTerminalDetails(new String[]{iccid, "89860617020031095783"});
//        System.out.println(detailsList.get(1).get("ratePlan"));
//        System.out.println(detailsList.get(0).get("ratePlan"));
        
        Map<String, String> infoMap = jasperClient.getSessionInfo(iccid);
        System.out.println(infoMap.get("ipAddress"));
        
        // ACTIVATION_READY_NAME: 可激活
        
//        String changeType = "3"; // 2: modemID, 3, terminal status, 4: ratePlan
//        String targetValue = "ACTIVATION_READY_NAME";
//        System.out.println(jasperClient.editTerminal(iccid, changeType, targetValue));
        
    }
}
