package DB;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import file.setupXML;

public class ConfigureDB implements ActionListener,ItemListener {
	
	private JButton browseB;
	private JButton dirbrowseB;
	private JTextField path;
	private JTextField dirpath;
	private JRadioButton dirB;
	private JRadioButton conB;
	private JTextField name;
	private JTextField pass;
	private boolean isDir=false;
	private boolean isCon=false;
	private final Border borderBlue = BorderFactory.createLineBorder(new Color(51,53,255));
	private final Border borderGray = BorderFactory.createLineBorder(Color.GRAY);
	private JLabel pathdirl;
	private JLabel namel ;
	private JLabel pathl ;
	private JLabel passl ;
	private String manName;
	private String dbpath;
	private String Password;

	public ConfigureDB() {
	
		JPanel container = new JPanel(new BorderLayout(5,5));
		container.setBorder(BorderFactory.createEmptyBorder(20, 20,10,20));
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel dirPanel = new JPanel(new BorderLayout());
		JPanel conPanel = new JPanel(new BorderLayout());
		
		ButtonGroup group = new ButtonGroup();
		dirB = new JRadioButton("Change Databse Directory");
		conB = new JRadioButton("Config New Databse");
		
		Font font =  new Font("Courier New",Font.PLAIN,19);
		dirB.setFont(font);
		conB.setFont(font);
		
		dirB.setOpaque(false);
		conB.setOpaque(false);
		
		dirB.addActionListener(this);
		dirB.addItemListener(this);
		
		conB.addActionListener(this);
		conB.addItemListener(this);
		
		dirPanel.add(dirB,BorderLayout.NORTH);
		dirPanel.add(dirP(),BorderLayout.CENTER);
		
		conPanel.add(conB,BorderLayout.NORTH);
		conPanel.add(conP(),BorderLayout.CENTER);
		
		group.add(dirB);
		group.add(conB);
		
		group.setSelected(dirB.getModel(), true);
		
		panel.add(dirPanel,BorderLayout.NORTH);
		panel.add(conPanel,BorderLayout.CENTER);
		
		container.add(panel);
		disable();
		
        String op[] = {"Save","Cancel"};
        
        while(JOptionPane.showOptionDialog(null, container, "Configure", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, op, op[0])==0){
        	if(isDir){
        		if(checkDir()){  
        			changeDirectory();
        			break;
        		}
        		else{
					JOptionPane.showMessageDialog(null, "Databse file \"mealManagement.db\" not found in \"" 
							+ dirpath.getText() + "\"\n"
							+ "Please copy \"mealManagement.db\" file to \"" + dirpath.getText() + "\"  from current location and try again\n"
							+ "Or Config a new database"
							);
				}
        	}
        	else if(isCon){
        		if(checkCon()){  
    				manName = name.getText();
    				dbpath = formatPath(path.getText());
    				Password = pass.getText();
    				new setupXML(manName,dbpath);
    				new createTables(dbpath,Password);
    				JOptionPane.showMessageDialog(null,"Databse successfully updated\nPlease restart application to get Effect.");
        			break;
        		}
        		else {
					JOptionPane.showMessageDialog(null, "Fill all!");
				}
        	}
        	else {
				break;
			}
        }
	}

	
	private void changeDirectory() {
		try {
			new setupXML(readXml(new File( "manager.xml")),formatPath(dirpath.getText()));
			JOptionPane.showMessageDialog(null,"Databse path successfully changed to '"+dirpath.getText()+"'\nPlease restart application to get Effect.");
		} catch (ParserConfigurationException | SAXException | IOException e) {
			JOptionPane.showMessageDialog(null,"Unknown error occur to change");
		}
	}

	private static String readXml(File xmlF) throws ParserConfigurationException, SAXException, IOException {
		String cman = "";
		DocumentBuilder parser;

		parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		org.w3c.dom.Document doc = parser.parse(xmlF);
		NodeList nodelist = doc.getElementsByTagName("Manager");
		 for(int i=0; i<nodelist.getLength(); i++){
		   org.w3c.dom.Node node = nodelist.item(i);
		   if(node.getNodeType() == Node.ELEMENT_NODE){
		    Element element = (Element) node;
		    cman = element.getElementsByTagName("setups").item(0).getAttributes().item(0).getTextContent();
		  }
		}
		return cman;
	}
	
	private String formatPath(String path) {
		String temp = "";
		for (int i = 0; i < path.length(); i++) {
			
			if(i==0 && path.charAt(i) == '\\' || i==0 && path.charAt(i) == '/'){
				continue;
			}
			if(path.charAt(i) == '\\'){
				temp += '/';
			}
			else {
				temp += path.charAt(i);
			}
		}
		return temp;
	}

	private boolean checkCon() {
		if(!checkEntry(name.getText())  || !checkEntry(pass.getText().toString()) || path.getText().equals("") ){
			return false;
		}
		return true;
	}
	
	private boolean checkEntry(String s) {
		for(int i=0; i<s.length(); i++){
			if(s.charAt(i) != 8 && s.charAt(i) != 9  && s.charAt(i) != 10  && s.charAt(i)!= 11  && s.charAt(i) != 32){
				return true;
			}
		}
		return false;
	}

	private boolean checkDir() {
		
		String ss  = dirpath.getText();
		
		if(ss.equals("")){
			return false;
		}
		else {
			ss = ss+"\\mealManagement.db";
			if(!isDb(ss)){
				return false;
			}
		}

		return true;
	}
	
	private static boolean isDb(String path) {
		boolean is = false;
		File file = new File(path);
		if(file.exists()){
			is = true;
		}
		return is;
	}

