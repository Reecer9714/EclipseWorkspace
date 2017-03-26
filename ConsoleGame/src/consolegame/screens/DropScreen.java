package consolegame.screens;

import consolegame.game.creatures.Creature;
import consolegame.game.items.Item;

public class DropScreen extends InventoryBasedScreen {
	
	public DropScreen(Creature player){
		super(player);
	}
	
	@Override
	protected String getVerb() {
		return "drop";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return true;
	}

	@Override
	protected Screen use(Item item) {
		player.drop(item);
		return null;
	}

}
