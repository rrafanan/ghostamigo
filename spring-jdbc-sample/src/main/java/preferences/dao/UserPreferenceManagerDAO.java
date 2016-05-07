package preferences.dao;

import preferences.beans.UserPreferenceValueObject;
import preferences.db.UserPreferenceDB;

public interface UserPreferenceManagerDAO {
	
public abstract UserPreferenceDB getUserPreferenceDB();

	/**
	 * Deletes all User Application Preferences
	 * 
	 */
	public abstract void deleteAllUserApplicationPreferences();
	
	/**
	 * Get UserPreference value object based on ID
	 * @param id
	 * @return
	 */
	public abstract UserPreferenceValueObject getPreferenceById(int id);
	
	/**
	 * Delete Preference based on ID
	 * @param id
	 */
	public abstract void deletePreferenceById(int id);
	
	/**
	 * Get all user preferences based on Filter Criteria
	 * Relies on start page and page size constant
	 * 
	 * @param startRecord
	 * @param recordSetCount
	 * @return
	 */
	public abstract UserPreferenceValueObject getAllPreferencesPagination(UserPreferenceValueObject vo); 
	
	/**
	 * Create a new user preference
	 * @param userId
	 * @param applicationName
	 * @param instanceId
	 * @param key
	 * @param value
	 * @param description
	 */
	public abstract void createNewOrUpdatePreference(String userId, String applicationName, String instanceId, String key, String value, String description);
	
	
	/**
	 * Updates User Preference Based on ID
	 * @param userId
	 * @param applicationName
	 * @param instanceId
	 * @param key
	 * @param value
	 * @param description
	 */
	public abstract void updatePreference(String id, String userId, String applicationName, String instanceId, String key, String value, String description);
		
	
}
