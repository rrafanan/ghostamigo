
package com.sample.jms.topic;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.listener.DefaultMessageListenerContainer;



public class JMSMessageListener implements MessageListener {

	final static Logger logger = Logger.getLogger(JMSMessageListener.class);

	
	public void init() throws UnknownHostException{
		
		logger.info("init called............"); 

	} 

	
	public void onMessage(Message msg) {	
		
		if (msg instanceof TextMessage) {
			TextMessage tmsg = (TextMessage)msg;
			try{			
				logger.info("TEXT IS:"+tmsg.getText());		
			}catch (Throwable e) {
				logger.error("error processing message...", e);
			}
		}else {
			logger.warn("unknown message type");			
		}
	}//end of onMessage
}
