package willtolive.game;

import java.awt.Graphics;

public class Debug {
	private Game game;
	
	public Debug(Game g){
		game = g;
	}
	
	public void render(Graphics g){		
		g.drawString("FPS: " + game.getFPS(), 10, 10);
	}
}
