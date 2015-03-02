package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Formatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;



public class setupXML {

	private DocumentBuilderFactory fact;
	private DocumentBuilder parser;
	private Document doc;
	private File sxml;
	
	public setupXML(String name,String path) {
		
		sxml = new File( "manager.xml");
		if(!sxml.exists()){
			try {
				new Formatter( "manager.xml");
				sxml = new File( "manager.xml");
			} 
			catch (FileNotFoundException  | TransformerFactoryConfigurationError e) {}
		}
		
		try {
			setxml(name,path+"/");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setxml(String managerName,String path) throws ParserConfigurationException, TransformerFactoryConfigurationError, FileNotFoundException, TransformerException{
		
		fact = DocumentBuilderFactory.newInstance();
	    parser = fact.newDocumentBuilder();
	    doc = parser.newDocument();
	    
	    doc.normalize();
	    
	    org.w3c.dom.Element manager = doc.createElement("Manager");
	    doc.appendChild(manager);
	    
	    org.w3c.dom.Element msetups = doc.createElement("setups");
	    
	    msetups.setAttribute("ManagerName",managerName);
	    msetups.setAttribute("dbpath",path);
	    msetups.setAttribute("date",getcurrentDate());
	    
	    manager.appendChild(msetups);

	    Transformer tr = TransformerFactory.newInstance().newTransformer();
	    tr.setOutputProperty(OutputKeys.INDENT, "yes");
	    tr.setOutputProperty(OutputKeys.METHOD, "xml");
	    tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    
	    tr.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(sxml)));
	    
	}
	
	public String getcurrentDate() {
		 return Calendar.getInstance().getTime().toString();
	}
		
	
}
