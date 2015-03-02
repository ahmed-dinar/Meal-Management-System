package Dialogs;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DB.checkPassword;
import DB.dbConnect;


public class changepassDialog {
	
	private JTextField oldpass;
	private JTextField newpass;
	
	public changepassDialog(String path) {
		
		oldpass = new JTextField();
		oldpass.setPreferredSize(new Dimension(400,25));
		
		newpass = new JTextField();
		newpass.setPreferredSize(new Dimension(400,25));
		
		JPanel panel = new JPanel(new GridLayout(2,2));

		JLabel old = new JLabel("Old Password");
		JLabel newp = new JLabel("New Password");
		
		panel.add(old);
		panel.add(oldpass);
		panel.add(newp);
		panel.add(newpass);
		
		String op[] = {"OK","Cancel"};
	        
	    int key= JOptionPane.showOptionDialog(null, panel, "Configure", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, op, op[0]);
		switch (key) {
			case 0:
				String pass = oldpass.getText().toString();
				if(pass == null || pass.equals("")){
					JOptionPane.showMessageDialog(null, "Please Enter a password!");
				}
				else {
					
					if(new checkPassword(pass,path).isCorrect()){
						change(path,newpass.getText().toString());
					}
					else {
						JOptionPane.showMessageDialog(null, "Password does not match!");
					}
					
				}
				
				break;
	
			default:
			
		}
	}
	
	
	private void change(String path,String pass){
		
		Connection conn  = null;
		
		new dbConnect(path);
	
		conn = dbConnect.GetConnection();
		PreparedStatement st = null;
		ResultSet set = null ;
		try {
			
			st = conn.prepareStatement("UPDATE `login`  SET `password` = " + pass);
			
			
			st.execute();
			
			JOptionPane.showMessageDialog(null, "Password changed! Restart application to get effect.");
		
			
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
	
}
