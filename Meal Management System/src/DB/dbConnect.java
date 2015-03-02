package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class dbConnect {

	private static Connection connection = null;
	private static String path;
	
	public dbConnect(String path) {
		dbConnect.path = path;
		connect_host();
	}
	
	public void connect_host() {
		try {
			Class.forName("org.sqlite.JDBC");
		} 
		catch (Exception e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"jdbc Driver Error",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static Connection GetConnection() {
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:"+path);
		} 
		catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);
		}
		return connection;
	}
	
}

