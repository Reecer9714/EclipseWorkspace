package consolegame.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public interface Screen {
	public void displayOutput(AsciiPanel console);
	
	public Screen respondToUserInput(KeyEvent e);
}
