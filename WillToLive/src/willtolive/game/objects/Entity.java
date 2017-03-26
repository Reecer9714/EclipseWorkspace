package willtolive.game.objects;

import java.awt.Point;
import java.awt.image.BufferedImage;

import willtolive.game.util.Vector2D;

public class Entity extends GameObject implements Comparable<Entity>{
	private int depth;
	public double speed;
	private BufferedImage image;
	public Vector2D movement = new Vector2D();
	
	public Entity(int x, int y, BufferedImage s, int d){
		super(x,y,s);
		depth = d;
		image = s;
		speed = 0;
	}
	
	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public BufferedImage getImage(){
		return image;
	}

	public int getDepth() {
		return depth;
	}
	
	public Vector2D getMovement(){
		return movement;
	}
	
	public void stopMovement(){
		movement.setMag(0);
	}
	
	public void setMovement(Vector2D v){
		movement = v;
	}
	
	public void setMovement(double x, double y){
		movement.setXComp(x);
		movement.setYComp(y);
	}
	
	public void update(){
		movement.setDir(1);
		movement = movement.scale(speed);
		if(movement.getXComp()!=0) x = (int) (x + movement.getXComp());
		if(movement.getYComp()!=0) y = (int) (y + movement.getYComp());
	}
	
	public int compareTo(Entity e) {
		if(e.getDepth()>depth){
			return -1;
		}else if(e.getDepth()<depth){
			return 1;
		}
		return 0;
	}

	public boolean contains(Point p) {
		// TODO Change sprite.getWidth() to a Boundary Rectangle used for collisions
		//      Maybe move this method into a Bounding Box class
		if(p.getX()>x&&p.getX()<x+image.getWidth()&&p.getY()>y&&p.getY()<y+image.getHeight())
			return true;
		return false;
	}
	
}
