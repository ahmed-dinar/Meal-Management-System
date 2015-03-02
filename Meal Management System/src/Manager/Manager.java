package Manager;

import java.awt.Color;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import UI.ManagerLoginUI;
import DB.Configuration;
import DB.ConfigureDB;



public class Manager{

	private static String[] op = {"Configure","Cancel"};
	
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.getDefaults().put("TabbedPane.contentBorderInsets", new Insets(4,10,1,10));
			UIManager.getDefaults().put("TabbedPane.tabsOverlapBorder", true);
			
			UIManager.put("TabbedPane.borderHightlightColor", Color.WHITE); 
			UIManager.put("TabbedPane.darkShadow", java.awt.Color.WHITE); 
			UIManager.put("TabbedPane.light", java.awt.Color.WHITE);
			UIManager.put("TabbedPane.selectHighlight", java.awt.Color.WHITE);
			UIManager.put("TabbedPane.darkShadow", java.awt.Color.WHITE);
			UIManager.put("TabbedPane.focus", java.awt.Color.WHITE);
			UIManager.put("OptionPane.background", Color.white);
			UIManager.put("Panel.background", Color.white);
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
		//	e.printStackTrace();
		}
	
		
		File xmlF = new File("manager.xml");
		if(!xmlF.exists()){
			
			String[] opt = {"Ok","Cancel"};
			
			int choise = JOptionPane.showOptionDialog(null, xml_error_msg(), "xml missing", JOptionPane.NO_OPTION, 0, null, opt, opt[0]);
			if(choise == 0){
				Configuration();
			}
			else {
				System.exit(0);
			}
			
		}
		else {
			readXml(xmlF);
		}
	}
	
	private  static String xml_error_msg(){
		return "'manager.xml'  not found in the directory '".concat(getCurrentDir()).concat("'\n")
		.concat("Do you want to create xml file and config?\n");
	}
	
	private static void readXml(File xmlF) {
		
		DocumentBuilder parser;
		try {
			parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			org.w3c.dom.Document doc = parser.parse(xmlF);
		    NodeList nodelist = doc.getElementsByTagName("Manager");
		    String path = null;
		    for(int i=0; i<nodelist.getLength(); i++){
		    	org.w3c.dom.Node node = nodelist.item(i);
		    	if(node.getNodeType() == Node.ELEMENT_NODE){
		    		Element element = (Element) node;
		    		path = element.getElementsByTagName("setups").item(0).getAttributes().item(2).getTextContent();
		    	}
		    }
		    if(path.equals("")){
				String msg = "Its seem like you log in first time,please config\nWe will set things for you!".concat("'\n");
				int choise = JOptionPane.showOptionDialog(null, msg, "Configuration", JOptionPane.NO_OPTION, 0, null, op, op[1]);
				if(choise == 0){
					Configuration();
				}
				else {
					System.exit(0);
				}
		    }
		    else {
		    	if(isDb(path)){
		    		new ManagerLoginUI(path);
		    	}
		    	else {
		    		int chos= JOptionPane.showOptionDialog(null, dbmissingMsg(path), "Database Missing", JOptionPane.NO_OPTION, 0, null, op, op[1]);
		    		if(chos==0){
		    			new ConfigureDB();
		    		}
		    		else {
						System.exit(0);
					}
				}
			}
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String dbmissingMsg(String path){
		return "Databse file not found in directory: '".concat(path).concat("'\nPlease Check ur configuration\n");
	}

	private static boolean isDb(String path) {
		boolean is = false;
		File file = new File(path+"/mealManagement.db");
		if(file.exists()){
			is = true;
		}
		return is;
	}

	private static void Configuration() {
		new Configuration().ConfigAll();
	}

	private static  String getCurrentDir() {
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

}
