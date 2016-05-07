package preferences.db;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.incrementer.OracleSequenceMaxValueIncrementer;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;

public class UserFileDB extends NamedParameterJdbcDaoSupport{
	
	private String fileTable;
	private String autoIncrementSequence;
	private String CREATE_NEW_FILE;
	private String GET_FILE_BY_ID;
	private String GET_ALL_FILE;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    protected void initDao() {
		CREATE_NEW_FILE = "INSERT INTO "+ fileTable +" (ID,FILENAME,FILETYPE,FILEOBJECT,AUTHOR,APPNAME,INSTANCEID,DESCRIPTION,CREATION_DATE,LAST_UPDATE) VALUES " +
			"(?,?,?,?,?,?,?,?,SYSDATE,SYSDATE)";
		GET_FILE_BY_ID = "SELECT * FROM " + fileTable + " WHERE ID=:ID";
		GET_ALL_FILE = "SELECT * FROM " + fileTable;
	}
	
	public void saveFile(final UserFileTO userFileTO) throws Exception{
		userFileTO.setId(getNextSeq());
		final InputStream blobIs = new ByteArrayInputStream(userFileTO.getFileObject());
		try {
			LobHandler lobHandler = new DefaultLobHandler();
			getJdbcTemplate().execute(CREATE_NEW_FILE, 
				new AbstractLobCreatingPreparedStatementCallback(lobHandler) {
					protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
						ps.setLong(1, userFileTO.getId());
						ps.setString(2, userFileTO.getFileName());
						ps.setString(3, userFileTO.getFileType());
						lobCreator.setBlobAsBinaryStream(ps, 4, blobIs, userFileTO.getFileObject().length);
						ps.setString(5, userFileTO.getAuthor() != null? userFileTO.getAuthor().toLowerCase() : null);
						ps.setString(6, userFileTO.getAppName() != null? userFileTO.getAppName().toLowerCase() : null);
						ps.setString(7, userFileTO.getInstanceId() != null? userFileTO.getInstanceId().toLowerCase() : null);
						ps.setString(8, userFileTO.getDescription());
					}
				}
			);
		} catch (Exception e) {
			logger.error("UserFileDB.saveFiles:: ERROR = ", e);
			throw e;
		} finally{
			blobIs.close();
		}
	}
	
	public List<UserFileTO> getFile(UserFileTO userFileTO) throws Exception{
		
		final LobHandler lobHandler = new DefaultLobHandler();
		List<UserFileTO> fileList = getNamedParameterJdbcTemplate().query(GET_FILE_BY_ID, userFileTO.getMappedParameters(), 
			new RowMapper<UserFileTO>() {
				public UserFileTO mapRow(ResultSet rs, int rowNum) throws SQLException{
					return extractData(rs, lobHandler);
				}
			}
		);
		return fileList;
	}
	
	public List<UserFileTO> getFileWithCondition(UserFileTO userFileTO) throws Exception{
		
		String filterCriterion = "";
		if (StringUtils.isNotEmpty(userFileTO.getAppName())){
			filterCriterion+=" WHERE APPNAME=lower(:APPNAME) "; 
			if (StringUtils.isNotEmpty(userFileTO.getInstanceId())){
				filterCriterion+="AND INSTANCEID=lower(:INSTANCEID) "; 
			}
			if (StringUtils.isNotEmpty(userFileTO.getAuthor())){
				filterCriterion+="AND AUTHOR=lower(:AUTHOR) "; 
			}
		}
		String FILTERED_SQL = GET_ALL_FILE + filterCriterion;
		
		final LobHandler lobHandler = new DefaultLobHandler();
		List<UserFileTO> fileList = getNamedParameterJdbcTemplate().query(FILTERED_SQL, userFileTO.getMappedParameters(), 
			new RowMapper<UserFileTO>() {
				public UserFileTO mapRow(ResultSet rs, int rowNum) throws SQLException{
					return extractData(rs, lobHandler);
				}
			}
		);
		return fileList;
	}
	
	public long getNextSeq(){
		OracleSequenceMaxValueIncrementer oinc = new OracleSequenceMaxValueIncrementer();
		oinc.setDataSource(getDataSource());
		oinc.setIncrementerName(autoIncrementSequence);
		return oinc.nextLongValue();
	}
	
	private UserFileTO extractData(ResultSet rs, LobHandler lobHandler) throws SQLException, DataAccessException{
    	UserFileTO userFile = new UserFileTO();
    	userFile.setId(rs.getLong("ID"));
    	userFile.setAuthor(rs.getString("AUTHOR"));
    	userFile.setInstanceId(rs.getString("INSTANCEID"));
    	userFile.setAppName(rs.getString("APPNAME"));
    	userFile.setDescription(rs.getString("DESCRIPTION"));
    	userFile.setFileName(rs.getString("FILENAME"));
    	userFile.setFileType(rs.getString("FILETYPE"));
    	userFile.setFileObject(lobHandler.getBlobAsBytes(rs, "FILEOBJECT"));
    	userFile.setCreationDate(rs.getTimestamp("CREATION_DATE"));
    	userFile.setLastUpdate(rs.getTimestamp("LAST_UPDATE"));
        return userFile;
    }

	public JdbcTemplate getJdbc(){
		return getJdbcTemplate();
	}
	
	public NamedParameterJdbcTemplate getNamedJdbc(){
		return getNamedParameterJdbcTemplate();
	}
	
	public String getFileTable() {
		return fileTable;
	}
	public void setFileTable(String fileTable) {
		this.fileTable = fileTable;
	}
	public String getAutoIncrementSequence() {
		return autoIncrementSequence;
	}
	public void setAutoIncrementSequence(String autoIncrementSequence) {
		this.autoIncrementSequence = autoIncrementSequence;
	}
	
	public static void main(String[] args){
		UserFileDB db = new UserFileDB();
		db.setFileTable("RG_APP_IMAGE");
		db.setAutoIncrementSequence("RG_APP_IMAGE_SEQ");
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@nj09mhf0053.mhf.mhc:1521:documdev");
		dataSource.setUsername("lap_yip_yeung");
		dataSource.setPassword("lap_yip_yeung");
		db.setDataSource(dataSource);
		db.initDao();
		
		/*UserFileTO fileTO = new UserFileTO();
		fileTO.setAppName("rgcms");
		fileTO.setInstanceId("cfs");
		try {
			List<UserFileTO> fileList = db.getFileWithCondition(fileTO);
			System.out.println("============= " + fileList.size());
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		UserFileTO fileTO = new UserFileTO();
		fileTO.setFileName("sandp.jpg");
		fileTO.setFileType("jpg");
		
		byte fileContent[] = null;
		try {
			File file = new File("C:/Users/lap_yip_yeung/Desktop/sandp.jpg");
			FileInputStream fin = new FileInputStream(file);
			fileContent = new byte[(int)file.length()];
			fin.read(fileContent);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		fileTO.setFileObject(fileContent);
		fileTO.setAuthor("lap_yip_yeung");
		fileTO.setAppName("rgcms");
		fileTO.setInstanceId("cfs");
		fileTO.setDescription("This is for Testing");
		
		try {
			db.saveFile(fileTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
