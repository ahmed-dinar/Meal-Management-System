package Dialogs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Formatter;
import javax.swing.JFileChooser;
import javax.xml.transform.TransformerFactoryConfigurationError;
import Interfaces.IManager;

public class SaveAsDialog implements IManager {

	public SaveAsDialog(){
		
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Chosse directory");
		chooser.setAcceptAllFileFilterUsed(false);
		if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			
			String files=chooser.getSelectedFile().getPath() + "\\mealRecord.txt";
			
			File save = new File(files);

			if(!save.exists()){
				try {
					new Formatter( files );
					save = new File( files );
				} 
				catch (FileNotFoundException  | TransformerFactoryConfigurationError e) {}
			}
			
			
			PrintWriter os;
			try {
				os = new PrintWriter(save);
				String vl;
				
				/* date and time*/
				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				
				
				os.println(dateFormat.format(cal.getTime()));
				os.println();
				
				
				os.println(meelRate.getText());
				os.println(totalMeel.getText());
				os.println(totalSpend.getText());
				os.println(remainAmmount.getText());
				
				os.println();
				os.println();
				
				
				/* meals*/
				os.println("Meals:");
				for (int col = 0; col < meelTable.getColumnCount(); col++) {
			        os.printf("%8s",meelTable.getColumnName(col));
			    }
				os.println();
				for (int row = 0; row < meelTable.getRowCount() ; row++) {
				    for (int col = 0; col < meelTable.getColumnCount(); col++) {
				    	
				    	vl="0.0";
				    	
				    	if(meelTable.getValueAt(row, col)!=null) vl=meelTable.getValueAt(row, col).toString();
				    	
				        os.printf("%8s", vl);
				    }
				    os.println();
				}
				
				os.println();
				os.println();
				
				
				os.println("Payment:");
				for (int col = 0; col < accountTable.getColumnCount(); col++) {
			        os.printf("%20s",accountTable.getColumnName(col));
			    }
				os.println();
				for (int row = 0; row < accountTable.getRowCount() ; row++) {
				    for (int col = 0; col < accountTable.getColumnCount(); col++) {
				    	
				    	vl="0.0";
				    	
				    	if(accountTable.getValueAt(row, col)!=null) vl=accountTable.getValueAt(row, col).toString();
				    	
				        os.printf("%20s", vl);
				    }
				    os.println();
				}
				
				os.println();
				os.println();
				
				
				os.println("Bazar:");
				for (int col = 1; col < bazarTable.getColumnCount(); col++) {
			        os.printf("%50s",bazarTable.getColumnName(col));
			    }
				os.println();
				for (int row = 0; row < bazarTable.getRowCount() ; row++) {
				    for (int col = 1; col < bazarTable.getColumnCount(); col++) {
				    	
				    	vl="0.0";
				    	
				    	if(bazarTable.getValueAt(row, col)!=null) vl=bazarTable.getValueAt(row, col).toString();
				    	
				        os.printf("%50s", vl);
				    }
				    os.println();
				}
				
				os.println();
				os.println();
				
				os.close();

				
			} catch (FileNotFoundException e) {
				//e.printStackTrace();
			}
			
		}
	}
	
	
}
