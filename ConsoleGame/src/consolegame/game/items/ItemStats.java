package consolegame.game.items;

import consolegame.game.Effect;
import consolegame.game.Stats;

public class ItemStats extends Stats{
    //private Item item; commented untill needed
    
    public int foodValue;
    public int hpValue;
    public int thrownAttackValue;
    public int rangedAttackValue;
    public Effect quaffEffect;

    public ItemStats(Item item){
	//this.item = item;
	thrownAttackValue = 1;
    }
    
    public void modifyFoodValue(int amount){
	foodValue += amount;
    }

    public void modifyHpValue(int amount){
	hpValue += amount;
    }

    public void modifyThrownAttackValue(int amount){
	thrownAttackValue += amount;
    }

    public void modifyRangedAttackValue(int amount){
	rangedAttackValue += amount;
    }
}