	private Container dirP(){
		
		JPanel container = new JPanel(new BorderLayout(5,5));
		container.setBorder(BorderFactory.createEmptyBorder(5, 5,10,20));

		JPanel pathC = new JPanel(new BorderLayout(5,0));
		
		dirpath = new JTextField();
		dirbrowseB = new JButton("Browse..");
		dirbrowseB.addActionListener(this);
		
		pathdirl = new JLabel("Database Directory");
		pathdirl.setPreferredSize(new Dimension(140,30));
		
	    Font font = new Font("",Font.PLAIN,14);
	    pathdirl.setFont(font);
	    dirpath.setFont(font);
	    dirpath.setEditable(false);
	    dirpath.setPreferredSize(new Dimension(320,25));
	    
	    pathC.add(pathdirl,BorderLayout.WEST);
        pathC.add(dirpath,BorderLayout.CENTER);
        pathC.add(dirbrowseB,BorderLayout.EAST);

        container.add(pathC,BorderLayout.CENTER);
        return container;
	}
	
	private Container conP(){
		JPanel container = new JPanel(new BorderLayout(5,5));
		container.setBorder(BorderFactory.createEmptyBorder(0, 5,10,20));
	
		JPanel gui = new JPanel(new BorderLayout());
        gui.setPreferredSize(new Dimension(535,125));
        gui.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        
        Container labels = new JPanel(new GridLayout(3,1,10,10));
        Container fileds = new JPanel(new GridLayout(3,1,10,10));
        JPanel pathC = new JPanel(new BorderLayout());
        
        name = new JTextField();
        path = new JTextField();
        pass = new JTextField();
        
        name.setPreferredSize(new Dimension(400,25));
        path.setPreferredSize(new Dimension(320,25));
        pass.setPreferredSize(new Dimension(400,25));
        pathC.setPreferredSize(new Dimension(400,25));
        
        namel = new JLabel("Manager Name");
        pathl = new JLabel("Database Directory");
        passl = new JLabel("Password");
        
        Font font = new Font("",Font.PLAIN,14);
        namel.setFont(font);
        pathl.setFont(font);
        passl.setFont(font);
        name.setFont(font);
        path.setFont(font);
        pass.setFont(font);
        path.setEditable(false);
       
        namel.setPreferredSize(new Dimension(140,30));
        pathl.setPreferredSize(new Dimension(140,30));
        passl.setPreferredSize(new Dimension(140,30));
        
        browseB = new JButton("Browse..");
        browseB.addActionListener(this);
        
        pathC.add(path,BorderLayout.WEST);
        pathC.add(browseB,BorderLayout.EAST);
        
        labels.add(namel);
        labels.add(passl);
        labels.add(pathl);
        
        fileds.add(name);
        fileds.add(pass);
        fileds.add(pathC);
        
        gui.add(labels,BorderLayout.WEST);
        gui.add(fileds,BorderLayout.EAST);
        
        container.add(gui,BorderLayout.CENTER);
		return container;
	}
	
	private void disable(){
		dirbrowseB.setEnabled(true);
		
		browseB.setEnabled(false);
		path.setEnabled(false);
		pass.setEnabled(false);
		name.setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if(object == browseB || object == dirbrowseB){
			JFileChooser chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			chooser.setDialogTitle("Chosse directory");
			chooser.setAcceptAllFileFilterUsed(false);
			if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
				if(object == browseB){
					 SetPath(path,chooser.getSelectedFile().getPath());
				}
				else if(object == dirbrowseB){
					 SetPath(dirpath,chooser.getSelectedFile().getPath());
				}
			}
		}
	}
	
	
	private void SetPath(JTextField field,String path2) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				field.setText(path2);
			}
		});
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
        int state = e.getStateChange();
        if (state == ItemEvent.SELECTED) {
        	if(e.getSource() == dirB){
        		
        		dirB.setForeground(Color.BLACK);
        		pathdirl.setForeground(Color.BLACK);
        		
        		conB.setForeground(Color.GRAY);
        		namel.setForeground(Color.GRAY);
        		pathl.setForeground(Color.GRAY);
        		passl.setForeground(Color.GRAY);
        		
        		isDir = true;
        		isCon = false;
        		
        		dirbrowseB.setEnabled(true);
        		dirpath.setEnabled(true);
        		dirpath.setBorder(borderBlue);
        		dirbrowseB.setBackground(new Color(51,53,255));
        		
        		
        		browseB.setEnabled(false);
        		path.setEnabled(false);
        		pass.setEnabled(false);
        		name.setEnabled(false);
        		
        		path.setBorder(borderGray);
        		pass.setBorder(borderGray);
        		name.setBorder(borderGray);
        		
        		path.setText("");
        		pass.setText("");
        		name.setText("");
        		
        		browseB.setBackground(Color.WHITE);
        	}
        	else if(e.getSource() == conB){
        		
        		dirB.setForeground(Color.GRAY);
        		pathdirl.setForeground(Color.GRAY);
        		
        		conB.setForeground(Color.BLACK);
        		namel.setForeground(Color.BLACK);
        		pathl.setForeground(Color.BLACK);
        		passl.setForeground(Color.BLACK);
        		
        		isDir = false;
        		isCon = true;
        		
        		dirbrowseB.setEnabled(false);
        		dirpath.setText("");
        		dirpath.setEnabled(false);
        		dirpath.setBorder(borderGray);
        		dirbrowseB.setBackground(Color.WHITE);
        		
        		browseB.setEnabled(true);
        		path.setEnabled(true);
        		pass.setEnabled(true);
        		name.setEnabled(true);
        		
        		path.setBorder(borderBlue);
        		pass.setBorder(borderBlue);
        		name.setBorder(borderBlue);
        		
        		browseB.setBackground(new Color(51,53,255));
        	}
        } 
	}
}
