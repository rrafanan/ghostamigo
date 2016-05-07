package preferences.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import preferences.beans.UserPreferenceBean;
import preferences.beans.UserPreferenceValueObject;
import preferences.dao.impl.UserPreferenceDAOImpl;
import preferences.db.UserPreferenceDB;
import preferences.db.UserPreferenceDTO;



/**
 * @author rolando_rafanan
 */
public class TestUserPreferenceDAOJunit {
	
	private static Logger logger;
	private static UserPreferenceDB userPrefDB = null;
	private static UserPreferenceDAOImpl dao = null;
	
	 /**
     * Sets up the test fixture. 
     */
	//@BeforeClass 
	@Test
	public void testSearch() {
		logger = Logger.getLogger(TestUserPreferenceDAOJunit.class);
		ApplicationContext context=new ClassPathXmlApplicationContext(new String[]{"root-context.xml"});
		DataSource dataSource = (DataSource) context.getBean("boPreferenceDataSource");
		userPrefDB = new UserPreferenceDB();
		userPrefDB.setDataSource(dataSource);
		userPrefDB.setPreferenceTable("");
		dao = new UserPreferenceDAOImpl();
		dao.setUserPreferenceDB(userPrefDB);
		
		
		UserPreferenceValueObject vo = dao.getAllPreferences(null, "", "");
		List<UserPreferenceBean> preferenceList = vo.getPreferenceList();
		for (UserPreferenceBean bean : preferenceList){
			System.out.println(bean.getUserId());
		}
		
		/*UserPreferenceDTO settings = new UserPreferenceDTO();
		settings.setAppName("");
		settings.setInstanceId("");
		List<UserPreferenceDTO> dtoList = userPrefDB.getAppSettingWithCondition(settings);
		for (UserPreferenceDTO dto : dtoList){
			transferProperties(dto);
		}*/
	}
		
	public static void transferProperties(UserPreferenceDTO dto){
		String userId = "";
		String key = "";
		String value = "";
		String description = "";
		String appName = "";
		String instanceId = "";
		String id = "";
		String lastUpdate = "";
		String creationDate = "";
		if (StringUtils.isNotEmpty(dto.getUserId())){
			userId = dto.getUserId();
		}
		if (StringUtils.isNotEmpty(dto.getKey())){
			key = dto.getKey();
		}
		if (StringUtils.isNotEmpty(dto.getValue())){
			value = dto.getValue();
		}
		if (StringUtils.isNotEmpty(dto.getDescription())){
			description = dto.getDescription();
		}
		if (StringUtils.isNotEmpty(dto.getAppName())){
			appName = dto.getAppName();
		}
		if (StringUtils.isNotEmpty(dto.getInstanceId())){
			instanceId = dto.getInstanceId();
		}
		if (StringUtils.isNotEmpty(dto.getId())){
			id = dto.getId();
		}
		if (dto.getLastUpdate() != null){
			lastUpdate = dateConverter(dto.getLastUpdate());
		}
		if (dto.getCreationDate() != null){
			creationDate = dateConverter(dto.getCreationDate());
		}
		System.out.println(userId + "/" + key + "/" + value + "/" + description + "/" + appName + "/" + instanceId + "/" + id );
	}
	private static String dateConverter(Date date) {
		String parsedDate = "";
		if (date != null) {
			parsedDate = (new SimpleDateFormat("MMM-dd-yyyy h:mm:ss a z")).format(date);
		}
		return parsedDate;
	}
	
}
