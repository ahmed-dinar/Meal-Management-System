package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Custom.BackgroundMenuBar;
import DB.ConfigureDB;
import DB.getYearList;
import Dialogs.AboutMM;
import Dialogs.SaveAsDialog;
import Dialogs.changepassDialog;
import Dialogs.openWeb;
import Interfaces.IManager;
import Interfaces.IMenu;
import Listeners.MenuListener;
import Renderers.headerRenderer;
import Renderers.tableRenderer;
import TModel.accountTableModel;
import TModel.bazarTableModel;
import TModel.meelTableModel;
import TModel.statusTableModel;

public class ManagerUI extends JFrame implements IManager,IMenu,ActionListener,ItemListener,KeyListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTabbedPane mainTab;
	private String path;
	private  String[] monthsName = { "january", "february", "march", "april", "may", "june", "july",
	        "august", "september", "october", "november", "december" };
	private meelTableModel meelModel;
	private bazarTableModel bazarModel;
	private accountTableModel accountModel;
	private statusTableModel statusModel;
	private String selectedYear;
	private String selectedMonth;
	private Color bg = new Color(92,151,206);
	private Color white = Color.WHITE;
	private JTextField onlyNumberCell;
	private Color TgridColor = new Color(240,240,240);
	
	public ManagerUI(String path) {
		
		super("Meal Records");
		
		this.path = path+"mealManagement.db";
		
		Initframe();
		addListener();
		tableDesign();
		new MenuListener();
		
		setPreferredSize(DIEMENTION);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getPreferredSize().width/2, dim.height/2-this.getPreferredSize().height/2);
	}

	private void addListener() {
		
		onlyNumberCell.addKeyListener(this);
		
		databaseSettingsItem.addActionListener(this);
		saveasItem.addActionListener(this);
		changePassItem.addActionListener(this);
		helpItem.addActionListener(this);
		
		meelAddB.addActionListener(this);
		meelDelB.addActionListener(this);
		meelUpdateB.addActionListener(this);
		acUpdateB.addActionListener(this);
		bazarAddB.addActionListener(this);
		bazarDelB.addActionListener(this);
		bazarEditB.addActionListener(this);
		refreshB.addActionListener(this);
		yearBox.addItemListener(this);
		monthBox.addItemListener(this);
		
		meelAddB.setFocusPainted(false);
		meelDelB.setFocusPainted(false);
		meelUpdateB.setFocusPainted(false);
		acUpdateB.setFocusPainted(false);
		bazarAddB.setFocusPainted(false);
		bazarDelB.setFocusPainted(false);
		bazarEditB.setFocusPainted(false);
		refreshB.setFocusPainted(false);
		
		totalMeel.setForeground(white);
		totalSpend.setForeground(white);
		meelRate.setForeground(white);
		remainAmmount.setForeground(white);
		
		Insets in = new Insets(0, 20, 0, 20);
		totalMeel.setBorder(new EmptyBorder(in));
		totalSpend.setBorder(new EmptyBorder(in));
		meelRate.setBorder(new EmptyBorder(in));
		remainAmmount.setBorder(new EmptyBorder(in));
		
		meelTable.setGridColor(TgridColor);
		accountTable.setGridColor(TgridColor);
		bazarTable.setGridColor(TgridColor);
		
		try {
			meelAddB.setIcon(new ImageIcon(loadImg("add.png")));
			bazarAddB.setIcon(new ImageIcon(loadImg("addbazar.png")));
			meelDelB.setIcon(new ImageIcon(loadImg("del.png")));
			meelUpdateB.setIcon(new ImageIcon(loadImg("up.png")));
			acUpdateB.setIcon(new ImageIcon(loadImg("up.png")));
			bazarDelB.setIcon(new ImageIcon(loadImg("del.png")));
			bazarEditB.setIcon(new ImageIcon(loadImg("up.png")));
			refreshB.setIcon(new ImageIcon(loadImg("ref.png")));
		} catch (IllegalArgumentException e) {}
	}
	
	public Image loadImg(String icon) {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/resources/"+icon));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An Error occured!");
		}
		return img;
	}

	private void Initframe() {
		
		onlyNumberCell = new JTextField();

		selectedYear = getCurrentYear();
		selectedMonth = getCurrentMonth();
		
		initMenu();
		
		initDrop();

		mainTab = new JTabbedPane();
		mainTab.setBackground(bg);
		mainTab.setOpaque(true);
		
		initMeelTab();
		initAcTab();
		initBazarTab();
		
		add(mainTab,BorderLayout.CENTER);
		initBottom();
	}

	private void initBottom() {
		JPanel bottomPanel = new JPanel(new BorderLayout());
		bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(220,220,220)));
		bottomPanel.setPreferredSize(new Dimension(getWidth(),20));
		bottomPanel.setBackground(Color.WHITE);
		
		JLabel currentPath = new JLabel("Current db path: " + path);
		currentPath.setBorder(new EmptyBorder(new Insets(0, 5, 0, 0)));
		
		JLabel version = new JLabel("Version : v1.2.0");
		version.setBorder(new EmptyBorder(new Insets(0, 0, 0, 5)));
		
		bottomPanel.add(currentPath, BorderLayout.WEST);
		bottomPanel.add(version,BorderLayout.EAST);
		
		add(bottomPanel,BorderLayout.SOUTH);
	}

	private void initMeelTab() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel butPanel = new JPanel();
		
		meelModel = new meelTableModel(getCurrentYear(),getCurrentMonth(),path);
		meelTable.setModel(meelModel.getModel());
		JScrollPane pane = new JScrollPane(meelTable);
		pane.setBorder(BorderFactory.createEmptyBorder());
		
		butPanel.setBackground(white);
		butPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		butPanel.add(meelAddB);
		butPanel.add(meelDelB);
		butPanel.add(meelUpdateB);
		butPanel.add(refreshB);
		
		panel.add(pane,BorderLayout.CENTER);
		panel.add(butPanel,BorderLayout.SOUTH);
		
		mainTab.addTab(tabHeader("Meel"), panel);
	}

	private void initAcTab(){
		JPanel panel = new JPanel(new BorderLayout());
		JPanel butPanel = new JPanel();
		
		accountModel = new accountTableModel(getCurrentYear(), getCurrentMonth(), meelModel, statusModel,path);
		accountTable.setModel(accountModel.getModel());
		JScrollPane pane = new JScrollPane(accountTable);
		pane.setBorder(BorderFactory.createEmptyBorder());
		
		butPanel.add(acUpdateB);
		
		butPanel.setBackground(white);
		butPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		panel.add(pane,BorderLayout.CENTER);
		panel.add(butPanel,BorderLayout.SOUTH);
		
		mainTab.addTab(tabHeader("Account"), panel);
	}

	private void initBazarTab() {
		JPanel panel = new JPanel(new BorderLayout());
		JPanel butPanel = new JPanel();
		
		bazarModel = new bazarTableModel(getCurrentYear(),getCurrentMonth(),path);
		bazarTable.setModel(bazarModel.getModel());
		JScrollPane pane = new JScrollPane(bazarTable);
		pane.setBorder(BorderFactory.createEmptyBorder());
		
		butPanel.setBackground(white);
		butPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
		
		butPanel.add(bazarAddB);
		butPanel.add(bazarDelB);
		butPanel.add(bazarEditB);
		
		panel.add(pane,BorderLayout.CENTER);
		panel.add(butPanel,BorderLayout.SOUTH);
		
		mainTab.addTab(tabHeader("Bazar"), panel);
	}
	
	private String tabHeader(String h) {
		return "<html><body leftmargin=10 topmargin=4 marginheight=2 ><font size=3>".concat(h).concat("</font></body></html>");
	}

	private void initMenu() {
		JMenuBar menuBar = new BackgroundMenuBar();
		menuBar.setBorder(BorderFactory.createEmptyBorder());
		
		JMenu filemenu = new JMenu("File");
		JMenu settngsMenu = new JMenu("Settings");
		JMenu helpmenu = new JMenu("Help");
		
		filemenu.add(saveasItem);
		filemenu.add(exitItem);
		
		settngsMenu.add(databaseSettingsItem);
		settngsMenu.add(changePassItem);
		
		helpmenu.add(helpItem);
		helpmenu.add(aboutItem);
		
		menuBar.add(filemenu);
		menuBar.add(settngsMenu);
		menuBar.add(helpmenu);
		
		setJMenuBar(menuBar);
		
	}

	private void initDrop() {
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JPanel listPanel = new JPanel();
		JPanel statuspanel = new JPanel();
		
		JLabel sy = new JLabel("Select Year:");
		JLabel sm = new JLabel("Select Month:");
		
		sy.setFont(new Font("Maiandra GD",Font.BOLD,18));
		sy.setForeground(white);
		sm.setFont(new Font("Maiandra GD",Font.BOLD,18));
		sm.setForeground(white);
		
		setYears();
		setMonths();
		
		yearBox.setPreferredSize(new Dimension(80,27));
		yearBox.setFocusable(false);
		yearBox.setFont(new Font("Maiandra GD",Font.PLAIN,16));
		monthBox.setPreferredSize(new Dimension(110,27));
		monthBox.setFocusable(false);
		monthBox.setFont(new Font("Maiandra GD",Font.PLAIN,16));
		
		setLabelfont(totalMeel);
		setLabelfont(totalSpend);
		setLabelfont(meelRate);
		setLabelfont(remainAmmount);
		
		listPanel.add(sy);
		listPanel.add(yearBox);
		listPanel.add(getSep());
		listPanel.add(sm);
		listPanel.add(monthBox);
		
		updateStatus();
		
		statuspanel.add(totalMeel);
		statuspanel.add(getSep());
		statuspanel.add(totalSpend);
		statuspanel.add(getSep());
		statuspanel.add(meelRate);
		statuspanel.add(getSep());
		statuspanel.add(remainAmmount);
	
		listPanel.setOpaque(false);
		statuspanel.setOpaque(false);
		panel.setBackground(bg);
		
		panel.add(listPanel,BorderLayout.NORTH);
		panel.add(statuspanel,BorderLayout.CENTER);
		
		add(panel,BorderLayout.NORTH);
	}
	
	private void setYears() {
		Vector<String> yS = new getYearList(path).getYears();
		for(String s: yS){
			yearBox.addItem(s);
		}
		yearBox.setSelectedItem(getCurrentYear());
	}

	private void setMonths() {
		for(String s: monthsName){
			monthBox.addItem(s);
		}
		monthBox.setSelectedItem(getCurrentMonth());
	}

	private JSeparator getSep() {
		JSeparator separator = new JSeparator(JSeparator.VERTICAL);
		separator.setPreferredSize(new Dimension(1,20));
		separator.setForeground(new Color(99,180,251));
		return separator;
	}

	
	public void setLabelfont(JLabel label){
		label.setFont(new Font("Maiandra GD",Font.PLAIN,20));
		label.setForeground(Color.BLACK);
	}
	
	public String getCurrentYear(){
		Calendar now = Calendar.getInstance();
		return Integer.toString(now.get(Calendar.YEAR));
	}
	
	public String getCurrentMonth(){
		Calendar now = Calendar.getInstance();
		return monthsName[now.get(Calendar.MONTH)];
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getStateChange() == ItemEvent.SELECTED) {
			
			if(e.getSource() == yearBox ){
				selectedYear = e.getItem().toString();
			}
			else{
				selectedMonth = e.getItem().toString();
			}

			meelModel = new meelTableModel(selectedYear,selectedMonth,path);
			meelTable.setModel(meelModel.getModel());
				
			accountModel = new accountTableModel(selectedYear,selectedMonth,meelModel,statusModel,path);
			accountTable.setModel(accountModel.getModel());
				
			bazarModel = new bazarTableModel(selectedYear,selectedMonth,path);
			bazarTable.setModel(bazarModel.getModel());
			
			updateStatus();

			if(selectedMonth.compareTo(getCurrentMonth()) == 0 && selectedYear.compareTo(getCurrentYear()) == 0 ){
			    	meelAddB.setEnabled(true);
			    	meelDelB.setEnabled(true);
			    	meelUpdateB.setEnabled(true);
					acUpdateB.setEnabled(true);
					bazarAddB.setEnabled(true);
					bazarDelB.setEnabled(true);
			    	bazarTable.setEnabled(true);
			    	bazarEditB.setEnabled(true);
			    	meelTable.setEnabled(true);
			    	accountTable.setEnabled(true);
			}
			else{
				boolean bol = false;
		    	meelAddB.setEnabled(bol);
		    	meelDelB.setEnabled(bol);
		    	meelUpdateB.setEnabled(bol);
				acUpdateB.setEnabled(bol);
				bazarAddB.setEnabled(bol);
				bazarDelB.setEnabled(bol);
		    	bazarTable.setEnabled(bol);
		    	bazarEditB.setEnabled(bol);
		    	meelTable.setEnabled(bol);
		    	accountTable.setEnabled(bol);
			 }
			 tableDesign();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object == meelUpdateB){
			
			if(meelTable.isEditing()){
				meelTable.getCellEditor().stopCellEditing();
			}
			if(accountTable.isEditing()){
				accountTable.getCellEditor().stopCellEditing();
			}
			if(bazarTable.isEditing()){
				bazarTable.getCellEditor().stopCellEditing();
			}
			
			if( meelModel.IsEdited()){

				meelModel.updateModel(selectedYear,selectedMonth,path);
				updateStatus();

				JOptionPane.showMessageDialog(null,"Updated");/*     Check Error  */

			}
			meelModel = new meelTableModel(selectedYear,selectedMonth,path);
			meelTable.setModel(meelModel.getModel());
			
			accountModel = new accountTableModel(selectedYear,selectedMonth,meelModel,statusModel,path);
			accountTable.setModel(accountModel.getModel());
			tableDesign();
		}
		else if(object == acUpdateB 	){
			if(accountTable.isEditing()){
				accountTable.getCellEditor().stopCellEditing();
			}
			
			if(accountModel.IsEdited()){

				accountModel.updateModel(selectedYear,selectedMonth,path);
				updateStatus();
				JOptionPane.showMessageDialog(null,"Updated");

			}
			accountModel = new accountTableModel(selectedYear,selectedMonth,meelModel,statusModel,path);
			accountTable.setModel(accountModel.getModel());
			tableDesign();
		}
		else if(object == meelAddB 	){
				
			String newMemberName = JOptionPane.showInputDialog(null, "Enter name :  ", "Add New Member",JOptionPane.PLAIN_MESSAGE);
			if(newMemberName != null){
				meelModel.addMember(selectedYear,selectedMonth, newMemberName,path);
				meelModel = new meelTableModel(selectedYear,selectedMonth,path);
				meelTable.setModel(meelModel.getModel());
				
				accountModel = new accountTableModel(selectedYear,selectedMonth,meelModel,statusModel,path);
				accountTable.setModel(accountModel.getModel());

				tableDesign();
			}

		}
		else if(object == meelDelB	){
			
				if(meelTable.getSelectedRow() >= 0){
					String ConfirmMessage = "Do you really want to delete '".concat(meelTable.getValueAt(meelTable.getSelectedRow(), 0).toString()).concat("'?");
					int confirm = JOptionPane.showConfirmDialog(null,ConfirmMessage,"Delete a member",JOptionPane.YES_NO_OPTION);
					if(confirm == 0){
						meelModel.deleteMember(selectedYear,selectedMonth, meelTable.getValueAt(meelTable.getSelectedRow(), 0).toString(),path);
						meelModel = new meelTableModel(selectedYear,selectedMonth,path);
						meelTable.setModel(meelModel.getModel());

						updateStatus();
						
						accountModel = new accountTableModel(selectedYear,selectedMonth,meelModel,statusModel,path);
						accountTable.setModel(accountModel.getModel());
						
						tableDesign();		
					}
				}

		}
		else if(object == bazarAddB	){

				addBazarFrame bazarObj = new addBazarFrame();
				int flag = bazarObj.bazarDialog();
				
				if(flag == 0){
					if(bazarObj.getName().compareTo("") == 0){
						JOptionPane.showMessageDialog(null, "Empty Name!");
					}
					else if(bazarObj.getAmount().compareTo("") == 0){
						JOptionPane.showMessageDialog(null, "Empty Cost!");
					}
					else{

						bazarModel.addBazarInModel(selectedYear, selectedMonth, bazarObj.getDates(), bazarObj.getName(), bazarObj.getDesc(), bazarObj.getAmount(),path);
						bazarModel = new bazarTableModel(selectedYear, selectedMonth,path);
						bazarTable.setModel(bazarModel.getModel());
						
						updateStatus();
					}
				}
				tableDesign();
		}
		else if(object == bazarEditB	){
			
			if(bazarTable.getSelectedRow()>=0){
				editBazarFrame bazarObj = new editBazarFrame();
				bazarObj.getDescArea().setText(bazarModel.getModel().getValueAt(bazarTable.getSelectedRow(), 3).toString());
				bazarObj.getNameField().setText(bazarModel.getModel().getValueAt(bazarTable.getSelectedRow(), 2).toString());
				bazarObj.getCostField().setText(bazarModel.getModel().getValueAt(bazarTable.getSelectedRow(), 4).toString());
				int flag = bazarObj.bazarDialog();
				if(flag == 0){
					if(bazarObj.getName().compareTo("") == 0){
						JOptionPane.showMessageDialog(null, "Empty Name!");
					}
					else if(bazarObj.getAmount().compareTo("") == 0){
						JOptionPane.showMessageDialog(null, "Empty Cost!");
					}
					else{
						bazarModel.editBazar(selectedYear, selectedMonth, bazarModel.getModel().getValueAt(bazarTable.getSelectedRow(), 0).toString(), bazarObj.getNameField().getText(), bazarObj.getCostField().getText(), bazarObj.getDescArea().getText(),path);
						bazarModel = new bazarTableModel(selectedYear, selectedMonth,path);
						bazarTable.setModel(bazarModel.getModel());
						
						updateStatus();
						tableDesign();
					}
				}
			}
		}
		else if(object == bazarDelB	){

			if(bazarTable.getSelectedRow() >= 0){
				
				String ConfirmMessage = "Do you really want to delete '".concat(bazarTable.getValueAt(bazarTable.getSelectedRow(), 0).toString()).concat("'?");
				int confirm = JOptionPane.showConfirmDialog(null,ConfirmMessage,"Delete a member",JOptionPane.YES_NO_OPTION);
				if(confirm == 0){
					bazarModel.deleteBazar(selectedYear, selectedMonth, bazarTable.getValueAt(bazarTable.getSelectedRow(), 0).toString(),path);
					bazarModel = new bazarTableModel(selectedYear,selectedMonth,path);
					bazarTable.setModel(bazarModel.getModel());
							
					updateStatus();
					tableDesign();		
				}
			}

		}
		else if(object == refreshB 	){
			meelModel = new meelTableModel(selectedYear,selectedMonth,path);
			meelTable.setModel(meelModel.getModel());
	
			updateStatus();
			
			accountModel = new accountTableModel(selectedYear,selectedMonth,meelModel,statusModel,path);
			accountTable.setModel(accountModel.getModel());
			
			bazarModel = new bazarTableModel(selectedYear,selectedMonth,path);
			bazarTable.setModel(bazarModel.getModel());
			
			if (meelTable.isEditing()){
				meelTable.getCellEditor().stopCellEditing();
			}
			if (accountTable.isEditing()){
				accountTable.getCellEditor().stopCellEditing();
			}
			if (bazarTable.isEditing()){
				bazarTable.getCellEditor().stopCellEditing();
			}
			
			monthBox.setSelectedItem(getCurrentMonth());
			yearBox.setSelectedItem(getCurrentYear());
			tableDesign();
		}
		else if(object == exitItem) {
			System.exit(0);
		}
		else if(object == aboutItem) {
			new AboutMM();
		}
		else if(object == databaseSettingsItem) {
			new ConfigureDB();
		}
		else if(object == saveasItem){
			new SaveAsDialog();
		}
		else if(object == changePassItem){
			new changepassDialog(path);
		}
		else if(object == helpItem){
			new openWeb("https://github.com/ahmed-dinar");
		}
		
	}


	public void tableDesign() {
		int COL;
		if(selectedMonth.compareTo("april") == 0 || selectedMonth.compareTo("june") == 0 || selectedMonth.compareTo("september") == 0 || selectedMonth.compareTo("november") == 0){
			COL = 31;
		}
		else if(selectedMonth.compareTo("february") == 0){
			COL = 29;
		}
		else{
			COL = 32;
		}
		
		for(int i=1; i<COL; i++){
			meelTable.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(onlyNumberCell));
		}
		
		accountTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(onlyNumberCell));
		
		bazarTable.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(onlyNumberCell));
		
		meelTable.getTableHeader().getColumnModel().getColumn(0).setPreferredWidth(250);
		meelTable.getTableHeader().getColumnModel().getColumn(COL).setPreferredWidth(250);
		
		bazarTable.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
		bazarTable.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
		
		bazarTable.getTableHeader().getColumnModel().getColumn(1).setPreferredWidth(30);
		bazarTable.getTableHeader().getColumnModel().getColumn(2).setPreferredWidth(10);
		bazarTable.getTableHeader().getColumnModel().getColumn(3).setPreferredWidth(210);
		bazarTable.getTableHeader().getColumnModel().getColumn(4).setPreferredWidth(10);
		
		meelTable.setRowHeight(23);
		accountTable.setRowHeight(23);
		bazarTable.setRowHeight(23);
		
		meelTable.getTableHeader().setReorderingAllowed(false);
		accountTable.getTableHeader().setReorderingAllowed(false);
		
		meelTable.getTableHeader().setDefaultRenderer(new headerRenderer());
		bazarTable.getTableHeader().setDefaultRenderer(new headerRenderer());
		accountTable.getTableHeader().setDefaultRenderer(new headerRenderer());
		
		
		bazarTable.setDefaultRenderer(Object.class, new tableRenderer());
		accountTable.setDefaultRenderer(Object.class, new tableRenderer());
		meelTable.setDefaultRenderer(Object.class, new tableRenderer());
	}

	public void updateStatus(){
		statusModel = new statusTableModel(selectedYear,selectedMonth,path);
		totalMeel.setText("Total Meal: " + statusModel.getTotalMeel());
		totalSpend.setText("Total Spend: " + statusModel.getTotalSpend()+ " TK");
		meelRate.setText("Meal Rate: " + statusModel.getMeelRate() + " TK");
		remainAmmount.setText("Remain Ammount: "  + statusModel.getRemainAmount() + " TK.");
	}
	
	public  String getCurrentDir() {
		String dir = null;
		try {
			dir = URLDecoder.decode(ClassLoader.getSystemClassLoader().getResource(".").getPath() , "UTF-8");
			String temp = "";
			for (int i = 0; i < dir.length(); i++) {
				
				if(i==0 && dir.charAt(i) == '\\' || i==0 && dir.charAt(i) == '/'){
					continue;
				}
				if(dir.charAt(i) == '/'){
					temp += '\\';
				}
				else {
					temp += dir.charAt(i);
				}
			}
			dir = temp;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return dir;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		if(e.getSource() == onlyNumberCell){
			if(!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != KeyEvent.VK_BACK_SPACE &&  e.getKeyChar() != '.' )
			{
				onlyNumberCell.setEditable(false);
				onlyNumberCell.setBackground(Color.WHITE);
				onlyNumberCell.setCursor(new Cursor(Cursor.TEXT_CURSOR));
			}
			else
			{
				onlyNumberCell.setEditable(true);
			}
		}
		
	}
	
}
