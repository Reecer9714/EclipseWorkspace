package willtolive.game.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import willtolive.game.Game;

public class KeyBinds extends KeyAdapter{
	private static boolean[] keys = new boolean[500];
	public boolean up, down, left, right, released;
	
	public void update(){
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()]=true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()]=false;
		Game.p.stopMovement();
	}
}
