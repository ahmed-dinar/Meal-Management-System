package DB;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import UI.ManagerLoginUI;
import file.setupXML;

public class Configuration implements ActionListener{

	private JButton browseB;
	private JTextField name;
	private JTextField path;
	private JTextField pass;
	private String manName;
	private String dbpath;
	private String Password;
	
	public Configuration() {
		
	}
	
	public void ConfigAll() {
		
		JPanel container = new JPanel(new BorderLayout(5,5));
		container.setBorder(BorderFactory.createEmptyBorder(20, 20,10,20));
	
		JPanel gui = new JPanel(new BorderLayout());
        gui.setPreferredSize(new Dimension(535,150));
        gui.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        
        Container labels = new JPanel(new GridLayout(3,1,10,10));
        Container fileds = new JPanel(new GridLayout(3,1,10,10));
        JPanel pathC = new JPanel(new BorderLayout());
        
        JPanel titleP = new JPanel(new BorderLayout());
        JLabel title = new JLabel("Configuration");
        title.setFont( new Font("Courier New",Font.PLAIN,19));
        titleP.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        titleP.add(title,BorderLayout.WEST);
        
        name = new JTextField();
        path = new JTextField();
        pass = new JTextField();
        
        name.setPreferredSize(new Dimension(400,25));
        path.setPreferredSize(new Dimension(320,25));
        pass.setPreferredSize(new Dimension(400,25));
        pathC.setPreferredSize(new Dimension(400,25));
        
        JLabel namel = new JLabel("Manager Name");
        JLabel pathl = new JLabel("Database Directory");
        JLabel passl = new JLabel("Password");
        
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
        container.add(titleP,BorderLayout.NORTH);
        
        String op[] = {"Save","Cancel"};
        
        while(JOptionPane.showOptionDialog(null, container, "Configure", JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, op, op[0])==0){
        	if(checkall()){
				manName = name.getText();
				dbpath = Paths.get(path.getText()).toString();
				
				Password = pass.getText();
				new setupXML(manName,dbpath);
				new createTables(dbpath,Password);
				readXml();
				break;
        	}
        }
	}
	

	private void readXml() {
		DocumentBuilder parser;
		File xmlF = new File( "manager.xml");
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
		    new ManagerLoginUI(path);
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
		}
	}

	private boolean checkall() {
		boolean is = true;
		if(!check(name.getText())){
			Show("Invalid name");
			is = false;
		}
		else if(path.getText().equals("")){
			Show("Empty path");
			is = false;
		}
		else if(!check(pass.getText())){
			Show("Inavalid password");
			is = false;
		}
		return is;
	}
	
	private void Show(String string) {
		JOptionPane.showMessageDialog(null, string);
	}

	private boolean check(String s) {
		boolean is = false;
		for(int i=0; i<s.length(); i++){
			if(s.charAt(i) != 8 && s.charAt(i) != 9  && s.charAt(i) != 10  && s.charAt(i)!= 11  && s.charAt(i) != 32){
				is = true;
				break;
			}
		}
		return is;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setDialogTitle("Chosse directory");
		chooser.setAcceptAllFileFilterUsed(false);
		if( chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
			 SetPath(chooser.getSelectedFile().getPath());
		}
	}
	
	private void SetPath(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				path.setText(msg);
			}
		});
	}
	
}
