package DB;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Formatter;

import javax.swing.JOptionPane;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

public class createTables {

	private static String DB_FILE_NAME = "mealManagement.db";
	private static String[] op = {"Ok","Cancel"};
	private Connection conn  = null;
	private String password;
	private  String[] monthsName = { "january", "february", "march", "april", "may", "june", "july",
	        "august", "september", "october", "november", "december" };
	
	public createTables(String path,String password) {
		this.password = password;
		if(!isDb(path)){
			createDbFile(path);
			new dbConnect(Paths.get(path, DB_FILE_NAME).toString());
			conn = dbConnect.GetConnection();
			createAll();
		}
		else {
			int choise = JOptionPane.showOptionDialog(null,msg(path) , "db already exists", JOptionPane.NO_OPTION, 0, null, op, op[1]);
			if(choise == 0){
				deleteDbFile(path);
				createDbFile(path);
				new dbConnect(Paths.get(path, DB_FILE_NAME).toString());
				conn = dbConnect.GetConnection();
				createAll();
			}
		}
	}
	
	private void deleteDbFile(String path) {
		File file = new File(Paths.get(path, DB_FILE_NAME).toString());
		file.delete();
	}

	private String msg(String path) {
		String m = "A file already exists in path " + path + "\nDo you want to replace with a new databse?\n";
		m = m.concat("If you replace all data in that databse will loss.");
		return m;
	}
	
	public void createAll() {
		create_year_Table();
		create_bazar_Table();
		create_status_Table();
		create_login_Table();
		insert_password();
		Close();
	}

	private void createDbFile(String path) {
		try {
			new Formatter(Paths.get(path, DB_FILE_NAME).toString());
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	private static boolean isDb(String path) {
		boolean is = false;
		File file = new File(Paths.get(path, DB_FILE_NAME).toString());
		if(file.exists()){
			is = true;
		}
		return is;
	}
	
	public void create_year_Table(){
		
	   PreparedStatement st = null;

	   try {
			String STATEMENT = "CREATE TABLE IF NOT EXISTS `".concat(getCurrentYear()).concat("` ( id INTEGER PRIMARY KEY,");
			STATEMENT = STATEMENT.concat(" month VARCHAR(20) NOT NULL, name VARCHAR(30) NOT NULL,"
					 + " `01` double(7,1), "
					 + " `02` double(7,1), "
					 + " `03` double(7,1), "
					 + " `04` double(7,1), "
					 + " `05` double(7,1), "
					 + " `06` double(7,1), "
					 + " `07` double(7,1), "
					 + " `08` double(7,1), "
					 + " `09` double(7,1), "
					 + " `10` double(7,1), "
					 + " `11` double(7,1), "
					 + " `12` double(7,1), "
					 + " `13` double(7,1), "
					 + " `14` double(7,1), "
					 + " `15` double(7,1), "
					 + " `16` double(7,1), "
					 + " `17` double(7,1), "
					 + " `18` double(7,1), "
					 + " `19` double(7,1), "
					 + " `20` double(7,1), "
					 + " `21` double(7,1), "
					 + " `22` double(7,1), "
					 + " `23` double(7,1), "
					 + " `24` double(7,1), "
					 + " `25` double(7,1), "
					 + " `26` double(7,1), "
					 + " `27` double(7,1), "
					 + " `28` double(7,1), "
					 + " `29` double(7,1), "
					 + " `30` double(7,1), "
					 + " `31` double(7,1), "
					 + " `payIn(tk)` double(7,1), `totalmeel` double(7,1) NOT NULL, `meelCost(tk)` double(7,1)"
					 + " )");
				
			st = (PreparedStatement) conn.prepareStatement(STATEMENT);
			st.execute();
		} 
		 catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	    finally{
			if(st != null){
				try {
					st.close();
				} 
				catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
	    }  
	}
	
	public void create_bazar_Table(){
		
		   PreparedStatement st = null;
		   try {
				 
				String STATEMENT = "CREATE TABLE IF NOT EXISTS `bazar".concat(getCurrentYear()).concat("` ( id INTEGER PRIMARY KEY,");
				STATEMENT = STATEMENT.concat(" `month` VARCHAR(20) NOT NULL, `date` VARCHAR(20), `name` VARCHAR(20), `description` VARCHAR(1000),"
						+ " `amount` double(10,1) )");
				

				st = (PreparedStatement) conn.prepareStatement(STATEMENT);
				st.execute();
			} 
			 catch (SQLException e) {
				 JOptionPane.showMessageDialog(null, e.getMessage());
			}
		    finally{
				if(st != null){
					try {
						st.close();
					} 
					catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
		    }  
	}
	
	public void create_status_Table(){
		
	   PreparedStatement st = null;
	   try {
		   
			String STATEMENT = "CREATE TABLE IF NOT EXISTS `status".concat(getCurrentYear()).concat("` ( id INTEGER PRIMARY KEY,");
			STATEMENT = STATEMENT.concat(" `month` VARCHAR(20) NOT NULL, `totalmeel` double(10,1), `totalspend` double(10,1),"
					+ " `meelrate` double(10,1),`remain` double(10,1)  )");
			
			
			st = (PreparedStatement) conn.prepareStatement(STATEMENT);
			st.execute();
			
			for(int i=0; i<monthsName.length; i++){
				STATEMENT = "INSERT INTO `status".concat(getCurrentYear()).concat("` (`month`) VALUES('").concat(monthsName[i]).concat("')");
				st = null;
				st = (PreparedStatement) conn.prepareStatement(STATEMENT);
				st.execute();
			}
		} 
		 catch (SQLException e) {
			 JOptionPane.showMessageDialog(null, e.getMessage());
		}
	    finally{
			if(st != null){
				try {
					st.close();
				} 
				catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
	    }  
	}
	
	public void create_login_Table(){
		   PreparedStatement st = null;
		   try {
				String STATEMENT = "CREATE TABLE IF NOT EXISTS `login".concat("` ( `password` VARCHAR(20) NOT NULL)");
				st = (PreparedStatement) conn.prepareStatement(STATEMENT);
				st.execute();
			} 
			 catch (SQLException e) {
				 JOptionPane.showMessageDialog(null, e.getMessage());
			}
		    finally{
				if(st != null){
					try {
						st.close();
					} 
					catch (SQLException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
		    }  
	}
	
	private void insert_password() {
		PreparedStatement st = null;
		try {
			String STATEMENT = "INSERT INTO `login` ('password') VALUES('".concat(password).concat("')");
			st = (PreparedStatement) conn.prepareStatement(STATEMENT);
			st.execute();
		}
		 catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	    finally{
			if(st != null){
				try {
					st.close();
				} 
				catch (SQLException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
	    }  
	}
	

	public void Close() {
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getCurrentYear(){
		Calendar now = Calendar.getInstance();
		return Integer.toString(now.get(Calendar.YEAR));
	}
	
}
