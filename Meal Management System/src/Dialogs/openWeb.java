package Dialogs;

import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;

public class openWeb {
		
	public openWeb(String url) {
		
		URI uri = null;
		try {
			uri = new URI(url);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		openWebpage(uri);
		
	}
	
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	           // e.printStackTrace();
	        }
	    }
	}

	
}
