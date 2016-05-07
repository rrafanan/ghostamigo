package preferences.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;


public class UserPreferenceDB extends NamedParameterJdbcDaoSupport{
	private String preferenceTable;
	private String autoIncrementSequence;
	private String GET_PREFERENCE_BY_ID;
	private String GET_APPLICATION_SETTINGS;
	private String UPDATE_PREFERENCE;
	private String GET_ALL_PREFERENCES;
    private String CREATE_NEW_PREFERENCE;
    private String DELETE_PREFERENCE;
    private String DELETE_PREFERENCE_BY_ID;
    private String DELETE_ALL_PREFERENCES;
    private String ROW_COUNT;
    private String UPDATE_PREFERENCE_BY_APP;
    private String GET_ALL;
    
    @Override
    protected void initDao() {
    	GET_ALL = "SELECT * FROM " + preferenceTable;
    	GET_PREFERENCE_BY_ID = "SELECT * FROM " + preferenceTable + " WHERE ID=:ID";
    	GET_ALL_PREFERENCES = "SELECT * FROM " + preferenceTable + " ORDER BY LAST_UPDATE DESC";
    	GET_APPLICATION_SETTINGS = "SELECT * FROM " + preferenceTable + " WHERE USERID=lower(:USERID) AND APPNAME=lower(:APPNAME) AND INSTANCEID=lower(:INSTANCEID)";
    	UPDATE_PREFERENCE = "UPDATE " + preferenceTable + " set USERID=lower(:USERID), KEY=:KEY, VALUE=:VALUE, APPNAME=lower(:APPNAME), INSTANCEID=lower(:INSTANCEID), DESCRIPTION=:DESCRIPTION,LAST_UPDATE=SYSDATE WHERE ID=:ID";
    	CREATE_NEW_PREFERENCE = "INSERT INTO " + preferenceTable + " (ID,USERID,KEY,VALUE,APPNAME,INSTANCEID,DESCRIPTION,CREATION_DATE,LAST_UPDATE) VALUES (:ID,lower(:USERID),:KEY,:VALUE,lower(:APPNAME),lower(:INSTANCEID),:DESCRIPTION,SYSDATE,SYSDATE)";
    	DELETE_PREFERENCE = "DELETE FROM " + preferenceTable + " WHERE USERID=lower(:USERID) and KEY=:KEY and APPNAME=lower(:APPNAME) and INSTANCEID=lower(:INSTANCEID)"; 
    	DELETE_PREFERENCE_BY_ID = "DELETE FROM " + preferenceTable + " WHERE ID=:ID"; 
    	DELETE_ALL_PREFERENCES = "DELETE FROM " + preferenceTable;  
    	ROW_COUNT = "SELECT COUNT(*) FROM " + preferenceTable;
    	UPDATE_PREFERENCE_BY_APP = "UPDATE " + preferenceTable + " set KEY=:KEY, VALUE=:VALUE,LAST_UPDATE=SYSDATE WHERE APPNAME=lower(:APPNAME) and INSTANCEID=lower(:INSTANCEID)";
	  
    }
  
    /**
	 * SELECT COUNT(*) FROM RG_BOUSER_PREFERENCE
	 * @return count of all records
	 */
	public int getTotalRecords(){
		int rowCount = 0;
        rowCount = getJdbcTemplate().queryForInt(ROW_COUNT);
		return rowCount;
	}

	/**
	 * Get a count of Total Records based on Filter Criteria
	 * 
	 * @param preferenceDTO
	 * @return
	 */
	public int getTotalRecords(UserPreferenceDTO preferenceDTO){
		int rowCount = 0;
		String filterCriterion = "";
		MapSqlParameterSource params = new MapSqlParameterSource();
		if (StringUtils.isNotEmpty(preferenceDTO.getAppName())){
			params.addValue("APPNAME", "%" + preferenceDTO.getAppName() + "%");
			filterCriterion+=" WHERE APPNAME LIKE :APPNAME "; 

			if (StringUtils.isNotEmpty(preferenceDTO.getInstanceId())){
				params.addValue("INSTANCEID", "%" + preferenceDTO.getInstanceId() + "%");
				filterCriterion+="AND INSTANCEID LIKE :INSTANCEID "; 
			}
			if (StringUtils.isNotEmpty(preferenceDTO.getUserId())){
				params.addValue("USERID", "%" + preferenceDTO.getUserId() + "%");
				filterCriterion+="AND USERID LIKE :USERID "; 
			}
		}
		String ROW_SQL = "SELECT COUNT (*) FROM " + preferenceTable + filterCriterion;
		rowCount = getNamedParameterJdbcTemplate().queryForInt(ROW_SQL, params);
		return rowCount;
	}
	
