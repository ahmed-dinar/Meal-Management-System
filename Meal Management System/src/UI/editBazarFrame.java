package UI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Custom.IntegerField;

public class editBazarFrame {
	
	private JPanel bazarDialogpanel ;
	private JLabel nameheader ;
	private JLabel costheader ;
	private JLabel descheader ;
	private int totalItem = 1;
	private JButton addDesc;
	private JTextArea desc;
	private JTextField name;
	private JTextField amount;

	
	public editBazarFrame() {
		
		declareAll();
		
	}
	
	public void declareAll(){
		bazarDialogpanel = new JPanel(new GridBagLayout());
		nameheader = new JLabel("Name");
		costheader = new JLabel("Total Cost");
		descheader = new JLabel("Description");
		addDesc = new JButton("Add Item");
		name = new JTextField();
		amount = new IntegerField();
		desc = new JTextArea();
	}
	
	public int bazarDialog(){
		

		nameheader.setFont(headerFont());
		costheader.setFont(headerFont());
		descheader.setFont(headerFont());
		addDesc.setFont(headerFont());
		

		name.setFont(headerFont());
		amount.setFont(headerFont());
		desc.setFont(headerFont());
		

		name.setPreferredSize(new Dimension(150,30));
		amount.setPreferredSize(new Dimension(150,30));
		desc.setLineWrap(true);
		
		addDesc.addActionListener(new ActionListenerClass());
		
		bazarDialogpanel.add(nameheader,customConstraints(0, 2, 1,1,15,0,0,15));
		bazarDialogpanel.add(name,customConstraints(0, 3, 1,1,0,0,5,15));
		bazarDialogpanel.add(descheader,customConstraints(1, 0, 3,1,0,0,0,0));
		bazarDialogpanel.add(costheader,customConstraints(0, 4, 1,1,15,0,0,15));
		bazarDialogpanel.add(amount,customConstraints(0, 5, 1,1,0,0,5,15));

		
		JScrollPane pane = new JScrollPane(desc);
		pane.setPreferredSize(new Dimension(500,180));
		bazarDialogpanel.add(pane,customConstraints(1, 1, 3,6,0,0,0,0));
		
		bazarDialogpanel.add(addDesc,customConstraints(1, 7, 1,1,5,0,0,0));
		
		String[] options = new String[]{"Ok", "Cancel"};
		return JOptionPane.showOptionDialog(null, bazarDialogpanel, "Add Bazar",JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, options[1]);
		
	}
	
	public Font headerFont(){
		Font font = new Font("",Font.PLAIN,16);
		return font;
	}
	
	public GridBagConstraints customConstraints(int grx,int gry,int grw,int grh,int top,int left,int bottom,int right){
		GridBagConstraints constraints= new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.gridx = grx;
		constraints.gridy = gry;
		constraints.gridwidth = grw;
		constraints.gridheight = grh;
		constraints.insets = new Insets(top, left, bottom, right);
		return constraints;
	}
	
	class  ActionListenerClass implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == addDesc){
				
				JPanel itempanel = new JPanel(new GridBagLayout());
				JTextField itemname  = new JTextField();
				JTextField itemcost = new IntegerField();
				JLabel headname = new JLabel("Item Name: ");
				JLabel headcost = new JLabel("Item Cost: ");
	
				
				
				itemname.setPreferredSize(new Dimension(150,30));
				itemcost.setPreferredSize(new Dimension(150,30));
				
				itemname.setFont(headerFont());
				itemcost.setFont(headerFont());
				headname.setFont(headerFont());
				headcost.setFont(headerFont());
				
				itempanel.add(headname,customConstraints(0, 0, 1, 1, 0, 0, 0, 0));
				itempanel.add(itemname,customConstraints(1, 0, 3, 1, 0, 0, 0, 0));
				itempanel.add(headcost,customConstraints(0, 1, 1, 1, 10, 10, 0, 0));
				itempanel.add(itemcost,customConstraints(1, 1, 3, 1, 10, 0, 0, 0));
				
				String[] OK = new String[]{"Ok","Cancel"};
				
				int flag = JOptionPane.showOptionDialog(null, itempanel, "Add Item",JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,null, OK, null);
				
				if(flag==0){
					String getitemName = itemname.getText();
					String getitemCost = itemcost.getText();
					int isEmptyName = getitemName.compareTo("");
					if( isEmptyName != 0  ){
						String descrip = desc.getText().concat("\n   ").concat(Integer.toString(totalItem)).concat(". ").concat(getitemName);
						if(getitemCost.compareTo("") != 0){
							descrip = descrip.concat(" = ").concat(getitemCost).concat(" tk.");
						}
						desc.setText(descrip);
						totalItem++;
					}
					else{
						if(isEmptyName == 0){
							JOptionPane.showMessageDialog(null, "Empty Name! Please type a name");
						}
					}
				}
				else{
					System.out.println("Cancel");
				}
				
			}
			
		}
		
	}
	

	public String getDesc() {
		return desc.getText();
	}


	public String getName() {
		return name.getText();
	}

	public String getAmount() {
		return amount.getText();
	}

	public JTextArea getDescArea(){
		return desc;
	}
	
	public JTextField getNameField(){
		return name;
	}
	
	public JTextField getCostField(){
		return amount;
	}
	
	
}
