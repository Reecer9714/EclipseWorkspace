package consolegame.screens;

import consolegame.game.creatures.Creature;
import consolegame.game.items.Item;

public class ThrowScreen extends InventoryBasedScreen {

	private int sx;
	private int sy;

	public ThrowScreen(Creature player, int sx, int sy) {
        super(player);
        this.sx = sx;
        this.sy = sy;
    }
	
	@Override
	protected String getVerb() {
		return "throw";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		// TODO Auto-generated method stub
		return new ThrowAtScreen(player, sx, sy, item);
	}

}
