package game;

import jgfe.Renderer;

public class Entity extends GameObject{

	public Entity(int x, int y, int pixel, int depth) {
		super(x, y, pixel, depth);
		// TODO Auto-generated constructor stub
	}
	
	public void render(Renderer r){
		r.setPixel((int)(x + 0.5f), (int)(y + 0.5f), pixel);
	}
	
}
