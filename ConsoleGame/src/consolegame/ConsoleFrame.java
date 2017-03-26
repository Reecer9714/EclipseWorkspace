package consolegame;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import asciiPanel.AsciiPanel;
import consolegame.screens.Screen;
import consolegame.screens.StartScreen;
import javax.swing.JPanel;
import java.awt.Color;

public class ConsoleFrame extends JFrame implements KeyListener{
    private static final long serialVersionUID = -1978761411822383035L;

    private AsciiPanel console;
    private Screen screen;

    /**
     * Launch the application.
     */
    public static void main(String[] args){
	EventQueue.invokeLater(new Runnable(){
	    public void run(){
		try{
		    ConsoleFrame con = new ConsoleFrame();
		    con.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		    con.setLocationRelativeTo(null);
		    con.setResizable(false);
		    con.setVisible(true);
		}
		catch(Exception e){
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the frame.
     */
    public ConsoleFrame(){
	JPanel panel = new JPanel();
	panel.setBackground(Color.BLACK);
	getContentPane().add(panel, BorderLayout.CENTER);
	console = new AsciiPanel();
	panel.add(console);
	pack();

	screen = new StartScreen();

	addKeyListener(this);
	repaint();

    }

    public void Update(){

    }

    public void repaint(){
	// Clear
	super.repaint();
	console.clear();
	screen.displayOutput(console);
    }

    @Override
    public void keyTyped(KeyEvent e){}

    @Override
    public void keyPressed(KeyEvent e){
	screen = screen.respondToUserInput(e);
	repaint();
    }

    @Override
    public void keyReleased(KeyEvent e){}

}
