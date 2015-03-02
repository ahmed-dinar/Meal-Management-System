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


public class accountTableModel {


	private boolean isEdited = false;
	private Vector<String> tablecolumnName;
	private Vector<Vector<String>> tableData;
	private int rowCount=0;
	private int ColumnCount=0;
	private TableModel model;

	public accountTableModel(String selectedDatabse,String selectedMonth,meelTableModel meelTabModel,statusTableModel statusTabModel,String path) {
		setTotalMeel(selectedDatabse,selectedMonth,meelTabModel,statusTabModel.getMeelRate(),path);
		tableSetup(selectedDatabse,selectedMonth,path);
		model = new tableModel();
	}
	
	public void setTotalMeel(String selectedDatabse,String selectedMonth,meelTableModel meelTabModel,String mr,String path) {
		
		int cl = meelTabModel.getColumNuber()-1;
		Vector<Vector<String>> data = meelTabModel.gettabledata();
		
		for(int i=0; i<meelTabModel.getRowNumber(); i++){
			String STATEMENT = "UPDATE `".concat(selectedDatabse).concat("` SET ");
			STATEMENT = STATEMENT.concat(" `totalmeel` = '").concat(data.get(i).get(cl)).concat("',");
			STATEMENT = STATEMENT.concat(" `meelCost(tk)` = ").concat(data.get(i).get(cl)).concat("*").concat(mr).concat("");
			STATEMENT = STATEMENT.concat(" WHERE name = '").concat( data.get(i).get(0).toString()).concat("'");
			STATEMENT = STATEMENT.concat(" AND month = '").concat(selectedMonth).concat("'");
			executeStatement(STATEMENT,path);
		}
	}
	
	public void tableSetup(String selectedDatabse,String selectedMonth,String path) {
		
		Connection connection=null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String STATEMENT = "SELECT name,`payIn(tk)`,totalmeel,`meelCost(tk)`,`payIn(tk)`-`meelCost(tk)` AS `Back(tk)` ";
		
		STATEMENT = STATEMENT.concat(" FROM  `").concat(selectedDatabse).concat("` WHERE month='");
		STATEMENT = STATEMENT.concat(selectedMonth).concat("' ORDER BY `meelCost(tk)` DESC");
		try {
			
			new dbConnect(path);
			connection = dbConnect.GetConnection();
			
			try {
				statement =  (PreparedStatement) connection.prepareStatement(STATEMENT);
			} catch (Exception e) {
			}
			

			resultSet = statement.executeQuery();
			
			ResultSetMetaData metaData = (ResultSetMetaData) resultSet.getMetaData();
			int row_count = metaData.getColumnCount();
			tablecolumnName = new Vector<String>(row_count);
			tableData = new Vector<Vector<String>>();
			
			ColumnCount = 0;
			for(int i=1; i<=row_count; i++){
				tablecolumnName.add(metaData.getColumnName(i));	
				ColumnCount++;
			}
			
			rowCount = 0;
			while(resultSet.next()){
				
				Vector<String> vector = new Vector<String>(row_count);
				
				for(int i=1; i<=row_count; i++){
					vector.add(resultSet.getString(i));
				}
				tableData.add(vector);
				rowCount++;
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
			return ColumnCount;
		}

		public int getRowCount() {
			return rowCount;
		}

		 public String getColumnName(int col) {
		      return tablecolumnName.get(col).toString();
		}
		
		public Object getValueAt(int row, int col) {
			return tableData.get(row).get(col);
		}
		
		 public boolean isCellEditable(int row, int col) {
			 if(col == 0 || col == 2 ||  col == 3 || col == 4){
				 return false;
			 }
			 else {
				 return true;
			}
		 }
		 
		 public void setValueAt(Object value, int row, int col) {

				tableData.get(row).set(col, (String) value);
				fireTableCellUpdated(row, col);
				isEdited = true;

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
		
	}
	
	public void updateModel(String database,String selectedMonth,String path) {
		
		for(int i=0; i<rowCount; i++){
			
			String STATEMENT = "UPDATE `".concat(database).concat("` SET ");
			boolean isedit = false;
			int edits = 0;
			for(int j=1;j<ColumnCount-1; j++){
				
				if( tableData.get(i).get(j) != null){
					isedit = true;
					if(edits>0){
						STATEMENT = STATEMENT.concat(",");
					}
					STATEMENT = STATEMENT.concat("`").concat(tablecolumnName.get(j).toString()).concat("` = '").concat((String) tableData.get(i).get(j)).concat("'");
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
	
	public void executeStatement(String statement,String dbP) {
		
		Connection connection=null;
		PreparedStatement prestatement = null;
		try {
			new dbConnect(dbP);
			connection = dbConnect.GetConnection();
			prestatement =  (PreparedStatement) connection.prepareStatement(statement);
			prestatement.execute();
		} catch (Exception e) {
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
	
	
	public TableModel getModel(){
		return model;
	}
	

	
	public boolean IsEdited() {
		return isEdited;
	}
	
}

