package hacking.files.programs;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import hacking.Game;
import hacking.files.File;
import hacking.files.Folder;
import hacking.terminal.ConsolePanel;

public abstract class Program extends File implements Runnable{
	
	public Program(Game g, String n){
		super(g, n, null, ".exe");
	}
	
	public Program(Game g, String n, Folder f){
		super(g, n, f, ".exe");
	}
	
	protected ConsolePanel panel;
	protected Thread t;
	private boolean waiting;
	
	public void start(ConsolePanel p, ArrayList<String> args) throws InterruptedException{
		this.panel = p;
		t = new Thread(this);
		t.start();
	}
	
	public void exit(ConsolePanel p, Thread t){
		try{
			t.join(10);
			p.setCurrentProgram(null);
			p.repaint();
		}
		catch(InterruptedException e){
			e.printStackTrace();
		}
	}
	
	protected String getInput(){
		try{
			waiting = true;
			waitForEnter();
			waiting = false;
			panel.newLine();
			return panel.getInputStream().flush();
		}
		catch(InterruptedException e){
			e.printStackTrace();
			return null;
		}
	}
	
	protected String getPrompt(String msg){
		panel.write(msg);
		return getInput();
	}
	
	private void waitForEnter() throws InterruptedException {
	    final CountDownLatch latch = new CountDownLatch(1);
	    KeyEventDispatcher dispatcher = new KeyEventDispatcher() {
	        // Anonymous class invoked from EDT
	        public boolean dispatchKeyEvent(KeyEvent e) {
	            if (e.getKeyCode() == KeyEvent.VK_ENTER)
	                latch.countDown();
	            return false;
	        }
	    };
	    KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(dispatcher);
	    latch.await();  // current thread waits here until countDown() is called
	    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(dispatcher);
	}
	
	public abstract void proccessInput(String in);
	
	@Override
	public void run(){
		exit(panel, t);
	}

	public boolean isWaiting(){
		return waiting;
	}

	public Thread getThread(){
		return t;
	}
	
}
