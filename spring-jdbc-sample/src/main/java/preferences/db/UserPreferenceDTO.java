package preferences.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

/**
 * Defines User Preference DTO
 * 
 * @author rolando_rafanan
 */
public class UserPreferenceDTO {
	
	private static final Logger logger = LoggerFactory.getLogger(UserPreferenceDTO.class);
	
	
	private String id = "";
	private String appName = "";
	private String instanceId = "";
	private String description = "";
	private String userId = "";
	private String key = "";
	private String value = "";
	private Date creationDate;
	private Date lastUpdate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public MapSqlParameterSource getMappedParameters() {
		MapSqlParameterSource userPreference = new MapSqlParameterSource();
		userPreference.addValue("ID", getId(), java.sql.Types.INTEGER);
		userPreference.addValue("USERID", getUserId(), java.sql.Types.VARCHAR);
		userPreference.addValue("INSTANCEID", getInstanceId(), java.sql.Types.VARCHAR);
		userPreference.addValue("APPNAME", getAppName(), java.sql.Types.VARCHAR);
		userPreference.addValue("DESCRIPTION", getDescription(), java.sql.Types.VARCHAR);
		userPreference.addValue("KEY", getKey(),java.sql.Types.VARCHAR);
		userPreference.addValue("VALUE", getValue(),java.sql.Types.CLOB);
		userPreference.addValue("CREATION_DATE", getCreationDate(),java.sql.Types.DATE);
		userPreference.addValue("LAST_UPDATE", getLastUpdate(),java.sql.Types.DATE);
		return userPreference;
	}
	
	

	
	/**
	 * Intended only for debugging.
	 * 
	 * <P>
	 * Here, a generic implementation uses reflection to print names and values
	 * of all fields <em>declared in this class</em>. Note that superclass
	 * fields are left out of this implementation.
	 * 
	 * <p>
	 * The format of the presentation could be standardized by using a
	 * MessageFormat object with a standard pattern.
	 */
	@Override
	public String toString() {
	    String lineSeparator = System.getProperty("line.separator");
	    StringBuffer buffer = new StringBuffer(lineSeparator);
	    Method[] methods = this.getClass().getMethods();
	    if(methods != null && methods.length >0){
	        Method method = null;
	        for(int i =0; i< methods.length; i++){
	            method = methods[i];
	            if(method.getName().startsWith("get") && !method.getName().startsWith("getClass")){
	            	buffer.append(method.getName().replaceAll("get",""));
	            	buffer.append(" = ");
	            	Object[] params = (Object[]) new Object();
	            	try {
						buffer.append(method.invoke(this, params));
		            	buffer.append("  ");
		            	buffer.append(" | ");
					} catch (IllegalArgumentException e) {
						logger.error(e.getMessage());
					} catch (IllegalAccessException e) {
						logger.error(e.getMessage());
					} catch (InvocationTargetException e) {
						logger.error(e.getMessage());
					}
	            }
	        }
	    }
	    return buffer.toString();
	}

}
