package preferences.dao;

import java.util.List;

import preferences.db.UserFileDB;
import preferences.db.UserFileTO;


public interface UserFileDAO {
	
	public abstract UserFileDB getUserFileDB();
	
	public abstract void uploadFile(UserFileTO config) throws Exception;
	
	public abstract UserFileTO downloadFile(UserFileTO config) throws Exception;
	
	public abstract List<UserFileTO> downloadFileList(UserFileTO userTO) throws Exception;
}
