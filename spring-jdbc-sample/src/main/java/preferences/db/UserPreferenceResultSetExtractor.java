package preferences.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;


/**
 * Class used to efficiently extract the data from the SQL ResultSet
 * @author rolando_rafanan
 * 
 */
public class UserPreferenceResultSetExtractor implements ResultSetExtractor <UserPreferenceDTO>{
	
	private static final Logger logger = LoggerFactory.getLogger(UserPreferenceResultSetExtractor.class);
	
	
    public UserPreferenceResultSetExtractor(){}
    
    public UserPreferenceDTO extractData(ResultSet rs) throws SQLException, DataAccessException{
        UserPreferenceDTO userPreference = new UserPreferenceDTO();
        userPreference.setId(rs.getString("ID"));
        userPreference.setUserId(rs.getString("USERID"));
        userPreference.setInstanceId(rs.getString("INSTANCEID"));
        userPreference.setAppName(rs.getString("APPNAME"));
        userPreference.setDescription(rs.getString("DESCRIPTION"));
        userPreference.setKey(rs.getString("KEY"));
        userPreference.setValue(clobToString(rs.getClob("VALUE")));
        userPreference.setCreationDate(rs.getTimestamp("CREATION_DATE"));
        userPreference.setLastUpdate(rs.getTimestamp("LAST_UPDATE"));
        return userPreference;
    }
    
    private static String clobToString(Clob clb) throws SQLException {
		if (clb == null){
			return  "";
		}
		StringBuffer str = new StringBuffer();
		String string;
		try {
			 //If the constructor throws an exception, the finally block will NOT execute
			BufferedReader bufferRead = new BufferedReader(clb.getCharacterStream());
			try{
				while ((string = bufferRead.readLine())!=null){
						  str.append(string);
				}
			}
			finally {
				//no need to check for null
		        //any exceptions thrown here will be caught by 
		        //the outer catch block
				bufferRead.close();
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
			logger.error(e.getLocalizedMessage());
		} 
		return str.toString();
    }        

    
}
