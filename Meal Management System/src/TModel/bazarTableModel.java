package TModel;


import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import DB.dbConnect;

public class bazarTableModel {

	private boolean isEdited = false;
	private Vector<String> tablecolumnName;
	private Vector<Vector<String>> tableData;
	private TableModel model;
	
	public bazarTableModel() {
		
	}
	
	public bazarTableModel(String selectedDatabse,String selectedMonth,String path) {
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
			try {
				statement =  (PreparedStatement) connection.prepareStatement(
								"SELECT id,date,name,description,amount FROM  `bazar"+ selectedDatabse + "`" +" WHERE month='" + selectedMonth + "'"
							);
			} catch (Exception e) {
				e.printStackTrace();
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
	
	
	
	public void addBazarInModel(String database,String selectedMonth,String date,String name,String desc,String amount,String path) {
		
		String STATEMENT = "INSERT INTO `bazar".concat(database).concat("`(month,date,name,description,amount) VALUES");
		STATEMENT = STATEMENT.concat("('").concat(selectedMonth).concat("','").concat(date).concat("-").concat(selectedMonth).concat("-").concat(database).concat("','").concat(name).concat("','");
		STATEMENT = STATEMENT.concat(desc).concat("','").concat(amount).concat("')");
		executeStatement(STATEMENT,path);
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
			 if(col == 0 ){
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
		 
		 public Component getTableCellEditorComponent(JTable table, Object value, boolean   isSelected, int row, int column) {
			    JTextArea area = new JTextArea();
			    area.setLineWrap(true);
			    area.setText((String) value);
			    JScrollPane pane = new JScrollPane(area);
			    return pane;
		}
		
	}
	
	public void editBazar(String database,String selectedMonth,String id,String name,String cost,String desc,String path){

		String STATEMENT = "UPDATE `bazar".concat(database).concat("` SET `name` = '").concat(name).concat("',").concat(" `amount` = '").concat(cost);
		STATEMENT = STATEMENT.concat("', `description` = '").concat(desc).concat("' WHERE month = '").concat(selectedMonth).concat("'");
		STATEMENT = STATEMENT.concat(" AND `id` = '").concat(id).concat("'");
		executeStatement(STATEMENT,path);
	}
	
	public void deleteBazar(String database,String selectedMonth,String id,String path){
		String STATEMENT = "DELETE FROM `bazar".concat(database).concat("` WHERE  `id` = '").concat(id).concat("' AND month = '").concat(selectedMonth).concat("'");
		executeStatement(STATEMENT,path);
	}
	
	public void executeStatement(String statement,String path) {
		
		Connection connection=null;
		PreparedStatement prestatement = null;
		try {
			new dbConnect(path);
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

