package preferences.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import preferences.beans.UserPreferenceBean;
import preferences.beans.UserPreferenceValueObject;
import preferences.dao.UserPreferenceDAO;
import preferences.db.UserPreferenceDB;
import preferences.db.UserPreferenceDTO;


public class UserPreferenceDAOImpl implements UserPreferenceDAO {
	
	private UserPreferenceDB userPreferenceDB;
	
	public void updateAppPreference(String instanceId, String applicationName, String key, String value){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setAppName(applicationName);
		preferenceDTO.setKey(key);
		preferenceDTO.setValue(value);
		preferenceDTO.setInstanceId(instanceId);
		preferenceDTO.setLastUpdate(Calendar.getInstance().getTime());
		userPreferenceDB.updateAppPreference(preferenceDTO);
	}
	
	public UserPreferenceValueObject getAllPreferences(String userId, String instanceId, String applicationName){
		return getAppPreferencesWithCondition(userId, instanceId, applicationName, true);
	}
	
	public UserPreferenceValueObject getUserApplicationPreferences(String userId, String instanceId, String applicationName){
		return getAppPreferencesWithCondition(userId, instanceId, applicationName, false);
	}
	
	public UserPreferenceValueObject getAppPreferencesWithCondition(String userId, String instanceId, String applicationName, boolean condition){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId(userId);
		preferenceDTO.setAppName(applicationName);
		preferenceDTO.setInstanceId(instanceId);
		List<UserPreferenceDTO> dtoList = new ArrayList<UserPreferenceDTO>();
		if (condition){
			dtoList  = userPreferenceDB.getAppSettingWithCondition(preferenceDTO);
		}else{
			dtoList  = userPreferenceDB.getApplicationSettings(preferenceDTO);
		}
		ArrayList <UserPreferenceBean> preferenceList = new ArrayList <UserPreferenceBean>();
		for (UserPreferenceDTO dto : dtoList){
			UserPreferenceBean bean = UserPreferenceCommonUtils.transferProperties(dto, new UserPreferenceBean());
			preferenceList.add(bean);
		}
		UserPreferenceValueObject valueObject = new UserPreferenceValueObject();
		valueObject.setPreferenceList(preferenceList);
		return valueObject;
	}
	
	public void createNewOrUpdatePreference(String userId, String applicationName, String instanceId, String key, String value, String description){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId(userId);
		preferenceDTO.setAppName(applicationName);
		preferenceDTO.setInstanceId(instanceId);
		preferenceDTO.setKey(key);
		preferenceDTO.setValue(value);
		preferenceDTO.setDescription(description);
		List<UserPreferenceDTO> dtoList  = userPreferenceDB.getApplicationSettings(preferenceDTO);
		if (dtoList.size() > 0){
			preferenceDTO.setId(dtoList.get(0).getId());
			userPreferenceDB.updatePreference(preferenceDTO);
		}else{
			userPreferenceDB.createNewPreference(preferenceDTO);
		}
	}
	
	public void deleteUserPreference(String userId, String applicationName, String instanceId, String key){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setUserId(userId);
		preferenceDTO.setAppName(applicationName);
		preferenceDTO.setInstanceId(instanceId);
		preferenceDTO.setKey(key);
		userPreferenceDB.deletePreference(preferenceDTO);
	}
	
	public UserPreferenceDB getUserPreferenceDB() {
		return userPreferenceDB;
	}

	public void setUserPreferenceDB(UserPreferenceDB userPreferenceDB) {
		this.userPreferenceDB = userPreferenceDB;
	}
}
