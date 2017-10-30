package com.simclouds.unicom.jasper;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPException;

import org.apache.log4j.Logger;

import com.sim.cloud.zebra.common.util.Constants;
import com.sim.cloud.zebra.model.SimCard;
import com.simclouds.unicom.jasper.rest.SmsClient;
import com.simclouds.unicom.jasper.soap.BillingClient;
import com.simclouds.unicom.jasper.soap.TerminalClient;
import com.sun.xml.wss.XWSSecurityException;

/**
 * Jasper API Client
 * 
 * @author henrylv
 *
 * @since 2017-09-26 10:58
 */
public class JasperClient {
	private static Logger log = Logger.getLogger(JasperClient.class);
	
	private static TerminalClient terminalClient = null;
	
	private static SmsClient smsClient = null;
	
	private static BillingClient billingClient = null;
	
    private static Map<String, JasperClient> jasperClientMap = new HashMap<String, JasperClient>();
    
    private static Map<String, Integer> typeMap = new HashMap<String, Integer>();
    
    // client
    private JasperClient(String licenseKey, String username, String password) throws MalformedURLException, SOAPException, XWSSecurityException {
    	
    	terminalClient = new TerminalClient(licenseKey, username, password);
    	
//    	smsClient = null; // TODO 
    	
    	billingClient = new BillingClient(licenseKey, username, password);
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
    public static JasperClient getInstance(String licenseKey, String username, String password, String apiKey, Integer type) {
    	JasperClient jasperClient = jasperClientMap.get(licenseKey);
    	if (jasperClient == null) {
    		try {
    			typeMap.put(username, type);
    			
    			jasperClient = new JasperClient(licenseKey, username, password);
    			
    			jasperClientMap.put(licenseKey, jasperClient);
        	} catch (Exception e) {
        		log.error("create jasper client failed", e);
        	}
    	}
    	
    	return jasperClient;
    }
    
    /**
     * get jasper client
     * 
     * @param accountValue
     * @return
     */
    public static JasperClient getInstance(String accountValue) {
    	String[] ss = accountValue.split(":");
    	String licenseKey = ss[2];
    		
    	JasperClient jasperClient = jasperClientMap.get(licenseKey);
    	if (jasperClient == null) {
    		String username = ss[0];
        	String password = ss[1];
        	String apiKey = ss[3];
        	Integer type = Integer.parseInt(ss[4]);
        	
        	jasperClient = getInstance(licenseKey, username, password, apiKey, type);
//        	
//        	typeMap.put(username, type);
//        	
//    		try {
//    			jasperClient = new JasperClient(licenseKey, username, password);
//    			
//    			jasperClientMap.put(licenseKey, jasperClient);
//        	} catch (Exception e) {
//        		log.error("create jasper client failed", e);
//        	}
    	}
    	
    	return jasperClient;
    }
    
    /**
     * get terminal ICCIDs
     * 
     * @param startTime
     *  format: 2008-08-26T00:00:00Z
     *  
     * @return
     */
    public List<String> getTerminalICCIDs(String startTime) {
    	try {
			return terminalClient.getModifiedTerminals(startTime);
		} catch (Exception e) {
			log.error("get terminal list failed", e);
		}
    	
    	return null;
    }
    
    /**
     * get terminals
     * 
     * @param startTime
     *  format: 2008-08-26T00:00:00Z
     *  
     * @return
     */
    public List<SimCard> getTerminals(String startTime) {
    	List<SimCard> simCards = null;
    	
    	try {
    		List<String> iccids = terminalClient.getModifiedTerminals(startTime);
    		
    		simCards = getTerminalDetails(iccids);
		} catch (Exception e) {
			log.error("get terminal list failed", e);
		}
    	
    	return simCards;
    }
    

    /**
     * get terminal detail
     * 
     * @param iccid
     */
    public SimCard getTerminalDetail(String iccid) {
    	try {
    		List<String> iccids = new ArrayList<String>();
    		iccids.add(iccid);
    		
    		List<SimCard> simCards = this.getTerminalDetails(iccids);
    		
    		return simCards.get(0);
		} catch (Exception e) {
			log.error("get terminal detail failed", e);
		}
    	
    	return null;
    }
    
    /**
     * get terminal details
     * 
     * @param iccid
     */
    public List<SimCard> getTerminalDetails(List<String> iccids) {
    	List<SimCard> simCards = new ArrayList<SimCard>();
    	
    	try {
    		List<Map<String, String>> terminalList = terminalClient.getTerminalDetails(iccids);
    		SimCard simCard = null;
    		for (Map<String, String> terminalMap : terminalList) {
    			simCard = new SimCard();
    			simCard.setIccid(terminalMap.get("iccid")); // iccid
    			simCard.setUsedFlow(Float.valueOf(terminalMap.get("monthToDateDataUsage"))); // data usage
    			simCard.setUsedMessages(Integer.parseInt(terminalMap.get("monthToDateSMSUsage"))); // message usage
    			simCard.setUsedMinutes(Integer.parseInt(terminalMap.get("monthToDateVoiceUsage"))); // voice usage
    			simCard.setObjType(Constants.statusIntegerMap.get(terminalMap.get("status"))); // status
    			
    			// net type
    			if (simCard.getObjType() == 1 || simCard.getObjType() == 3 || simCard.getObjType() == 4) {
    				simCard.setNetType(1);
    			} else {
    				simCard.setNetType(0);
    			}
    			
    			simCard.setOperator(3); // unicom
    			simCard.setPhone(terminalMap.get("msisdn"));
    			simCard.setAccount("unicom." + terminalClient.getUsername());
    			simCard.setLastSyncTime(new Date().toLocaleString());
    			simCard.setType(typeMap.get(terminalClient.getUsername())); // type
    			simCard.setTerminalId(terminalMap.get("terminalId"));
    			
    			simCards.add(simCard);
    		}
		} catch (Exception e) {
			log.error("get terminal detail failed", e);
		}
    	
    	return simCards;
    }

    private Map<String, String> getTerminalDetailMap(String iccid) {
    	List<String> iccids = new ArrayList<String>();
		iccids.add(iccid);
		
		try {
    		List<Map<String, String>> terminalList = terminalClient.getTerminalDetails(iccids);
    		
    		return terminalList.get(0);
		} catch (Exception e) {
			log.error("getTerminalDetailMap failed", e);
		}
    	
		return null;
    }
    
    /**
     * get terminal rate plan
     * 
     * @param iccid
     * @return
     */
    public String getTerminalRatePlan(String iccid) {
    	Map<String, String> detailMap = this.getTerminalDetailMap(iccid);
    	
    	return detailMap.get("ratePlan");
    }
    
    /**
     * get current month data usage
     * 
     * @param iccid
     * @return
     */
    public String getTerminalDataUsage(String iccid) {
    	Map<String, String> detailMap = this.getTerminalDetailMap(iccid);
    	
    	return detailMap.get("monthToDateDataUsage");
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
			log.error("get terminal session info failed", e);
		}
    	
    	return null;
    }
    
