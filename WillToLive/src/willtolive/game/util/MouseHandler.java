package willtolive.game.util;

import java.awt.event.*;

import willtolive.game.World;
import willtolive.game.objects.BreakableEntity;
import willtolive.game.objects.Entity;

public class MouseHandler implements MouseListener, MouseMotionListener{
	private int mouseX, mouseY;
	
	public MouseHandler() {
		mouseX = 0;
		mouseY = 0;
	}
	
	//MouseListener methods
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton()==MouseEvent.BUTTON1){
			//Left Clicked
			for(Entity i: World.getObjects()){
				if(i instanceof BreakableEntity &&i.contains(e.getPoint())) ((BreakableEntity) i).leftClicked(e);//clicks object
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
	
	//MouseMotionListener methods
	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	//Gets and Sets
	public int getMouseX() {
		return mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

}
