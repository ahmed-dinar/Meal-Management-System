package Dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class AboutMM extends JFrame implements MouseListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JButton git;

	public AboutMM() {
		
		Init();
		
		
		setResizable(false);
		setPreferredSize(new Dimension(600,500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();
		
		
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getPreferredSize().width/2, dim.height/2-this.getPreferredSize().height/2);
		
	}

	private void Init() {
		
		JPanel panel = new JPanel(new BorderLayout());
		JPanel topP = new JPanel(new BorderLayout());
		JPanel botP = new JPanel(new BorderLayout());
		JTextArea lincence = new JTextArea();
		JLabel licenceL = new JLabel("Licence Details");
		JLabel logo = new JLabel();
	
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		topP.setPreferredSize(new Dimension(500,200));
		topP.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
		
		JPanel leftP = new JPanel();
		JPanel rihgtP = new JPanel(new GridBagLayout());
		
		leftP.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
		
		leftP.setPreferredSize(new Dimension(200,200));
		rihgtP.setPreferredSize(new Dimension(390,200));

		try {
			logo.setIcon(new ImageIcon(loadImg("logo.png")));
		} catch (IllegalArgumentException e) { }
		
		leftP.add(logo);
		
	
		initDetail(rihgtP);
		
		
		topP.add(leftP,BorderLayout.WEST);
		topP.add(rihgtP,BorderLayout.EAST);
		

		
		/* licence panel */

		
		lincence.setText("");
		
		
		/**************/
		licenceL.setFont(new Font("",Font.PLAIN,15));
		
		lincence.setWrapStyleWord(true);
		lincence.setLineWrap(true);
		lincence.setEditable(false);
		lincence.setFocusable(false);
		lincence.setOpaque(false);

		
		JScrollPane pane = new JScrollPane(lincence,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
				);
		
		pane.setPreferredSize(new Dimension(500,200));
		pane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		botP.add(licenceL, BorderLayout.NORTH);
		botP.add(pane,BorderLayout.CENTER);
		
		panel.add(topP,BorderLayout.NORTH);
		panel.add(botP,BorderLayout.CENTER);
		setContentPane(panel);
	}
	
	
	@SuppressWarnings("unchecked")
	private void initDetail(JPanel detail) {
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
		JLabel hed = new JLabel("Developer:");
		JLabel name = new JLabel("Ahmed Dinar");
		JLabel email = new JLabel("madinar.cse@gmail.com");
		JLabel sp = new JLabel("Thanks for the logo : WWahid Adnan");
		
		
		git = new JButton();
		git.setPreferredSize(new Dimension(100,84));
		git.addMouseListener(this);
		git.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		git.setFocusPainted(false);
		git.setContentAreaFilled(true);
		git.setCursor(new Cursor(Cursor.HAND_CURSOR));
		try {
			git.setIcon(new ImageIcon(loadImg("gg.png")));
		} catch (IllegalArgumentException e) { }
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 1;
		gc.gridy = 0;
		gc.gridwidth = 2;
		gc.gridheight = 3;
		detail.add(git, gc);
		
		hed.setFont(new Font("",Font.BOLD,22));
		@SuppressWarnings("rawtypes")
		Map attributes = hed.getFont().getAttributes();
		attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		hed.setFont(hed.getFont().deriveFont(attributes));
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		detail.add(hed, gc);
		
		
		name.setFont(new Font("",Font.PLAIN,18));

		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 1;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		detail.add(name, gc);
		
		
		email.setFont(new Font("",Font.PLAIN,13));
		email.setForeground(Color.BLUE);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 2;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		detail.add(email, gc);
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 3;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		detail.add(new JSeparator(), gc);
		
		sp.setFont(new Font("",Font.PLAIN,12));
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 5;
		gc.gridwidth = 1;
		gc.gridheight = 1;
		detail.add(sp, gc);
		
	}

	public Image loadImg(String icon) {
		
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/resources/"+icon));
		} catch (IOException e) {  }
		return img;
	
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		git.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		new openWeb("https://github.com/ahmed-dinar");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		git.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		git.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
