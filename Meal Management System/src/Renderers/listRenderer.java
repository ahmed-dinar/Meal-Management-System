package Renderers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;


class listRenderer extends DefaultListCellRenderer     
{     
              
      
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent( JList<?> list,     
                Object value, int index, boolean isSelected,     
                boolean cellHasFocus )     
        {     
        super.getListCellRendererComponent(list, value, index,     
                 isSelected, cellHasFocus );     
      
          
   
	        if( isSelected )     
		    {     
		          setBackground(Color.RED); 
    
		     }     
	      
	           
            
            	return( this );     
        }     
    }  