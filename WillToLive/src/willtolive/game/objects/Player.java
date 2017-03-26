package willtolive.game.objects;

import java.awt.Graphics2D;

import willtolive.game.Game;
import willtolive.game.World;
import willtolive.game.util.*;

public class Player extends Entity{
	
	public Player(int x, int y){
		super(x,y,ImageLoader.getPlayer(),1);
		speed = 4;
		getSprite().centerOrigin();
		System.out.println("Spawning Player");
	}

	@Override
	public void update(){
		movement.setMag(speed);
		movement.setDir(Func.pointDirection(x+getSprite().getOrigin().x, y+getSprite().getOrigin().y,
				Game.getInput().getMouseX(), Game.getInput().getMouseY()));
		
		super.update();
	}
	
	@Override
	public void render(Graphics2D g, World w, double delta){
		getSprite().render(g, w, Game.getWorld().getWidth()/2, Game.getWorld().getHeight()/2, movement.getDir(), 1.0f);
	}
	
}
