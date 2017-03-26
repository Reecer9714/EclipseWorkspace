package game;

import java.awt.event.KeyEvent;

import jgfe.GameContainer;
import jgfe.Renderer;
import jgfe.gfx.Image;
import jgfe.gfx.TileSheet;
import jgfe.util.ResourceLoader;

public class Player extends Entity{
    Image sprite;
    int speed;

    public Player(int x, int y, int pixel){
	super(x, y, pixel, 0);
	speed = 5;
	sprite = ((TileSheet)(ResourceLoader.getResources().get("human"))).getTileImage(1);
    }

    @Override
    public void update(GameContainer gc, float delta){
	if(gc.getInput().isKey(KeyEvent.VK_W)){
	    y -= speed * delta;
	}
	if(gc.getInput().isKey(KeyEvent.VK_S)){
	    y += speed * delta;
	}
	if(gc.getInput().isKey(KeyEvent.VK_A)){
	    x -= speed * delta;
	}
	if(gc.getInput().isKey(KeyEvent.VK_D)){
	    x += speed * delta;
	}
    }

    @Override
    public void render(Renderer r){
	r.drawImage(sprite, (int)x, (int)y);
    }

}
