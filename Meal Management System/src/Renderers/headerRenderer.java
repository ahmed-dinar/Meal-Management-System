package Renderers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;


public class headerRenderer extends DefaultTableCellRenderer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void DefaultTableHeaderCellRenderer() {
		
		
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		
		
		setBackground(Color.WHITE);
	
		setHorizontalAlignment(JLabel.CENTER);
		
		super.getTableCellRendererComponent(table, value,
				isSelected, hasFocus, row, column);
		
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(240,240,240)),(Border) new EmptyBorder(new Insets(5, 0, 5, 0))));
		
		return this;
		
	}
	
	
}

