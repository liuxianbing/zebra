package com.simclouds.unicom.jasper.soap;

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
        
//        List<String> list = jasperClient.getTerminals();
//        
//        System.out.println(list.size());
//        Map<String, String> detailMap = jasperClient.getTerminalDetails(iccid);
//        System.out.println(detailMap.get("ratePlan"));
        
//        Map<String, String> infoMap = jasperClient.getSessionInfo(iccid);
//        System.out.println(infoMap.get("ipAddress"));
        
        String changeType = "4";
        String targetValue = "000";
        System.out.println(jasperClient.editTerminal(iccid, changeType, targetValue));
        
    }
}
