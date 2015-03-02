package TModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import DB.dbConnect;

public class meelTableModel {

	private boolean isEdited = false;
	private Vector<String> tablecolumnName;
	private Vector<Vector<String>> tableData;
	private TableModel model;
	private String SelectedMonth;
	
	public meelTableModel() {
		// TODO Auto-generated constructor stub
	}
	
	public meelTableModel(String selectedDatabse,String selectedMonth,String path) {
		this.SelectedMonth = selectedMonth;
		tableSetup(selectedDatabse,selectedMonth,path);
		model = new tableModel();
	}
	
	public void tableSetup(String selectedDatabse,String selectedMonth,String path) {
		
		Connection connection=null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			
			new dbConnect(path);
			connection = dbConnect.GetConnection();
			
			String STATEMENT = "SELECT name,`01`,`02`,`03`,`04`,`05`,`06`,`07`,`08`,`09`,`10`,`11`,`12`,`13`,`14`,`15`,`16`,`17`,`18`,`19`,`20`,`21`,`22`,`23`,`24`,`25`,`26`,`27`,`28`";
			if(selectedMonth.compareTo("april") == 0 || selectedMonth.compareTo("june") == 0 || selectedMonth.compareTo("september") == 0 || selectedMonth.compareTo("november") == 0){
				STATEMENT = STATEMENT.concat(",`29`,`30`"); 
			}
			else if(selectedMonth.compareTo("february") == 0){
				//STATEMENT = STATEMENT.concat(",`29`");
			}
			else{
				STATEMENT = STATEMENT.concat(",`29`,`30`,`31`"); 
			}
			
			STATEMENT = STATEMENT.concat(
					",ifnull(`01`,0)+" +
					"ifnull(`02`,0)+" +
					"ifnull(`03`,0)+" +
					"ifnull(`04`,0)+" +
					"ifnull(`05`,0)+" +
					"ifnull(`06`,0)+" +
					"ifnull(`07`,0)+" +
					"ifnull(`08`,0)+" +
					"ifnull(`09`,0)+" +
					"ifnull(`10`,0)+" +
					"ifnull(`11`,0)+" +
					"ifnull(`12`,0)+" +
					"ifnull(`13`,0)+" +
					"ifnull(`14`,0)+" +
					"ifnull(`15`,0)+" +
					"ifnull(`16`,0)+" +
					"ifnull(`17`,0)+" +
					"ifnull(`18`,0)+" +
					"ifnull(`19`,0)+" +
					"ifnull(`20`,0)+" +
					"ifnull(`21`,0)+" +
					"ifnull(`22`,0)+" +
					"ifnull(`23`,0)+" +
					"ifnull(`24`,0)+" +
					"ifnull(`25`,0)+" +
					"ifnull(`26`,0)+" +
					"ifnull(`27`,0)+" +
					"ifnull(`28`,0)+" +
					"ifnull(`29`,0)+" +
					"ifnull(`30`,0)" +
					"as `Total`"+
					"FROM  `" + selectedDatabse + "`" +" WHERE month='" + selectedMonth + "' ORDER BY `Total` DESC"
					);
			
			
			try {
				statement =  (PreparedStatement) connection.prepareStatement(STATEMENT);
			} catch (Exception e) {
			}
			
			resultSet = statement.executeQuery();
			
			ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
			int row_count = metaData.getColumnCount();
			tablecolumnName = new Vector<String>(row_count);
			tableData = new Vector<Vector<String>>();
			
			for(int i=1; i<=row_count; i++){
				tablecolumnName.add(metaData.getColumnName(i));	
			}
			while(resultSet.next()){
				Vector<String> vector = new Vector<String>(row_count);
				for(int i=1; i<=row_count; i++){
					vector.add(resultSet.getString(i));
				}
				tableData.add(vector);
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
	
	class tableModel extends AbstractTableModel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public int getColumnCount() {
			return tablecolumnName.size();
		}

		public int getRowCount() {
			return tableData.size();
		}

		 public String getColumnName(int col) {
		      return tablecolumnName.get(col).toString();
		}
		
		public Object getValueAt(int row, int col) {
			return tableData.get(row).get(col);
		}
		
		 public boolean isCellEditable(int row, int col) {
			 boolean Rt = true;
			 if(SelectedMonth.compareTo("april") == 0 || SelectedMonth.compareTo("june") == 0 || SelectedMonth.compareTo("september") == 0 || SelectedMonth.compareTo("november") == 0){
				 if(col == 31 || col == 0 ){
					 Rt =  false;
				 }
				 else {
					 Rt =  true;
				 }
			 }
			else if(SelectedMonth.compareTo("february") == 0){
				
			}
			else{
				 if(col == 32 || col == 0 ){
					 Rt =  false;
				 }
				 else {
					 Rt =  true;
				}
			}
			return Rt;
		 }
		
		 public void setValueAt(Object value, int row, int col) {
			tableData.get(row).set(col, (String) value);
			fireTableCellUpdated(row, col);
			isEdited = true;
		 }
		
	}
	
	public TableModel getModel(){
		return model;
	}
	
	public void updateModel(String database,String selectedMonth,String path) {
		
		for(int i=0; i<tableData.size(); i++){
			String STATEMENT = "UPDATE `".concat(database).concat("` SET ");
			boolean isedit = false;
			int edits = 0;
			for(int j=1;j<tablecolumnName.size()-1; j++){
				
				if( tableData.get(i).get(j) != null){
					isedit = true;
					if(edits>0){
						STATEMENT = STATEMENT.concat(",");
					}
					
					STATEMENT = STATEMENT.concat("`").concat(tablecolumnName.get(j).toString()).concat("` = ");

					if( tableData.get(i).get(j).toString().compareTo("") == 0 ){
						STATEMENT = STATEMENT.concat("null");
					}
					else{
						STATEMENT = STATEMENT.concat("'").concat((String) tableData.get(i).get(j)).concat("'");
					}
					
					edits++;
				}

			}
			if(isedit){
				STATEMENT = STATEMENT.concat(" WHERE name = '").concat( tableData.get(i).get(0).toString()).concat("'");
				STATEMENT = STATEMENT.concat(" AND month = '").concat(selectedMonth).concat("'");
				executeStatement(STATEMENT,path);
			}
		}
	}
	
	public void addMember(String database,String selectedMonth,String memberName,String path){
		
		String STATEMENT = "INSERT INTO `".concat(database).concat("`(month,name,`payIn(tk)`,totalmeel ) VALUES('").concat(selectedMonth).concat("','").concat(memberName).concat("','0','0')");
		executeStatement(STATEMENT,path);
	}
	
	public void deleteMember(String database,String selectedMonth,String memberName,String path){
		
		String STATEMENT = "DELETE FROM `".concat(database).concat("`WHERE  month = '").concat(selectedMonth).concat("' AND name = '").concat(memberName).concat("'");
		executeStatement(STATEMENT,path);
		
	}
	
	public void executeStatement(String statement,String dbPath) {
		
		Connection connection=null;
		PreparedStatement prestatement = null;
		try {
			new dbConnect(dbPath);
			connection = dbConnect.GetConnection();
			
			prestatement =  (PreparedStatement) connection.prepareStatement(statement);
			prestatement.execute();
			
		} 
		catch (Exception e) {}
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
	
	
	public boolean IsEdited() {
		return isEdited;
	}
	
	public int getColumNuber(){
		return tablecolumnName.size();
	}
	
	public int getRowNumber(){
		return tableData.size();
	}
	
	public  Vector<Vector<String>> gettabledata(){
		return tableData;
	}

	
}

