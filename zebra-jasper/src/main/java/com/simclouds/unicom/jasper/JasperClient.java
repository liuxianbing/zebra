package com.simclouds.unicom.jasper;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import com.simclouds.unicom.jasper.soap.TerminalClient;

/**
 * Jasper API Client
 * 
 * @author henrylv
 *
 * @since 2017-09-26 10:58
 */
public class JasperClient {
	private static TerminalClient terminalClient = null;
	
    private static Map<String, JasperClient> jasperClientMap = new HashMap<String, JasperClient>();
    
    private JasperClient(String licenseKey, String username, String password) throws SOAPException, MalformedURLException {
    	try {
    		terminalClient = new TerminalClient(licenseKey, username, password);
    		
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    /**
     * get jasper client
     * 
     * @param url
     * @param licenseKey
     * @param username
     * @param password
     * @throws SOAPException 
     * @throws MalformedURLException 
     */
    public static JasperClient getInstance(String licenseKey, String username, String password) throws MalformedURLException, SOAPException {
    	JasperClient jasperClient = jasperClientMap.get(licenseKey);
    	if (jasperClient == null) {
    		jasperClient = new JasperClient(licenseKey, username, password);
    		
    		jasperClientMap.put(licenseKey, jasperClient);
    	}
    	
    	return jasperClient;
    }
    
    /**
     * get terminals
     */
    public List<String> getTerminals() {
    	try {
			return terminalClient.getModifiedTerminals();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * get terminal details
     * 
     * @param iccid
     */
    public Map<String, String> getTerminalDetails(String iccid) {
    	try {
    		return terminalClient.getTerminalDetails(iccid);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * get session info
     * 
     * @param iccid
     */
    public Map<String, String> getSessionInfo(String iccid) {
    	try {
    		return terminalClient.getSessionInfo(iccid);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }
    
    /**
     * edit terminal
     * 
     * @param iccid
     * @param changeType
     * @param targetValue
     */
    public Boolean editTerminal(String iccid, String changeType, String targetValue) {
    	try {
    		return terminalClient.editTerminal(iccid, changeType, targetValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return false;
    }
    
}
