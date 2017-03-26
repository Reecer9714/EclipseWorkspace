package consolegame.screens;

import consolegame.game.creatures.Creature;
import consolegame.game.items.Item;

public class EatScreen extends InventoryBasedScreen {
	
	public EatScreen(Creature player) {
        super(player);
    }
	
	@Override
	protected String getVerb() {
		return "eat";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.stats.foodValue != 0;
	}

	@Override
	protected Screen use(Item item) {
		player.eat(item);
        return null;
	}

}
