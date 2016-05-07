package preferences.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import preferences.beans.UserPreferenceBean;
import preferences.db.UserPreferenceDTO;


class UserPreferenceCommonUtils {
	
	public static UserPreferenceBean transferProperties(UserPreferenceDTO dto, UserPreferenceBean bean){
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
		bean.setUserId(userId);
		bean.setKey(key);
		bean.setValue(value);
		bean.setDescription(description);
		bean.setAppName(appName);
		bean.setInstanceId(instanceId);
		bean.setId(id);
		bean.setCreationDate(creationDate);
		bean.setLastUpdate(lastUpdate);
		return bean;
	}
	private static String dateConverter(Date date) {
		String parsedDate = "";
		if (date != null) {
			parsedDate = (new SimpleDateFormat("MMM-dd-yyyy h:mm:ss a z")).format(date);
		}
		return parsedDate;
	}

}
