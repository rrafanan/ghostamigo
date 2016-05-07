package preferences.test;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import preferences.db.UserPreferenceDB;
import preferences.db.UserPreferenceDTO;


/**
 * @author rolando_rafanan
 */
@PrepareForTest( {TestUserPreferenceDBJunit.class} )
public class TestUserPreferenceDBJunit {
	
	private static Logger logger;
	private static UserPreferenceDB userPrefDB = null;
	
	 /**
     * Sets up the test. 
     */
	@BeforeClass 
	public void setup() {
		logger = Logger.getLogger(TestUserPreferenceDBJunit.class);
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"root-context.xml"});
		DataSource dataSource = (DataSource) context.getBean("boPreferenceDataSource");
		userPrefDB = new UserPreferenceDB();
		userPrefDB.setDataSource(dataSource);
	}
	
	public static void testGetAllUserPreferencesPagination(){
	//	UserPreferenceDTOValueHandler valueHandler = new UserPreferenceDTOValueHandler();
	//	userPrefDB.getAllUserPreferences(valueHandler);	
	}
	
	public static void testGetApplicationSettings(){
		
		UserPreferenceDTO settings = new UserPreferenceDTO();
		settings.setUserId("ROLANDO_RAFANAN");
		settings.setAppName("");
		settings.setInstanceId("j");
		settings.setKey("x");
		logger.debug(userPrefDB.getApplicationSettings(settings));
	}
	
	
	public static void testGetPreferenceById(){
		UserPreferenceDTO settings = new UserPreferenceDTO();
		settings.setId("380");
		logger.debug(userPrefDB.getPreferencesById(settings));
	}
	
	
	
	
	//@Test
	public static void testGetAllUserPreferences() {
		logger.info(userPrefDB.getAllUserPreferences());
	}
	
	public static void testDeleteAllUserPreferences() {
		userPrefDB.deleteAllPreferences();
	}

	public static void testCreateNewPreference(){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId("ROLANDO_RAFANAN");
		preferenceDTO.setAppName("");
		preferenceDTO.setCreationDate(Calendar.getInstance().getTime());
		preferenceDTO.setDescription("");
		preferenceDTO.setInstanceId("");
		preferenceDTO.setKey("Type");
		preferenceDTO.setValue("PDF");
	
		userPrefDB.createNewPreference(preferenceDTO);
		
		
		/*
		preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId("ROLANDO_RAFANAN");
		preferenceDTO.setAppName("");
		preferenceDTO.setCreationDate(Calendar.getInstance().getTime());
		preferenceDTO.setDescription("");
		preferenceDTO.setInstanceId("");
		preferenceDTO.setKey("");
		preferenceDTO.setValue("");
		*/
		//userPrefDB.createNewPreference(preferenceDTO);
	}
	
	@Test
	public void testCreateOrUpdateNewPreference(){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId("ROLANDO_RAFANAN");
		preferenceDTO.setAppName("");
		preferenceDTO.setCreationDate(Calendar.getInstance().getTime());
		preferenceDTO.setDescription("");
		preferenceDTO.setInstanceId("1");
		preferenceDTO.setKey("Type");
	//	preferenceDTO.setValue("X");
	//	userPrefDB.createNewOrUpdatePreference(preferenceDTO);
		userPrefDB.createNewPreference(preferenceDTO);
		
		
		/*
		preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId("ROLANDO_RAFANAN");
		preferenceDTO.setAppName("");
		preferenceDTO.setCreationDate(Calendar.getInstance().getTime());
		preferenceDTO.setDescription("");
		preferenceDTO.setInstanceId("");
		preferenceDTO.setKey("");
		preferenceDTO.setValue("");
		
		userPrefDB.createNewPreference(preferenceDTO);
		*/
	}
	
	
	public void testUpdatePreference(){
		logger.info("## Updating User Preference ##");
		Calendar calendar = Calendar.getInstance();
	    Date lastUpdate = calendar.getTime();
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId("ROLANDO_RAFANAN");
		preferenceDTO.setAppName("BO");
		preferenceDTO.setKey("Type");
		preferenceDTO.setValue("XLS");
		preferenceDTO.setLastUpdate(lastUpdate);
		userPrefDB.updatePreference(preferenceDTO);
	}
	
	public void testDeletePreference(){
		logger.info("## Delete Preferences ##");
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId("ROLANDO_RAFANAN");
		preferenceDTO.setAppName("BO");
		preferenceDTO.setKey("Type");
		userPrefDB.deletePreference(preferenceDTO);
	}
	
	public void testDeletePreferenceById(){
		logger.info("## Delete Preferences By Id ##");
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setId("312");
		userPrefDB.deletePreferenceById(preferenceDTO);
	}
	
	public void testGetUserPreference(){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId("ROLANDO_RAFANAN");
		preferenceDTO.setAppName("");
		preferenceDTO.setKey("");
		logger.debug(userPrefDB.getApplicationSettings(preferenceDTO));
	}
	
	
	 /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
	@AfterClass
	public static void tearDown() {
		//logger.info(userPrefDB.getAllUserPreferences());
	}
	
}
