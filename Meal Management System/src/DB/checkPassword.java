package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class checkPassword {

	private boolean isC;
	
	public checkPassword(String pass,String path) {
		Connection conn  = null;
		
		if(path.contains(".db")) new dbConnect(path);
		
		else new dbConnect(path+"/mealManagement.db");
		
		conn = dbConnect.GetConnection();
		PreparedStatement st = null;
		ResultSet set = null ;
		try {
			st = conn.prepareStatement("SELECT password FROM login");
			set = st.executeQuery();
			String p = null;
			while(set.next()){
				p = set.getString("password");
			}
			if(p.equalsIgnoreCase(pass)){
				isC = true;
			}
			else {
				isC = false;
			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		finally{
			if(conn != null){
				try {
					conn.close();
				} 
				catch (SQLException e) {}
			}
			if(st!=null){
				try {
					st.close();
				} catch (SQLException e) {}
			}
			if(set != null){
				try {
					set.close();
				} catch (SQLException e) {}
			}
		}
	}
	
	
	public boolean isCorrect() {
		return isC;
	}
	
}
