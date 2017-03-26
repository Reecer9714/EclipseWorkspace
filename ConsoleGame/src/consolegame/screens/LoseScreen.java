package consolegame.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class LoseScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel console) {
		console.write("You lost.", 1, 1);
        console.writeCenter("-- press [enter] to restart --", 22);

	}

	@Override
	public Screen respondToUserInput(KeyEvent e) {
		return e.getKeyCode() == KeyEvent.VK_ENTER ? new PlayScreen() : this;
	}

}
