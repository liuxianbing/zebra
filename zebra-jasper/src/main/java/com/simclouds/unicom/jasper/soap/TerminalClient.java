package com.simclouds.unicom.jasper.soap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.sun.xml.wss.XWSSecurityException;

/**
 * Terminal client
 * 
 * @author henrylv
 *
 */
public class TerminalClient extends AbstractClient {
	
    /**
     * Constructor which initializes Soap Connection, messagefactory and ProcessorFactory
     *
     * @param url
     * @throws SOAPException
     * @throws MalformedURLException
     * @throws XWSSecurityException
     */
    public TerminalClient(String licenseKey, String username, String password)
            throws SOAPException, MalformedURLException, XWSSecurityException {
    	super(TERMINAL_API, licenseKey, username, password);
    }

    /********************************************************************************************************************
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createGetModifiedTerminalsRequest() throws SOAPException {
        SOAPMessage message = this.createBaseRequest(TERMINAL_API, "GetModifiedTerminals");
        
        return message;
    }

    /**
     * Gets the terminal response.
     *
     * @param message
     * @throws SOAPException
     */
    private List<String> writeGetModifiedTerminalsResponse(SOAPMessage message) throws SOAPException {
    	List<String> iccids = new ArrayList<String>();
    	
        // iccids 
        NodeList iccidList = message.getSOAPBody().getFirstChild().getLastChild().getPreviousSibling().getChildNodes();
        for (int i = 0; i < iccidList.getLength(); i++) {
        	iccids.add(iccidList.item(0).getTextContent());
        }
        
        return iccids;
    }
    
    /**
     * get terminals
     * @throws SOAPException 
     * @throws IOException 
     * @throws XWSSecurityException 
     */
    public List<String> getModifiedTerminals() throws SOAPException, IOException, XWSSecurityException {
    	SOAPMessage request = createGetModifiedTerminalsRequest();
        request = secureMessage(request, username, password);
        
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        
        if (!response.getSOAPBody().hasFault()) {
        	return writeGetModifiedTerminalsResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code :" + fault.getFaultCode());
            System.err.println("SOAP Fault String :" + fault.getFaultString());
        }
        
        return null;
    }
    
