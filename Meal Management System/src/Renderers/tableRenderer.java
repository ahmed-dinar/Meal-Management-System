package Renderers;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

	public class tableRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
		 

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

		  public Component getTableCellRendererComponent(JTable table, Object value,boolean isSelected, boolean hasFocus, int row, int column) {
			  
		    Component renderer = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		    
		 
		    
		    ((JLabel) renderer).setOpaque(true);
		    Color foreground, background;
		    if (isSelected) {
		      foreground = Color.WHITE;
		      background = new Color(78,166,234);
		    } 
		    else {
		    	foreground = Color.BLACK;
		    	background = Color.WHITE;
		    }
		    
		    renderer.setForeground(foreground);
		    renderer.setBackground(background);
		    
	
		    ((JLabel) renderer).setHorizontalAlignment(JLabel.CENTER);
		    
		    
		    return renderer;
		  }
        
	}
