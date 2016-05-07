package com.sample.jms.topic;

import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class TopicMessageListenerContainer extends DefaultMessageListenerContainer{

	final static Logger logger = Logger.getLogger(TopicMessageListenerContainer.class);
	
	@Override
	public void setDurableSubscriptionName(String durableSubscriptionName) {
		
		   //set name by managed server name
		   String subName = durableSubscriptionName + System.getProperty("weblogic.Name");
		   //logger.debug("Starting subscriber with name: " +  subName);
		   super.setDurableSubscriptionName(subName);
	}
    
}
