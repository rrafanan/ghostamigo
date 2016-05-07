package preferences.dao;

import preferences.beans.UserPreferenceValueObject;
import preferences.db.UserPreferenceDB;

public interface UserPreferenceDAO {
	
	public abstract UserPreferenceDB getUserPreferenceDB();
	
	/**
	 * Gets the application settings based on userId, instanceId, and applicationName (Like condition)
	 * @param userId
	 * @param instanceId
	 * @param applicationName
	 * 
	 * @return UserPreferenceValueObject
	 */
	public abstract UserPreferenceValueObject getUserApplicationPreferences(String userId, String instanceId, String applicationName);
	
	/**
	 * Gets the application settings based on userId, instanceId, and applicationName (Equal condition)
	 * @param userId
	 * @param instanceId
	 * @param applicationName
	 * 
	 * @return UserPreferenceValueObject
	 */
	public abstract UserPreferenceValueObject getAllPreferences(String userId, String instanceId, String applicationName);
	
	/**
	 * Creates the application preference in the Preference Table
	 * @param userId
	 * @param applicationName
	 * @param instanceId
	 * @param key
	 * @param value
	 * @param description
	 * @return UserPreferenceValueObject
	 */
	public abstract void createNewOrUpdatePreference(String userId, String applicationName, String instanceId, String key, String value, String description);
	
	public abstract void updateAppPreference(String instanceId, String applicationName,String key, String value);
	/**
	 * Removes application preference
	 * @param userId
	 * @param applicationName
	 * @param instanceId
	 * @param key
	 */
	public abstract void deleteUserPreference(String userId, String applicationName, String instanceId, String key);
}
