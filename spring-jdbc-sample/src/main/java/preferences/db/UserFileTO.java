package preferences.db;

import java.sql.Types;
import java.util.Date;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

public class UserFileTO {
	
	private long id;
	private String fileName;
	private String fileSize;
	private String fileType;
	private String author;
	private byte[] fileObject;
	private String instanceId;
	private String appName;
	private String description;
	private Date creationDate;
	private Date lastUpdate;
	
	public Object[] getParametersList() {
		Object[] args = new Object[] {getId(), getFileName(), getFileType(), getFileObject(), getAuthor(), getAppName(), getInstanceId(), getDescription()};
		return args;
	}

	public MapSqlParameterSource getMappedParameters() {
		MapSqlParameterSource userTO = new MapSqlParameterSource();
		userTO.addValue("ID", getId(), java.sql.Types.INTEGER);
		userTO.addValue("AUTHOR", getAuthor(), java.sql.Types.VARCHAR);
		userTO.addValue("INSTANCEID", getInstanceId(), java.sql.Types.VARCHAR);
		userTO.addValue("APPNAME", getAppName(), java.sql.Types.VARCHAR);
		userTO.addValue("DESCRIPTION", getDescription(), java.sql.Types.VARCHAR);
		userTO.addValue("FILENAME", getFileName(),java.sql.Types.VARCHAR);
		userTO.addValue("FILETYPE", getFileType(),java.sql.Types.VARCHAR);
		userTO.addValue("FILEOBJECT", getFileObject(),java.sql.Types.BINARY);
		userTO.addValue("CREATION_DATE", getCreationDate(),java.sql.Types.DATE);
		userTO.addValue("LAST_UPDATE", getLastUpdate(),java.sql.Types.DATE);
		return userTO;
	}
	
	public int[] getTypeParameters() {
		int types[] = new int[]{Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.BINARY, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR};
		return types;
	}
	
	@Override
	public String toString() {
	    return getId() + "/" + getFileName() + "/" + getFileType() + "/" + getAuthor() + "/" + getInstanceId() + "/" + getAppName() + 
	    	"/" + getDescription() + "/" + getFileObject() + "/";
	}
	public long getId() {
		return id;
	}
	public byte[] getFileObject() {
		return fileObject;
	}
	public void setFileObject(byte[] fileObject) {
		this.fileObject = fileObject;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
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
	
}
