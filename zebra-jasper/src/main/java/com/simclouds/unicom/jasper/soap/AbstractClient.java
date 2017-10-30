package com.simclouds.unicom.jasper.soap;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.apache.log4j.Logger;

import com.sun.xml.wss.ProcessingContext;
import com.sun.xml.wss.XWSSProcessor;
import com.sun.xml.wss.XWSSProcessorFactory;
import com.sun.xml.wss.XWSSecurityException;
import com.sun.xml.wss.impl.callback.PasswordCallback;
import com.sun.xml.wss.impl.callback.UsernameCallback;

/**
 * API abstract client
 * 
 * @author henrylv
 *
 */
public abstract class AbstractClient {
	private static Logger log = Logger.getLogger(AbstractClient.class);
	
    static final String NAMESPACE_URI = "http://api.jasperwireless.com/ws/schema";
    static final String PREFIX = "jws";
    static final String TERMINAL_API = "/ws/service/terminal";
    static final String BILLING_API = "/ws/service/billing";
    
    String BASE_URL = "https://api.10646.cn";
    
    URL url;
    String licenseKey;
    String username;
    String password;
    
    SOAPConnectionFactory connectionFactory;
    MessageFactory messageFactory;
    XWSSProcessorFactory processorFactory;
    
    public AbstractClient(String api, String licenseKey, String username, String password) throws XWSSecurityException, SOAPException, MalformedURLException {
    	connectionFactory = SOAPConnectionFactory.newInstance();
    	messageFactory = MessageFactory.newInstance();
    	processorFactory = XWSSProcessorFactory.newInstance();
    	
        this.url = new URL(BASE_URL + api);
        this.licenseKey = licenseKey;
        this.username = username;
        this.password = password;
    }
    
    protected SOAPMessage createBaseRequest(String apiURI, String apiName) throws SOAPException {
    	SOAPMessage message = messageFactory.createMessage();
        
    	// SOAP action
        message.getMimeHeaders().addHeader("SOAPAction",
                "http://api.jasperwireless.com" + apiURI + "/" + apiName);
        
        // envelope
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        
        // request
        Name terminalRequestName = envelope.createName(apiName + "Request", PREFIX, NAMESPACE_URI);
        SOAPBodyElement terminalRequestElement = message.getSOAPBody()
                .addBodyElement(terminalRequestName);
        
        // messageId
        Name msgId = envelope.createName("messageId", PREFIX, NAMESPACE_URI);
        SOAPElement msgElement = terminalRequestElement.addChildElement(msgId);
        msgElement.setValue("TCE-100-ABC-34084");
        
        // version
        Name version = envelope.createName("version", PREFIX, NAMESPACE_URI);
        SOAPElement versionElement = terminalRequestElement.addChildElement(version);
        versionElement.setValue("1.0");
        
        // licenseKey
        Name license = envelope.createName("licenseKey", PREFIX, NAMESPACE_URI);
        SOAPElement licenseElement = terminalRequestElement.addChildElement(license);
        licenseElement.setValue(licenseKey);

        return message;
    }
    
    /**
     * This method is used to add the security. This uses xwss:UsernameToken configuration and expects
     * Username and Password to be passes. SecurityPolicy.xml file should be in classpath.
     *
     * @param message
     * @param username
     * @param password
     * @return
     * @throws IOException
     * @throws XWSSecurityException
     */
    protected SOAPMessage secureMessage(SOAPMessage message, final String username, final String password)
            throws IOException, XWSSecurityException {
        CallbackHandler callbackHandler = new CallbackHandler() {
            public void handle(Callback[] callbacks) throws UnsupportedCallbackException {
                for (int i = 0; i < callbacks.length; i++) {
                    if (callbacks[i] instanceof UsernameCallback) {
                        UsernameCallback callback = (UsernameCallback) callbacks[i];
                        callback.setUsername(username);
                    } else if (callbacks[i] instanceof PasswordCallback) {
                        PasswordCallback callback = (PasswordCallback) callbacks[i];
                        callback.setPassword(password);
                    } else {
                        throw new UnsupportedCallbackException(callbacks[i]);
                    }
                }
            }
        };
        InputStream policyStream = null;
        XWSSProcessor processor = null;
        try {
            policyStream = getClass().getResourceAsStream("securityPolicy.xml");
            processor = processorFactory.createProcessorForSecurityConfiguration(policyStream, callbackHandler);
        }
        finally {
            if (policyStream != null) {
                policyStream.close();
            }
        }
        ProcessingContext context = processor.createProcessingContext(message);
        return processor.secureOutboundMessage(context);
    }


    // getter and setter
	public String getUsername() {
		return username;
	}
    
    
}
