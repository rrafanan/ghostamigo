package preferences.dao.impl;

import java.util.List;

import preferences.dao.UserFileDAO;
import preferences.db.UserFileDB;
import preferences.db.UserFileTO;


public class UserFileDAOImpl implements UserFileDAO  {

	private UserFileDB userFileDB;
	
	public UserFileTO downloadFile(UserFileTO userTO) throws Exception{
		List<UserFileTO> fileList = userFileDB.getFile(userTO);
		if (fileList.isEmpty())
			return new UserFileTO();
		else
			return fileList.get(0);
	}

	public List<UserFileTO> downloadFileList(UserFileTO userTO) throws Exception{
		List<UserFileTO> fileList = userFileDB.getFileWithCondition(userTO);
		return fileList;
	}
	
	public void uploadFile(UserFileTO userTO) throws Exception{
		userFileDB.saveFile(userTO);
	}
	
	public UserFileDB getUserFileDB() {
		return userFileDB;
	}
	public void setUserFileDB(UserFileDB userFileDB) {
		this.userFileDB = userFileDB;
	}
}
