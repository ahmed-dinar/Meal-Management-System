package DB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class getYearList {

	private Vector<String> yearName;
	public getYearList(String path) {
		
		Connection connection=null;
		yearName = new Vector<String>();
		try {
		
			new dbConnect(path);
			connection = dbConnect.GetConnection();
			DatabaseMetaData metaData = (DatabaseMetaData) connection.getMetaData();
			ResultSet resultSet = metaData.getTables(null, null, null,  new String[] {"TABLE"});
			while (resultSet.next()) {
				String tablename  = resultSet.getString("TABLE_NAME");
				if(isNumeric(tablename) && tablename != null){
					yearName.add(tablename);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	 public boolean isNumeric(String s) {
		  try { 
		        Float.parseFloat(s); 
		  } 
		  catch(NumberFormatException e) { 
		        return false; 
		  }
		  return true;
	}
	
	public Vector<String> getYears() {
		return yearName;
	}
	
}

