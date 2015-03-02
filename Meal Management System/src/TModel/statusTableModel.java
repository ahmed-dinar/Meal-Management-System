package TModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DB.dbConnect;

public class statusTableModel {

	private String RemainAmount = "0.0";
	private String TotalMeel = "0.0";
	private String TotalSpend = "0.0";
	private String MeelRate = "0.0";
	
	public statusTableModel() {
		
	}
	
	public statusTableModel(String selectedDatabse,String selectedMonth,String path) {
		
		tableSetup(selectedDatabse,selectedMonth,path);
		modelSetup(selectedDatabse,selectedMonth,path);
		if(getTotalMeel().compareTo("0.0") != 0 && getTotalSpend().compareTo("0.0") != 0){
			MeelRate = Float.toString((Float.parseFloat(getTotalSpend())/Float.parseFloat(getTotalMeel())));
		}
		else{
			MeelRate = "0.0";
		}
	}
	
	public void tableSetup(String selectedDatabse,String selectedMonth,String path) {
		
		String STATEMENT  = "UPDATE `status".concat(selectedDatabse).concat("` SET `totalmeel` =  ( SELECT ");
		STATEMENT = STATEMENT.concat("COALESCE(");
		STATEMENT = STATEMENT.concat("SUM(COALESCE(`01`,0)+COALESCE(`02`,0)+COALESCE(`03`,0)+COALESCE(`04`,0)+COALESCE(`05`,0)+COALESCE(`06`,0)+");
		STATEMENT = STATEMENT.concat("COALESCE(`07`,0)+COALESCE(`08`,0)+COALESCE(`09`,0)+COALESCE(`10`,0)+COALESCE(`11`,0)+COALESCE(`12`,0)+COALESCE(`13`,0)+");
		STATEMENT = STATEMENT.concat("COALESCE(`14`,0)+COALESCE(`15`,0)+COALESCE(`16`,0)+COALESCE(`17`,0)+COALESCE(`18`,0)+COALESCE(`19`,0)+COALESCE(`20`,0)+");
		STATEMENT = STATEMENT.concat("COALESCE(`21`,0)+COALESCE(`22`,0)+COALESCE(`23`,0)+COALESCE(`24`,0)+COALESCE(`25`,0)+COALESCE(`26`,0)+COALESCE(`27`,0)+");
		STATEMENT = STATEMENT.concat("COALESCE(`28`,0)+COALESCE(`29`,0)+COALESCE(`30`,0)+COALESCE(`31`,0)),0)");
		STATEMENT = STATEMENT.concat(" FROM `").concat(selectedDatabse).concat("` WHERE month = '").concat(selectedMonth).concat("'),");
		STATEMENT = STATEMENT.concat(" totalspend = ( SELECT COALESCE(SUM(COALESCE(amount,0)),0) FROM `bazar").concat(selectedDatabse).concat("`");
		STATEMENT = STATEMENT.concat(" WHERE month = '").concat(selectedMonth).concat("'), ");
		STATEMENT = STATEMENT.concat(" remain = (SELECT  COALESCE(SUM(COALESCE(`payIn(tk)`,0)),0) FROM `").concat(selectedDatabse).concat("`");
		STATEMENT = STATEMENT.concat(" WHERE month = '").concat(selectedMonth).concat("') - ( SELECT  COALESCE(SUM(COALESCE(`amount`,0)),0) FROM `bazar");
		STATEMENT = STATEMENT.concat(selectedDatabse).concat("` WHERE month = '").concat(selectedMonth).concat("')");
		STATEMENT = STATEMENT.concat(" WHERE month = '").concat(selectedMonth).concat("'");
		
		Connection connection=null;
		PreparedStatement prestatement = null;
		
		try {
			new dbConnect(path);
			connection = dbConnect.GetConnection();
			prestatement =  (PreparedStatement) connection.prepareStatement(STATEMENT);
			prestatement.execute();
		}catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
			if(prestatement != null){
				try {
					prestatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void modelSetup(String selectedDatabse,String selectedMonth,String path) {
		
		Connection connection=null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String STATEMENT = "SELECT `totalmeel`,`totalspend`,`remain` FROM `status"+ selectedDatabse + "`" +" WHERE month='" + selectedMonth + "'";
		
		try {

			new dbConnect(path);
			connection = dbConnect.GetConnection();
			
			try {
				statement =  (PreparedStatement) connection.prepareStatement(STATEMENT);
				//System.err.println(STATEMENT);
			} catch (Exception e) {
				//e.printStackTrace();
			}
			resultSet = statement.executeQuery();
			while(resultSet.next()){
				TotalMeel = resultSet.getString(1);
				TotalSpend = resultSet.getString(2);
				RemainAmount = resultSet.getString(3);
				//System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
			}
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			
			if(statement != null){
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(connection != null){
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public String getTotalMeel(){
		return TotalMeel;
	}
	
	public String getTotalSpend(){
		return TotalSpend;
	}
	
	public String getRemainAmount(){
		return RemainAmount;
	}
	
	public String getMeelRate(){
		return MeelRate;
	}
	
	
}

