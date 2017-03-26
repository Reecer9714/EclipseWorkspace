package consolegame.screens;

import java.awt.event.KeyEvent;

import asciiPanel.AsciiPanel;

public class HelpScreen implements Screen {

	@Override
	public void displayOutput(AsciiPanel console) {
		console.clear();
		console.writeCenter("roguelike help", 1);
		console.write("Descend the Caves Of Slight Dange, find the Trophy, and return to", 1, 3);
		console.write("the surface to win. Use what you find to avoid dying.", 1, 4);
		
		int y = 6;
		console.write("[g] or [,] to pick up", 2, y++);
		console.write("[d] to drop", 2, y++);
		console.write("[e] to eat", 2, y++);
		console.write("[q] to quaff/drink" , 2, y++);
		console.write("[w] to wear or wield", 2, y++);
		console.write("[?] for help", 2, y++);
		console.write("[x] to examine your items", 2, y++);
		console.write("[;] to look around", 2, y++);
		
		console.writeCenter("-- press any key to continue --", 22);
	}

	@Override
	public Screen respondToUserInput(KeyEvent e) {
		return null;
	}

}
