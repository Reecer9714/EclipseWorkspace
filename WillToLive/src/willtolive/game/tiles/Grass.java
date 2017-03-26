package willtolive.game.tiles;

import willtolive.game.World;
import willtolive.game.objects.Tree;
import willtolive.game.util.ImageLoader;

public class Grass extends Terrain {

	public Grass(int x, int y) {
		super(x, y, ImageLoader.grass);
		if(Math.random()<0.3){
			World.addObject(new Tree(x,y));
		}
	}

}