    /********************************************************************************************************************
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createGetTerminalDetailsRequest(String iccid) throws SOAPException {
    	SOAPMessage message = this.createBaseRequest(TERMINAL_API, "GetTerminalDetails");
        
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBodyElement terminalRequestElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        
        Name iccids = envelope.createName("iccids", PREFIX, NAMESPACE_URI);
        SOAPElement iccidsElement = terminalRequestElement.addChildElement(iccids);
        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
        SOAPElement iccidElement = iccidsElement.addChildElement(iccidName);
        iccidElement.setValue(iccid);
        
        return message;
    }

    /**
     * Gets the terminal response.
     *
     * @param message
     * @throws SOAPException
     */
    private Map<String, String> writeGetTerminalDetailsResponse(SOAPMessage message) throws SOAPException {
    	Map<String, String> detailMap = new HashMap<String, String>();
    	
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = envelope.createName("GetTerminalDetailsResponse", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message
                .getSOAPBody().getChildElements(terminalResponseName).next();
        
        Name terminals = envelope.createName("terminals", PREFIX, NAMESPACE_URI);
        Name terminal = envelope.createName("terminal", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalsElement = (SOAPBodyElement) terminalResponseElement.getChildElements(terminals).next();
        SOAPBodyElement terminalElement = (SOAPBodyElement) terminalsElement.getChildElements(terminal).next();
        
        NodeList list = terminalElement.getChildNodes();
        Node n = null;
        for (int i = 0; i < list.getLength(); i ++) {
            n = list.item(i);
            detailMap.put(n.getLocalName(), n.getTextContent());
        }

        return detailMap;
    }

    /**
     * get terminal details 
     * 
     * @param iccid
     * @throws SOAPException
     * @throws IOException
     * @throws XWSSecurityException
     * @throws Exception
     */
    public Map<String, String> getTerminalDetails(String iccid) throws SOAPException, IOException, XWSSecurityException, Exception {
        SOAPMessage request = createGetTerminalDetailsRequest(iccid);
        request = secureMessage(request, username, password);
        
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        
        if (!response.getSOAPBody().hasFault()) {
        	return writeGetTerminalDetailsResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code :" + fault.getFaultCode());
            System.err.println("SOAP Fault String :" + fault.getFaultString());
        }
        
        return null;
    }

    /********************************************************************************************************************
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createGetSessionInfoRequest(String iccid) throws SOAPException {
    	SOAPMessage message = this.createBaseRequest(TERMINAL_API, "GetSessionInfo");
        
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBodyElement terminalRequestElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        
        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
        SOAPElement iccidElement = terminalRequestElement.addChildElement(iccidName);
        iccidElement.setValue(iccid);
        
        return message;
    }

    /**
     * Gets the terminal response.
     *
     * @param message
     * @throws SOAPException
     */
    private Map<String, String> writeGetSessionInfoResponse(SOAPMessage message) throws SOAPException {
    	Map<String, String> infoMap = new HashMap<String, String>();
    	
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name terminalResponseName = envelope.createName("GetSessionInfoResponse", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalResponseElement = (SOAPBodyElement) message
                .getSOAPBody().getChildElements(terminalResponseName).next();
        
        Name terminals = envelope.createName("sessionInfo", PREFIX, NAMESPACE_URI);
        Name terminal = envelope.createName("session", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalsElement = (SOAPBodyElement) terminalResponseElement.getChildElements(terminals).next();
        boolean found = false;
        Iterator itr = terminalsElement.getChildElements(terminal);
        while ( itr.hasNext()) {
            SOAPBodyElement terminalElement = (SOAPBodyElement) itr.next();
            found = true;
            NodeList list = terminalElement.getChildNodes();
            Node n = null;
            for (int i = 0; i < list.getLength(); i ++) {
                n = list.item(i);
                if ( n.getLocalName() != null && !"null".equals(n.getLocalName()))
                	infoMap.put(n.getLocalName(), n.getTextContent());
            }
        }
        
        if (!found) {
            System.out.println("No session found for ICCID ");
        }
        
        return infoMap;
    }
    
    /**
     * get session info
     * 
     * @param username
     * @param password
     * @param iccid
     * @throws SOAPException
     * @throws IOException
     * @throws XWSSecurityException
     * @throws Exception
     */
    public Map<String, String> getSessionInfo(String iccid) throws SOAPException, IOException, XWSSecurityException, Exception {
        SOAPMessage request = createGetSessionInfoRequest(iccid);
        request = secureMessage(request, username, password);
        System.out.println("Request: ");
        request.writeTo(System.out);
        System.out.println("");
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        System.out.println("Response: ");
        response.writeTo(System.out);
        System.out.println("");
        if (!response.getSOAPBody().hasFault()) {
            return writeGetSessionInfoResponse(response);
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code :" + fault.getFaultCode());
            System.err.println("SOAP Fault String :" + fault.getFaultString());
        }
        
        return null;
    }

    /********************************************************************************************************************
     * This method creates a Terminal Request and sends back the SOAPMessage.
     * ICCID value is passed into this method
     *
     * @return SOAPMessage
     * @throws SOAPException
     */
    private SOAPMessage createEditTerminalRequest(String iccid, String changeType, String targetValue) throws SOAPException {
    	SOAPMessage message = this.createBaseRequest(TERMINAL_API, "EditTerminal");
        
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        SOAPBodyElement terminalRequestElement = (SOAPBodyElement) message.getSOAPBody().getFirstChild();
        
        Name iccidName = envelope.createName("iccid", PREFIX, NAMESPACE_URI);
        SOAPElement iccidElement = terminalRequestElement.addChildElement(iccidName);
        iccidElement.setValue(iccid);
        

        Name targetValueName = envelope.createName("targetValue", PREFIX, NAMESPACE_URI);
        SOAPElement targetValueElement = terminalRequestElement.addChildElement(targetValueName);
        targetValueElement.setValue(targetValue);
        
        Name changeTypeName = envelope.createName("changeType", PREFIX, NAMESPACE_URI);
        SOAPElement changeTypeElement = terminalRequestElement.addChildElement(changeTypeName);
        changeTypeElement.setValue(changeType);
        
        
        return message;
    }

    /**
     * edit terminal
     * 
     * @param iccid
     * @param targetValue
     * @param changeType
     * @throws IOException
     * @throws XWSSecurityException
     * @throws SOAPException
     */
    public Boolean editTerminal(String iccid, String changeType, String targetValue) throws IOException, XWSSecurityException, SOAPException {
    	SOAPMessage request = createEditTerminalRequest(iccid, changeType, targetValue);
        request = secureMessage(request, username, password);
        
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        
        //response.writeTo(System.out);
        
        if (!response.getSOAPBody().hasFault()) {
        	return true;
        } else {
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code :" + fault.getFaultCode());
            System.err.println("SOAP Fault String :" + fault.getFaultString());
        }
        
        return false;
    }
}

