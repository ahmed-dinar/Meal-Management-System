package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import Interfaces.IMenu;

public class MenuListener implements ActionListener,IMenu {

	public MenuListener() {
		exitItem.addActionListener(this);
		aboutItem.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == exitItem){
			System.exit(0);
		}
		else if(obj == aboutItem) {
			//new AboutMM();
		}
	}

}
