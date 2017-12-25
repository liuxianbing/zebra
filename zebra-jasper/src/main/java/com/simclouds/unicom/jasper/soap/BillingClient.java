package com.simclouds.unicom.jasper.soap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.sun.xml.wss.XWSSecurityException;

/**
 * Terminal client
 * 
 * @author henrylv
 *
 */
public class BillingClient extends AbstractClient {
	//private static Logger log = Logger.getLogger(BillingClient.class);
	private static Logger log = LogManager.getLogger(BillingClient.class);
	
    /**
     * Constructor which initializes Soap Connection, messagefactory and ProcessorFactory
     *
     * @param url
     * @throws SOAPException
     * @throws MalformedURLException
     * @throws XWSSecurityException
     */
    public BillingClient(String licenseKey, String username, String password)
            throws SOAPException, MalformedURLException, XWSSecurityException {
    	super(BILLING_API, licenseKey, username, password);
    }

    /********************************************************************************************************************
     * This method creates a GetTerminalUsageDataDetails Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createGetTerminalUsageDataDetailsRequest(String iccid, String cycleStartDate, String pageNumber) throws SOAPException {
    	SOAPMessage message = this.createBaseRequest(BILLING_API, "GetTerminalUsageDataDetails");
        
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBodyElement terminalRequestElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        
        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
        SOAPElement iccidElement = terminalRequestElement.addChildElement(iccidName);
        iccidElement.setValue(iccid);
        
        Name cycleStartDateName = envelope.createName("cycleStartDate", PREFIX, NAMESPACE_URI);
        SOAPElement cycleStartDateElement = terminalRequestElement.addChildElement(cycleStartDateName);
        cycleStartDateElement.setValue(cycleStartDate);
        
        Name pageNumberName = envelope.createName("pageNumber", PREFIX, NAMESPACE_URI);
        SOAPElement pageNumberElement = terminalRequestElement.addChildElement(pageNumberName);
        pageNumberElement.setValue(pageNumber);
        
        return message;
    }
    
    /**
     * Gets the GetTerminalUsageDataDetails response.
     *
     * @param message
     * @throws SOAPException
     */
    private Float writeGetTerminalUsageDataDetailsResponse(String cycleStartDate, SOAPMessage message) throws SOAPException {
    	Float dayData = 0.0f;
    	
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = envelope.createName("GetTerminalUsageDataDetailsResponse", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message.getSOAPBody().getChildElements(terminalResponseName).next();
        
//      Name totalPages = envelope.createName("totalPages", PREFIX, NAMESPACE_URI);
        
        Name usageDetails = envelope.createName("usageDetails", PREFIX, NAMESPACE_URI);
        SOAPBodyElement usageDetailsElement = (SOAPBodyElement) terminalResponseElement.getChildElements(usageDetails).next();
        
        Name usageDetail = envelope.createName("usageDetail", PREFIX, NAMESPACE_URI);
       // SOAPBodyElement usageDetailElement = (SOAPBodyElement) usageDetailsElement.getChildElements(usageDetail).next();
        
        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
        Name sessionStartTimeName = envelope.createName("sessionStartTime", PREFIX, NAMESPACE_URI);
        Name dataVolumeName = envelope.createName("dataVolume", PREFIX, NAMESPACE_URI);
        
        String startTime = null;
        
        Iterator itr = usageDetailsElement.getChildElements(usageDetail);
        while ( itr.hasNext()) {
            SOAPBodyElement usageDetailElement = (SOAPBodyElement) itr.next();
            SOAPBodyElement dataVolume = (SOAPBodyElement) usageDetailElement.getChildElements(dataVolumeName).next();
            
            SOAPBodyElement sessionStartTime = (SOAPBodyElement) usageDetailElement.getChildElements(sessionStartTimeName).next();
            
            System.out.println("start time: " + sessionStartTime.getTextContent());
            System.out.println("data: " + dataVolume.getTextContent());
            
            startTime = sessionStartTime.getTextContent().split("T")[0];
            
            if (startTime.compareTo(cycleStartDate) > 0) {
            	dayData = -1.0f;
            	continue;
            } else if (startTime.compareTo(cycleStartDate) == 0) {
                dayData += Float.parseFloat(dataVolume.getTextContent());
            } else {
            	break;
            }
        }

        return dayData;
    }
    
    /**
     * GetTerminalUsageDataDetails
     * 
     */
    public Float getTerminalUsageDataDetails(String iccid, String cycleStartDate)  throws IOException, XWSSecurityException, SOAPException {
    	Float data = 0.0f;
    	Float temp = null;
    	
    	int i = 1;
    	while (true) {
        	SOAPMessage request = createGetTerminalUsageDataDetailsRequest(iccid, cycleStartDate, String.valueOf(i++));
            request = secureMessage(request, username, password);

            SOAPConnection connection = connectionFactory.createConnection();
            SOAPMessage response = connection.call(request, url);
//            response.writeTo(System.out);
//            System.out.println();
            
            if (!response.getSOAPBody().hasFault()) {
            	temp = writeGetTerminalUsageDataDetailsResponse(cycleStartDate, response);
            	if (temp == -1.0f) { // today
            		continue;
            	} else if (temp == 0.0f) { // before yesterday
            		break;
            	} else { // yesterday
            		data += temp;
            	}
            } else {
                SOAPFault fault = response.getSOAPBody().getFault();
                log.error("Method: getTerminalUsageDataDetails Received SOAP Fault, Code:" + fault.getFaultCode() + ", String: " + fault.getFaultString());
                System.out.println("Method: getTerminalUsageDataDetails Received SOAP Fault, Code:" + fault.getFaultCode() + ", String: " + fault.getFaultString());
                
                break;
            }
            
            System.out.println("i: " + i);
    	}
    	
        return data;
    }
    
    /********************************************************************************************************************
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createGetTerminalUsageRequest(String iccid, String cycleStartDate) throws SOAPException {
    	SOAPMessage message = this.createBaseRequest(BILLING_API, "GetTerminalUsage");
        
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBodyElement terminalRequestElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        
        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
        SOAPElement iccidElement = terminalRequestElement.addChildElement(iccidName);
        iccidElement.setValue(iccid);
        
        Name cycleStartDateName = envelope.createName("cycleStartDate", PREFIX, NAMESPACE_URI);
        SOAPElement cycleStartDateElement = terminalRequestElement.addChildElement(cycleStartDateName);
        cycleStartDateElement.setValue(cycleStartDate);
        
        return message;
    }

    /**
     * Gets the terminal response.
     *
     * @param message
     * @throws SOAPException
     */
    private Float writeGetTerminalUsageResponse(SOAPMessage message) throws SOAPException {
    	Float monthData = 0.0f;
    	
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = envelope.createName("GetTerminalUsageResponse", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message
                .getSOAPBody().getChildElements(terminalResponseName).next();
        
        Name totalDataVolumeName = envelope.createName("totalDataVolume", PREFIX, NAMESPACE_URI);
        
        SOAPBodyElement totalDataVolumeElement = (SOAPBodyElement) terminalResponseElement.getChildElements(totalDataVolumeName).next();
        
        monthData = Float.parseFloat(totalDataVolumeElement.getTextContent());
        
        return monthData;
    }
    
    /**
     * get Terminal Usage
     * 
     * @param username
     * @param password
     * @param iccid
     * @throws SOAPException
     * @throws IOException
     * @throws XWSSecurityException
     * @throws Exception
     */
    public Float getTerminalUsage(String iccid, String cycleStartDate) throws SOAPException, IOException, XWSSecurityException, Exception {
        SOAPMessage request = createGetTerminalUsageRequest(iccid, cycleStartDate);
        request = secureMessage(request, username, password);
        
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        response.writeTo(System.out);
        if (!response.getSOAPBody().hasFault()) {
            return writeGetTerminalUsageResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            log.error("Method: getSessionInfo Received SOAP Fault, Code:" + fault.getFaultCode() + ", String: " + fault.getFaultString());
            System.out.println("Method: getSessionInfo Received SOAP Fault, Code:" + fault.getFaultCode() + ", String: " + fault.getFaultString());
        }
        
        return null;
    }
}
