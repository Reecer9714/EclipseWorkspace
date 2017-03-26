package willtolive.game;

import java.awt.event.*;

public class Input implements KeyListener, MouseListener, MouseMotionListener{
	private Game gc;
	
	private static boolean[] keys = new boolean[256];
	private static boolean[] keysLast = new boolean[256];
	
	private static boolean[] buttons = new boolean[5];
	private static boolean[] buttonsLast = new boolean[5];
	
	private static int mouseX, mouseY;
	
	public Input(Game gc){
		this.gc = gc;
		gc.addKeyListener(this);
		gc.addMouseListener(this);
		gc.addMouseMotionListener(this);
	}
	
	public void update(){
		keysLast = keys.clone();
		buttonsLast = buttons.clone();
	}
	
	public boolean isKey(int keyCode){
		return keys[keyCode];
	}
	
	public boolean isKeyPressed(int keyCode){
		return keys[keyCode] && !keysLast[keyCode];
	}
	
	public boolean isKeyReleased(int keyCode){
		return !keys[keyCode] && keysLast[keyCode];
	}
	
	public boolean isButton(int keyCode){
		return buttons[keyCode];
	}
	
	public boolean isButtonPressed(int keyCode){
		return buttons[keyCode] && !buttonsLast[keyCode];
	}
	
	public boolean isButtonReleased(int keyCode){
		return !buttons[keyCode] && buttonsLast[keyCode];
	}
	
	//implemented methods
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = (int)(e.getX());
		mouseY = (int)(e.getY());
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = (int)(e.getX());
		mouseY = (int)(e.getY());
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		buttons[e.getButton()] = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		buttons[e.getButton()] = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public int getViewX() {
		return mouseX;
	}

	public int getViewY() {
		return mouseY;
	}
	
	public int getMouseX() {
		return gc.getView().getX() + mouseX;
	}

	public int getMouseY() {
		return gc.getView().getY() + mouseY;
	}
}
