package consolegame.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class StartScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel console) {
		console.write("rl tutorial", 0, 1);
        console.writeCenter("-- press [enter] to start --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}

}
