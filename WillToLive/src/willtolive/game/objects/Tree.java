package willtolive.game.objects;

import willtolive.game.util.ImageLoader;

public class Tree extends BreakableEntity{
	
	public Tree(int x, int y){
		super(x,y,ImageLoader.tree,2,100);
		
	}

	@Override
	public void onBreak() {
		System.out.println("Tree Broke");
		setHp(100);
	}

}
