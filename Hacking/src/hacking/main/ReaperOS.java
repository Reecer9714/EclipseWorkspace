package hacking.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import hacking.main.programs.gui.*;

public class ReaperOS extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private GUIGame game;
	private JDesktopPane desktop;
	private JMenu startmenu;
	private JMenuBar menubar;
	
	private Terminal terminal;
	private WebBrowser webbrowser;
	private FileBrowser filebrowser;
	private TextEditor texteditor;
	private MyComputer mycomputer;//Going to be removed
	private Store store;//Same
	private MailBrowser mailbox;
	private Cracker cracker;
	
	public ImageIcon readMail;
	public ImageIcon unreadMail;
	
	private ArrayList<Image> backgrounds;
	
	
	/**
	 * Creates new form ReaperOS icons
	 * https://www.iconfinder.com/iconsets/onebit
	 */
	public ReaperOS(GUIGame game){
		this.game = game;
		initComponents();
	}
	
	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents(){
		setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		setMinimumSize(new Dimension(720, 640));
		setPreferredSize(new Dimension(1080, 720));
		
		loadBackgrounds();
		
		readMail = createImageIcon("/icons/readMail.png");
		unreadMail = createImageIcon("/icons/unreadMail.png");
		
		desktop = new JDesktopPane(){
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				g.drawImage(backgrounds.get(1), 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		menubar = new JMenuBar();
		startmenu = new JMenu();
		
		JLabel icon = new JLabel();
		icon.setIcon(createImageIcon("/icons/Reaper.png"));
		menubar.add(icon);
		
		Component horizontalStrut = Box.createHorizontalStrut(5);
		menubar.add(horizontalStrut);
		
		getContentPane().add(desktop, BorderLayout.CENTER);
		setJMenuBar(menubar);
		startmenu.setText("REAPERos");
		startmenu.setFont(new Font("Data Control", Font.BOLD, 14));
		menubar.add(startmenu);
		
		JMenuItem miShutdown = new JMenuItem("Shutdown");
		miShutdown.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//TODO: Show popup
				System.exit(0);
			}
		});
		startmenu.add(miShutdown);
		
		Component horizontalGlue = Box.createHorizontalGlue();
		menubar.add(horizontalGlue);
		
		JMenu mnWifi = new JMenu();
		mnWifi.setIcon(createImageIcon("/icons/wifiicon.png"));
		menubar.add(mnWifi);
		
		
		//For when there is sound
		/*
		JMenu mnSound = new JMenu("");
		mnSound.setIcon(new ImageIcon(
				Designer.class.getResource("/com/sun/javafx/webkit/prism/resources/mediaMuteDisabled.png")));
		mnSound.setHorizontalAlignment(SwingConstants.LEFT);
		menubar.add(mnSound);
		
		JSlider volumeSlider = new JSlider();
		volumeSlider.setPreferredSize(new Dimension(20, 200));
		volumeSlider.setOrientation(SwingConstants.VERTICAL);
		mnSound.add(volumeSlider);
		*/
		
		loadPrograms(getWidth(), getHeight());
		
		pack();
	}
	
	// Helpers
	public ImageIcon createImageIcon(String path){
		URL imgURL = getClass().getResource(path);
		if(imgURL != null){
			return new ImageIcon(imgURL);
		}else{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	
	private void loadPrograms(int width, int height){
		terminal = new Terminal(this, createImageIcon("/icons/terminal.png"), (int)(width * 0.7), (int)(height * 0.6));
		webbrowser = new WebBrowser(this, createImageIcon("/icons/webbrowser.png"), (int)(width * 0.5),
				(int)(height * 0.5));
		filebrowser = new FileBrowser(this, createImageIcon("/icons/filebrowser.png"), (int)(width * 0.5),
				(int)(height * 0.5));
		texteditor = new TextEditor(this, createImageIcon("/icons/texteditor.png"), (int)(width * 0.5),
				(int)(height * 0.5));
		mailbox = new MailBrowser(this, createImageIcon("/icons/mailbox.png"), (int)(width * 0.5), (int)(height * 0.5));
		mycomputer = new MyComputer(this, createImageIcon("/icons/mycomputer.png"), (int)(width * 0.3),
				(int)(height * 0.25));
		store = new Store(this, createImageIcon("/icons/store.png"), (int)(width * 0.5), (int)(height * 0.5));
		cracker = new Cracker(this, createImageIcon("/icons/cracker.png"), (int)(width * 0.5), (int)(height * 0.25));
	}
	
	private void loadBackgrounds(){
		backgrounds = new ArrayList<Image>();
		File dir = new File("./resources/backgrounds");
		File[] directoryListing = dir.listFiles();
		if(directoryListing != null){
			for(File child : directoryListing){
				try{
					backgrounds.add(ImageIO.read(child));
				}
				catch(IOException e){
					e.printStackTrace();
				}
			}
		}else{
			System.out.println("Error Backgrounds Folder NOT Found");
		}
	}
	
	// Gets and Sets
	public JMenuBar getMenubar(){
		return menubar;
	}
	
	public JDesktopPane getDesktop(){
		return desktop;
	}
	
	public JMenu getStartmenu(){
		return startmenu;
	}
	
	public GUIGame getGame(){
		return game;
	}
	
	public Terminal getTerminal(){
		return terminal;
	}
	
	public WebBrowser getWebbrowser(){
		return webbrowser;
	}
	
	public FileBrowser getFilebrowser(){
		return filebrowser;
	}
	
	public TextEditor getTexteditor(){
		return texteditor;
	}
	
	public MailBrowser getMailbox(){
		return mailbox;
	}

	public Cracker getCracker(){
		return cracker;
	}
	
}
