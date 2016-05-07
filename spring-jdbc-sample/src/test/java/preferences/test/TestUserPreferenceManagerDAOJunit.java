package preferences.test;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import preferences.beans.UserPreferenceValueObject;
import preferences.dao.impl.UserPreferenceManagerDAOImpl;
import preferences.db.UserPreferenceDB;



/**
 * @author rolando_rafanan
 */
@PrepareForTest( {TestUserPreferenceManagerDAOJunit.class} )
public class TestUserPreferenceManagerDAOJunit {
	
	private static Logger logger;
	private static UserPreferenceDB userPreferenceDB = null;
	private static UserPreferenceManagerDAOImpl dao = null;
	
	 /**
     * Sets up the test fixture. 
     */
	@BeforeClass 
	public void setup() {
		logger = Logger.getLogger(TestUserPreferenceManagerDAOJunit.class);
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("");
		dataSource.setUsername("rolando");
		dataSource.setPassword("rolando");   
		userPreferenceDB = new UserPreferenceDB();
		userPreferenceDB.setDataSource(dataSource);
		dao = new UserPreferenceManagerDAOImpl();
		dao.setUserPreferenceDB(userPreferenceDB);
	}
	
	/**
	 * Test creation of settings in the Database
	 */
	@Test
	public void testUpdatePreference(){
		String id = "460";
		String userId = "ROLANDO_RAFANAN";
		String applicationName = "BO";
		String instanceId = "bo.botest1.instance1";
		String key="key1";
		String value = "e";
		String description = "";
		dao.updatePreference(id, userId, applicationName, instanceId, key, value, description);
	}
	
	
	/**
	 * Test Retrieval of all user preferences in the Database
	 */
	//@Test
	public void testGetAllSettings(){
		//UserPreferenceValueObject vo =  dao.getAllPreferences();
		//logger.debug("Preferences List " + vo.getPreferenceList());
	}

	
	/**
	 * Test Retrieval of all user preferences in the Database
	 */
	//@Test
	public void testGetApplicationSetting(){
		String userId = "ROLANDO_RAFANAN";
		String instanceId = "com.bo";
		String applicationName = "BO";
		UserPreferenceValueObject vo =  dao.getUserApplicationPreferences(userId, instanceId, applicationName);
		logger.debug("Preferences List " + vo.getPreferenceList());
	}

	
	
	//@Test
	public void getAllPreferencesPagination() {
		int pageNumber = 1;
		int page_size_constant = 25;
		//UserPreferenceValueObject vo = dao.getAllPreferencesPagination(pageNumber, page_size_constant);
		//logger.debug("Preference List: " + vo.getPreferenceList());
		//logger.debug("Total Records: " + vo.getTotalRecords());
		//logger.debug("Total Pages: " + vo.getTotalPages());
		//logger.debug("Current Page: " + vo.getCurrentPage());
		//logger.debug("Page Size: " + vo.getPageSize());
		//logger.debug("Record Start: " + vo.getRecordSetStart());
		//logger.debug("Record End: " + vo.getRecordSetEnd());
	}
	

	
	 /**
     * Tears down the test fixture. 
     * (Called after every test case method.)
     */
	@AfterClass
	public static void tearDown() {
		
	}
	
}
