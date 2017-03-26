package consolegame.screens;

import consolegame.game.creatures.Creature;
import consolegame.game.items.Item;

public class EquipScreen extends InventoryBasedScreen {

	public EquipScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "equip";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.stats.attackValue > 0 || item.stats.defenseValue > 0;
	}

	@Override
	protected Screen use(Item item) {
		if(player.weapon() == item || player.armor() == item){
			player.unequip(item);
		}else{
			player.equip(item);
		}
	    return null;
	}

}
