package consolegame.screens;

import java.awt.event.KeyEvent;
import java.util.List;

import asciiPanel.AsciiPanel;
import consolegame.game.LevelUpController;
import consolegame.game.creatures.Creature;

public class LevelUpScreen implements Screen{
	private LevelUpController controller;
	private Creature player;
	private int picks;
	
	public LevelUpScreen(Creature player, int picks){
		this.controller = new LevelUpController();
		this.player = player;
		this.picks = picks;
	}

	@Override
	public void displayOutput(AsciiPanel console) {
		List<String> options = controller.getLevelUpOptions();

	    int y = 5;
	    console.clear(' ', 5, y, 30, options.size() + 2);
	    console.write("     Choose a level up bonus       ", 5, y++);
	    console.write("------------------------------", 5, y++);

	    for (int i = 0; i < options.size(); i++){
	    	console.write(String.format("[%d] %s", i+1, options.get(i)), 5, y++);
	    }
	}

	@Override
	public Screen respondToUserInput(KeyEvent e) {
		List<String> options = controller.getLevelUpOptions();
	    String chars = "";

	    for (int i = 0; i < options.size(); i++){
	      chars = chars + Integer.toString(i+1);
	    }

	    int i = chars.indexOf(e.getKeyChar());

	    if (i < 0)
	      return this;

	    controller.getLevelUpOption(options.get(i)).invoke(player);

	    if (--picks < 1)
	      return null;
	    else
	      return this;
	}
}
