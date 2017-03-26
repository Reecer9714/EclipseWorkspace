package consolegame.screens;

import consolegame.game.creatures.Creature;
import consolegame.game.items.Item;

public class DrinkScreen extends InventoryBasedScreen {

	public DrinkScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "quaff/drink";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.quaffEffect() != null;
	}

	@Override
	protected Screen use(Item item) {
		player.quaff(item);
		return null;
	}

}
