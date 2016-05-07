package com.test.xml.parser;

import generated.LvbSystem;
import generated.LvbSystem.Line;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;


public class ParseXML {

	public static void main(String[] args) throws IOException, JAXBException {
		// TODO Auto-generated method stub
		ParseXML x = new ParseXML();
		
		File xmlFile = new File("src/test/resources/XML_Sample_033116.xml");
		x.process(xmlFile);
	}

	public void process(File file) throws IOException, JAXBException {
		
		JAXBContext jaxbContext = JAXBContext.newInstance(LvbSystem.class);
		Unmarshaller u = jaxbContext.createUnmarshaller();
		JAXBElement<LvbSystem> obj = u.unmarshal(new StreamSource(new StringReader(FileUtils.readFileToString(file))), LvbSystem.class);		
		LvbSystem system = obj.getValue();
		List <Line> lineList = system.getLine();
		for (Line line : lineList){
			System.out.println(line.getEndStation().getName());
		}
		
		
		//JAXBContext jc = JAXBContext.newInstance ("com.standardandpoors.integration.serviceschema.v3");
	    //Unmarshaller u = jc.createUnmarshaller ();
	   
	    /*
	    JAXBElement<Sync> obj = u.unmarshal(new StreamSource(new StringReader(FileUtils.readFileToString(file))), Sync.class);		    
	    Sync sync = obj.getValue();
	    
	    System.out.println(sync.getDataArea().getMessageID());
	    
	    DataObjectSet set = sync.getDataArea().getDataObjectSet();	   
	    //System.out.println(set.getAccessControlFramework());
	    
	    Gson gson = new Gson();
	    AccessControlFramework frameWork = gson.fromJson(set.getAccessControlFramework(), AccessControlFramework.class);
	    
	    List <FunctionalRole> role = frameWork.getUserAccess().getFunctionalRoles();
	    for (FunctionalRole  x :role){
	    	System.out.println(x.getFunctionalRole());
	    }
	    
	    List <Application> applications =  frameWork.getUserAccess().getApplications();
	    for(Application a : applications){
	    	System.out.println(a.getApplication());
	    	List <String>  newsAndResearchList = a.getNewsAndResearch();
	    	for(String b:newsAndResearchList){
	    		System.out.println("    "+b);
	    	}
	    }
	    
	    UserProfile userProfile = frameWork.getUserProfile();
	    System.out.println(userProfile.getEin());
	    */
	    
	}

}