	/**
	 * Returns a list of all preferences by a filterable criteria
	 * 
	 * @param preferenceDTO
	 * @param startRecord
	 * @param endRecord
	 * @return
	 */
	public List<UserPreferenceDTO> getAllUserPreferences(UserPreferenceDTO preferenceDTO, int startRecord, int endRecord){
		List <UserPreferenceDTO> userPreferencesList = null;	
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("END_RECORD", endRecord);
		params.addValue("START_RECORD", startRecord);
		
		String filterCriterion = "  ";
		if (StringUtils.isNotEmpty(preferenceDTO.getAppName())){
			params.addValue("APPNAME", "%" + preferenceDTO.getAppName() + "%");
			filterCriterion+=" WHERE APPNAME LIKE :APPNAME "; 
			
			if (StringUtils.isNotEmpty(preferenceDTO.getInstanceId())){
				params.addValue("INSTANCEID", "%" + preferenceDTO.getInstanceId() + "%");
				filterCriterion+="AND INSTANCEID LIKE :INSTANCEID "; 
			}
			if (StringUtils.isNotEmpty(preferenceDTO.getUserId())){
				params.addValue("USERID", "%" + preferenceDTO.getUserId() + "%");
				filterCriterion+="AND USERID LIKE :USERID "; 
			}
		}
		String FILTERED_SQL = "SELECT RNUM, USERID, KEY, VALUE, APPNAME, INSTANCEID, DESCRIPTION, CREATION_DATE, LAST_UPDATE, ID " + 
					   			"FROM " + 
					   				"(SELECT ROWNUM RNUM, USERID, KEY, VALUE, APPNAME, INSTANCEID, DESCRIPTION, CREATION_DATE, LAST_UPDATE, ID " + 
					   					"FROM " +
					   						"(SELECT USERID, KEY, VALUE, APPNAME, INSTANCEID, DESCRIPTION, CREATION_DATE, LAST_UPDATE, ID " + 
					   							"FROM " + preferenceTable + filterCriterion + 
					   							" ORDER BY LAST_UPDATE DESC" +
					   							") " + 
					   					"WHERE ROWNUM <= :END_RECORD " +
					   				") " + 
					   			"WHERE RNUM > :START_RECORD ";
			  
		userPreferencesList = getNamedParameterJdbcTemplate().query(FILTERED_SQL, params, new RowMapper<UserPreferenceDTO>() {
	            public UserPreferenceDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
	                UserPreferenceResultSetExtractor extractor = new UserPreferenceResultSetExtractor();
	                return extractor.extractData(rs);
	            }
	    });
		return userPreferencesList;
	}
	
	/**
	 * Returns a list of all preferences by a filterable criteria
	 * 
	 * @param preferenceDTO
	 * @param startRecord
	 * @param endRecord
	 * @return
	 */
	public List<UserPreferenceDTO> getAppSettingWithCondition(UserPreferenceDTO preferenceDTO){
		List <UserPreferenceDTO> userPreferencesList = null;	
		
		String filterCriterion = "";
		if (StringUtils.isNotEmpty(preferenceDTO.getAppName())){
			filterCriterion+=" WHERE APPNAME=lower(:APPNAME) "; 
			if (StringUtils.isNotEmpty(preferenceDTO.getInstanceId())){
				filterCriterion+="AND INSTANCEID=lower(:INSTANCEID) "; 
			}
			if (StringUtils.isNotEmpty(preferenceDTO.getUserId())){
				filterCriterion+="AND USERID=lower(:USERID) "; 
			}
		}
		String FILTERED_SQL = GET_ALL + filterCriterion;
			  
		userPreferencesList = getNamedParameterJdbcTemplate().query(FILTERED_SQL, preferenceDTO.getMappedParameters(), new RowMapper<UserPreferenceDTO>() {
			public UserPreferenceDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
				UserPreferenceResultSetExtractor extractor = new UserPreferenceResultSetExtractor();
				return extractor.extractData(rs);
			}
		});
		return userPreferencesList;
	}

	/**
	 * Get a user preference based on ID
	 * SELECT * FROM RG_BOUSER_PREFERENCE WHERE ID=:ID
	 * @param preferenceDTO
	 * @return a specific User Preference
	 */
	public UserPreferenceDTO getPreferencesById(UserPreferenceDTO preferenceDTO){
		UserPreferenceDTO userPreferenceResult = getNamedParameterJdbcTemplate().queryForObject(GET_PREFERENCE_BY_ID, preferenceDTO.getMappedParameters(), new RowMapper<UserPreferenceDTO>() {
            public UserPreferenceDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
                UserPreferenceResultSetExtractor extractor = new UserPreferenceResultSetExtractor();
                return extractor.extractData(rs);
            }
        });
        return userPreferenceResult;
    }
	
	/**
	 * Get all preferences in the Preferences Table without pagination
	 * @return List of User Preferences
	 */
	public List<UserPreferenceDTO> getAllUserPreferences(){
    	List <UserPreferenceDTO> userPreferencesList = null;
        MapSqlParameterSource params = new MapSqlParameterSource();
        userPreferencesList = getNamedParameterJdbcTemplate().query(GET_ALL_PREFERENCES, params, new RowMapper<UserPreferenceDTO>() {
            public UserPreferenceDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
                UserPreferenceResultSetExtractor extractor = new UserPreferenceResultSetExtractor();
                return extractor.extractData(rs);
            }
        });
        return userPreferencesList;
    }
	
	/**
	 * Get application settings based on userId, app name, and instance id
	 * @param preferenceDTO
	 * @return 
	 */
	public List<UserPreferenceDTO> getApplicationSettings(UserPreferenceDTO preferenceDTO){
		List <UserPreferenceDTO> userPreferencesList = null;
		userPreferencesList = getNamedParameterJdbcTemplate().query(GET_APPLICATION_SETTINGS, preferenceDTO.getMappedParameters(), new RowMapper<UserPreferenceDTO>() {
			public UserPreferenceDTO mapRow(ResultSet rs, int rowNum) throws SQLException{
				UserPreferenceResultSetExtractor extractor = new UserPreferenceResultSetExtractor();
				return extractor.extractData(rs);
			}
		});
		return userPreferencesList;
    }

	/**
	 * Updates the preference value based on a pre-existing userId, key, and appName
	 */
	public void updatePreference(UserPreferenceDTO preferenceDTO){
		getNamedParameterJdbcTemplate().update(UPDATE_PREFERENCE, preferenceDTO.getMappedParameters());
	}
	
	public void updateAppPreference(UserPreferenceDTO preferenceDTO){
		getNamedParameterJdbcTemplate().update(UPDATE_PREFERENCE_BY_APP, preferenceDTO.getMappedParameters());
	}
	
	/**
	 * Creates a new application preference 
	 */
	public void createNewPreference(UserPreferenceDTO preferenceDTO){
		OracleSequenceMaxValueIncrementer oinc = new OracleSequenceMaxValueIncrementer();
		oinc.setDataSource(getDataSource());
		oinc.setIncrementerName(autoIncrementSequence);
		preferenceDTO.setId(String.valueOf(oinc.nextLongValue()));
		getNamedParameterJdbcTemplate().update(CREATE_NEW_PREFERENCE, preferenceDTO.getMappedParameters());
	}
	
	/**
	 * Deletes a preference in the Preference table based on userId, key, and appname
	 */
	public void deletePreference(UserPreferenceDTO preferenceDTO){
		getNamedParameterJdbcTemplate().update(DELETE_PREFERENCE, preferenceDTO.getMappedParameters());
	}
	
	/**
	 * Deletes a preference in the Preference table based on userId, key, and appname
	 */
	public void deletePreferenceById(UserPreferenceDTO preferenceDTO){
		getNamedParameterJdbcTemplate().update(DELETE_PREFERENCE_BY_ID, preferenceDTO.getMappedParameters());
	}
	
	public void deleteAllPreferences(){
		getJdbcTemplate().execute(DELETE_ALL_PREFERENCES);
	}

	public String getPreferenceTable() {
		return preferenceTable;
	}

	public void setPreferenceTable(String preferenceTable) {
		this.preferenceTable = preferenceTable;
	}

	public String getAutoIncrementSequence() {
		return autoIncrementSequence;
	}

	public void setAutoIncrementSequence(String autoIncrementSequence) {
		this.autoIncrementSequence = autoIncrementSequence;
	}
	
}