    /**
     * edit terminal
     * 
     * @param iccid
     * @param changeType 
     * 	3: terminal status
     *  4: ratePlan
     * @param targetValue
     */
    public Boolean editTerminal(String iccid, String changeType, String targetValue) {
    	try {
    		return terminalClient.editTerminal(iccid, changeType, targetValue);
		} catch (Exception e) {
			log.error("edit terminal failed", e);
		}
    	
    	return false;
    }
    
    /**
	 * change SimCard status
	 */
	public List<String> changeStatus(List<String> iccids, String status) {
		List<String> failList = new ArrayList<String>();
		
		for(String iccid : iccids) {
			if (!this.editTerminal(iccid, "3", status)) {
				failList.add(iccid);
			}
		}
		
		return failList;
	}
	
	/**
	 * change SimCard rate plan
	 */
	public List<String> changeRatePlan(List<String> iccids, String ratePlan) {
		List<String> failList = new ArrayList<String>();
		
		for(String iccid : iccids) {
			if (!this.editTerminal(iccid, "4", ratePlan)) {
				failList.add(iccid);
			}
		}
		
		return failList;
	}
	
	/**
	 * get terminal usage data details
	 * 
	 * @param iccid
	 * @param cycleStartDate
	 * @param pageNumber
	 */
	public Float getTerminalUsageDataDetails(String iccid, String cycleStartDate) {
		Float data = null;
		try {
			data = billingClient.getTerminalUsageDataDetails(iccid, cycleStartDate);
		} catch (Exception e) {
			log.error("getTerminalUsageDataDetails failed", e);
		}
		
		return data;
	}
    
    /**
     * get sms list
     * 
     * @return
     */
    public List<String> searchSms() {
    	// TODO 
    	
    	return null;
    }
    
    /**
     * get sms detail
     * 
     * @return
     */
    public Object getSmsDetail() {
    	// TODO
    	
    	return null;
    }
    
    /**
     * send sms
     * 
     * @return
     */
    public Boolean sendSms() {
    	// TODO
    	
    	return false;
    }
}
