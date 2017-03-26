package willtolive.game.util;

import java.awt.*;
import java.awt.image.BufferedImage;

import willtolive.game.World;

public class Sprite{
	//double rot, trans;
	
	private BufferedImage sprite;
	private Point origin = new Point(0,0);
	//private double imageAngle = 0.0;
	
	public Sprite(BufferedImage i) {
		sprite=i;
	}
		
	public void resetOrigin(){
		setOrigin(0,0);
	}
	public void centerOrigin(){
		setOrigin(sprite.getWidth()/2,sprite.getHeight()/2);
	}
	public void setOrigin(Point p){
		origin = p;
	}
	public void setOrigin(int x, int y){
		origin = new Point(x,y);
	}
	public Point getOrigin() {
		return origin;
	}
	
	public void render(Graphics2D g2d, World w, int x, int y){
		g2d.drawImage(sprite, x, y, w);
	}
	
	public void render(Graphics2D g2d, World w, int x, int y, double theta, float trans){
		g2d.rotate(theta, x + origin.x, y + origin.y);
		if(trans!=1.0f) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, trans));
		
		render(g2d, w, x, y);
		
		g2d.rotate(-theta, x + origin.x, y + origin.y);
		if(trans!=1.0f) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}

	
}
