package preferences.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.util.CollectionUtils;

import preferences.beans.UserPreferenceBean;
import preferences.beans.UserPreferenceValueObject;
import preferences.dao.UserPreferenceManagerDAO;
import preferences.db.UserPreferenceDB;
import preferences.db.UserPreferenceDTO;


public class UserPreferenceManagerDAOImpl implements UserPreferenceManagerDAO {
	
	private UserPreferenceDB userPreferenceDB;
	
	
	/**
	 * Page Number is the Start Number of the Page
	 * Page Size Constant is always 12
	 * 
	 */
	public UserPreferenceValueObject getAllPreferencesPagination(UserPreferenceValueObject vo){
		// Start Page
		int pageNumber = vo.getCurrentPage();
		// Page Size Constant (e.g. 12)
		int page_size_constant = vo.getPageSize();
		// Initialize application settings
		String appName = "";
		String userId = "";
		String instanceId = "";
	
		if (!CollectionUtils.isEmpty(vo.getPreferenceList())){
			appName = vo.getPreferenceList().get(0).getAppName();
			userId = vo.getPreferenceList().get(0).getUserId();
			instanceId = vo.getPreferenceList().get(0).getInstanceId();
		}
		
		int startRecordDisplay = 0;
		int endRecordDisplay = 0;
		int startRecord = 0;
		int endRecord = 0;
		int totalRecords = 0;
		int totalPages = 0;
		int pageSize = 0;
		
		UserPreferenceDTO dtoInput = new UserPreferenceDTO();
		dtoInput.setAppName(appName);
		dtoInput.setUserId(userId);
		dtoInput.setInstanceId(instanceId);
		
		// Get a list of total records
		totalRecords = userPreferenceDB.getTotalRecords(dtoInput);
		
		// Set the start record
		startRecord = (pageNumber - 1) * page_size_constant;
		startRecordDisplay = startRecord + 1;
		
		// Set the end record
		endRecord = startRecord + page_size_constant;
		endRecordDisplay = endRecord;
		
		// Calculate total pages
		totalPages = totalRecords / page_size_constant;
		if (totalRecords > page_size_constant * totalPages) {
	        totalPages++;
	    }
		
		// Set the page size
		if (pageNumber == totalPages){
        	endRecordDisplay = totalRecords;
        	// one added to count the initial record
        	pageSize = (endRecordDisplay - startRecordDisplay) + 1;
        }else{
    		pageSize = page_size_constant;
        }

		// Get a list of user preferences by the start record number and end record number
		ArrayList <UserPreferenceDTO> dtoList = (ArrayList<UserPreferenceDTO>)userPreferenceDB.getAllUserPreferences(dtoInput, startRecord, endRecord);
		ArrayList <UserPreferenceBean> preferenceList = new ArrayList <UserPreferenceBean>();
		for (UserPreferenceDTO dto : dtoList){
	        UserPreferenceBean bean = new UserPreferenceBean();
	        bean = UserPreferenceCommonUtils.transferProperties(dto, bean);
	        preferenceList.add(bean);
		}
		UserPreferenceValueObject valueObject = new UserPreferenceValueObject();
		if (preferenceList.size() > 0){
			valueObject.setCurrentPage(pageNumber);
			valueObject.setRecordSetStart(startRecordDisplay);
			valueObject.setRecordSetEnd(endRecordDisplay);
			valueObject.setPreferenceList(preferenceList);
			valueObject.setTotalRecords(totalRecords);
			valueObject.setTotalPages(totalPages);
			valueObject.setPageSize(pageSize);
		}
		return valueObject;
	}
	
	public UserPreferenceValueObject getUserApplicationPreferences(String userId, String instanceId, String applicationName){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		ArrayList <UserPreferenceBean> preferenceList = new ArrayList <UserPreferenceBean>();
		preferenceDTO.setUserId(userId);
		preferenceDTO.setAppName(applicationName);
		preferenceDTO.setInstanceId(instanceId);
	
		List<UserPreferenceDTO> dtoList  = userPreferenceDB.getApplicationSettings(preferenceDTO);
		UserPreferenceBean bean = new UserPreferenceBean();
		for (UserPreferenceDTO dto : dtoList){
			bean = UserPreferenceCommonUtils.transferProperties(dto, bean);
			preferenceList.add(bean);
		}
		UserPreferenceValueObject valueObject = new UserPreferenceValueObject();
		valueObject.setPreferenceList(preferenceList);
		return valueObject;
	}
	
	public void updateAppPreference(String applicationName, String key, String value){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setAppName(applicationName);
		preferenceDTO.setKey(key);
		preferenceDTO.setValue(value);
		preferenceDTO.setLastUpdate(Calendar.getInstance().getTime());
		userPreferenceDB.updateAppPreference(preferenceDTO);
	}
	
	public void updatePreference(String id, String userId, String applicationName, String instanceId, String key, String value, String description){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setId(id);
		preferenceDTO.setUserId(userId);
		preferenceDTO.setAppName(applicationName);
		preferenceDTO.setDescription(description);
		preferenceDTO.setInstanceId(instanceId);
		preferenceDTO.setKey(key);
		preferenceDTO.setValue(value);
		preferenceDTO.setLastUpdate(Calendar.getInstance().getTime());
		userPreferenceDB.updatePreference(preferenceDTO);
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
	
	public UserPreferenceValueObject getPreferenceById(int id){
		UserPreferenceValueObject valueObject = new UserPreferenceValueObject();
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		UserPreferenceBean bean = new UserPreferenceBean();
		ArrayList <UserPreferenceBean> preferenceList = new ArrayList <UserPreferenceBean>();
		preferenceDTO.setId(String.valueOf(id));
		UserPreferenceDTO dto = userPreferenceDB.getPreferencesById(preferenceDTO);
		bean = UserPreferenceCommonUtils.transferProperties(dto, bean);
		preferenceList.add(bean);
		valueObject.setPreferenceList(preferenceList);
		return valueObject;
	}
	
	public void deletePreferenceById(int id){
		UserPreferenceDTO preferenceDTO = new UserPreferenceDTO();
		preferenceDTO.setId(String.valueOf(id));
		userPreferenceDB.deletePreferenceById(preferenceDTO);
	}
	
	public void deleteAllUserApplicationPreferences(){
		userPreferenceDB.deleteAllPreferences();
	}
	
	public UserPreferenceDB getUserPreferenceDB() {
		return userPreferenceDB;
	}

	public void setUserPreferenceDB(UserPreferenceDB userPreferenceDB) {
		this.userPreferenceDB = userPreferenceDB;
	}
	

}
