package willtolive.game.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import willtolive.game.World;
import willtolive.game.phys.Bounds;
import willtolive.game.util.Sprite;

public class GameObject {
	public int x, y;
	private Bounds bounds;
	private Sprite sprite;
	
	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		onCreate();
	}
	
	public GameObject(int x, int y, BufferedImage s){
		this(x,y);
		setSprite(new Sprite(s));
	}
	
	private void onCreate(){
		
	}
	
	private void onDestroy(){
		
	}
	
	public void render(Graphics2D g, World w, double delta){
		renderOffset(g,w,0,0);
	}
	
	public void renderOffset(Graphics2D g, World w, int xOffset, int yOffset){
		if(sprite!=null){
			sprite.render(g, w, x+xOffset, y+yOffset);
		}else{
			g.drawRect(x, y, 32, 32);
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}
}
