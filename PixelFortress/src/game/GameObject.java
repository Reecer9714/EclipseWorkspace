package game;

import jgfe.GameContainer;
import jgfe.Renderer;

public class GameObject implements Comparable<GameObject>{
	int pixel;
	float x, y; 
	int depth;
	
	public GameObject(int x, int y, int pixel, int depth) {
		this.pixel = pixel;
		this.x = x;
		this.y = y;
		this.depth = depth;
	}
	
	public GameObject(int x, int y, int pixel) {
		this(x, y, pixel, 0);
	}
	
	public void update(GameContainer gc, float delta){}
	
	public void render(Renderer r){
		r.setPixel((int)(x + 0.5f), (int)(y+0.5f), pixel);
	}
	
	public int compareTo(GameObject e) {
		if(e.getDepth()>depth){
			return -1;
		}else if(e.getDepth()<depth){
			return 1;
		}
		return 0;
	}

	public int getPixel() {
		return pixel;
	}

	public void setPixel(int pixel) {
		this.pixel = pixel;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}
}
