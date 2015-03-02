package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import Custom.BackgroundMenuBar;
import DB.checkPassword;
import Dialogs.AboutMM;
import Interfaces.IMenu;
import Listeners.MenuListener;

public class ManagerLoginUI extends JFrame implements IMenu,ActionListener,KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPasswordField passBox;
	private JButton logB;
	private Color white = Color.WHITE;
	private String path;
	private JLabel info;
	
	public ManagerLoginUI(String path) {
		super("Login");
		this.path = path;

		initFrame();
		new MenuListener();
		
		setPreferredSize(new Dimension(700,450));
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
		pack();
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dim.width/2-this.getPreferredSize().width/2, dim.height/2-this.getPreferredSize().height/2);
	}

	private void initFrame() {
		initMenu();
		initleft();
		initRight();
	}
	
	private void initleft() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400,getHeight()));
		panel.setBackground(white);
		panel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(232,233,234)));
		JLabel logo = new JLabel();
		
		try {
			logo.setIcon(new ImageIcon(loadImg("cover.png")));
		} catch (IllegalArgumentException e) { System.err.println("Error");}
		
		panel.add(logo);
		add(panel,BorderLayout.WEST);
	}
	
	public Image loadImg(String icon) {
		Image img = null;
		try {
			img = ImageIO.read(getClass().getResource("/resources/"+icon));
		} catch (IOException e) {}
		return img;
	}

	private void initRight() {
		JPanel panel = new JPanel();
		JPanel holdp = new JPanel(new GridBagLayout());
		JLabel passlab = new JLabel("Password");
		info = new JLabel();
		logB = new JButton("Login");
		
		passlab.setForeground(white);
		passlab.setFont(new Font("Arial",Font.PLAIN,16));
		
		panel.setPreferredSize(new Dimension(300,getHeight()));
		panel.setBackground(new Color(0,175,240));
		
		info.setFont(new Font("Arial",Font.PLAIN,16));
		info.setForeground(Color.WHITE);
		info.setPreferredSize(new Dimension(210,35));
		
		passBox = new JPasswordField();
		passBox.setPreferredSize(new Dimension(210,35));
		passBox.setBackground(white);
		passBox.addKeyListener(this);
		
		logB.setPreferredSize(new Dimension(90,35));
		logB.setFont(new Font("Arial",Font.PLAIN,16));
		logB.setFocusPainted(false);
		logB.setContentAreaFilled(true);
		logB.setBackground(new Color(0,175,240));
		logB.setOpaque(true);
		logB.addActionListener(this);
		logB.addKeyListener(this);
		
		aboutItem.addActionListener(this);
		
		holdp.setOpaque(false);
		holdp.setBorder(new EmptyBorder(new Insets(120, 0, 0, 0)));
		
		holdp.add(passlab,getCon(0, 0,1,0));
		holdp.add(passBox,getCon(0, 1,3,5));
		holdp.add(logB,getCon(0, 2,2,0));
		holdp.add(info,getCon(0, 3,3,0));
		
		panel.add(holdp);
		add(panel,BorderLayout.EAST);
	}
	
	private GridBagConstraints getCon(int x,int y,int w,int b) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		constraints.gridwidth = w;
		constraints.insets = new Insets(0, 0, b, 0);
		return constraints;
	}

	private void initMenu() {
		JMenuBar menuBar = new BackgroundMenuBar();
		menuBar.setBorder(BorderFactory.createEmptyBorder());
		
		JMenu filemenu = new JMenu("File");
		JMenu helpmenu = new JMenu("Help");
		
		filemenu.add(exitItem);

		helpmenu.add(helpItem);
		helpmenu.add(aboutItem);
		
		menuBar.add(filemenu);
		menuBar.add(helpmenu);
		
		setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == logB){
			@SuppressWarnings("deprecation")
			String pass = passBox.getText().toString();
			if(pass == null || pass.equals("")){
				updateInfo("Please Enter a password");
			}
			else {
				if(new checkPassword(pass,path).isCorrect()){
					updateInfo("Loading data..");;
					new ManagerUI(path);
					this.dispose();
				}
				else {
					updateInfo("Password Incorrect!");;
				}
			}
		}
		else if(e.getSource() == aboutItem){
			new AboutMM();
		}
	}

	private void updateInfo(String string) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				info.setText(string);
			}
		});
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
			temp += "\\";
			dir = temp;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return dir;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode()==KeyEvent.VK_ENTER){
			logB.doClick();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
