package Interfaces;

import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;

public interface IManager {


	
	JTable meelTable = new JTable();
	JTable accountTable = new JTable();
	JTable bazarTable = new JTable();
	
	JLabel totalMeel = new JLabel("Total Meel: ");
	JLabel totalSpend = new JLabel("Total Spend: ");
	JLabel meelRate = new JLabel("Meel Rate: ");
	JLabel remainAmmount = new JLabel("Reamin: ");
	
	JButton meelUpdateB = new JButton("Update");
	JButton meelAddB = new JButton("Add");
	JButton meelDelB = new JButton("Delete");
	JButton refreshB = new JButton("Refresh");
	JButton acUpdateB = new JButton("Update");
	JButton bazarAddB = new JButton("Add");
	JButton bazarEditB = new JButton("Edit");
	JButton bazarDelB = new JButton("Delete");
	JButton logoutB = new JButton("LogOut");
	
	JComboBox<String> yearBox = new JComboBox<String>();
	JComboBox<String> monthBox = new JComboBox<String>();
	
	final static Dimension DIEMENTION = new Dimension(1100,650);
	
}
