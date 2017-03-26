package consolegame.game.creatures;

import consolegame.game.Stats;
import consolegame.game.World;

public class CreatureStats extends Stats{
    private Creature creature;
    private World world;
    public int maxHp;
    public int hp;
    public int regenHpCooldown;
    public int regenHpPer1000;
    public int maxMana;
    public int mana;
    public int regenManaCooldown;
    public int regenManaPer1000;
    public int maxFood;
    public int food;
    public int xp;
    public int level;
    
    public CreatureStats(World world, Creature creature, int maxHp, int vision, int attack, int defense){
	this.world = world;
	this.creature = creature;
	this.maxHp = maxHp;
	this.visionValue = vision;
	this.attackValue = attack;
	this.defenseValue = defense;
	hp = maxHp;
	level = 1;
	maxFood = 1000;
	food = maxFood / 3 * 2;
	regenHpPer1000 = 5;
	regenManaPer1000 = 10;
    }

    public void modifyMaxHp(int amount){
        maxHp += amount;
    }

    public void modifyHp(int amount){
        hp += amount;
        if(hp > maxHp){
	    hp = maxHp;
	}else if(hp < 1){
	    creature.doAction("die");
	    creature.leaveCorpse();
	    world.remove(creature);
	}
    }

    public void modifyRegenHpCooldown(int amount){
        regenHpCooldown += amount;
    }

    public void modifyRegenHpPer1000(int amount){
        regenHpPer1000 += amount;
    }

    public void modifyMaxMana(int amount){
        maxMana += amount;
    }

    public void modifyMana(int amount){
        mana += amount;
    }

    public void modifyRegenManaCooldown(int amount){
        regenManaCooldown += amount;
    }

    public void modifyRegenManaPer1000(int amount){
        regenManaPer1000 += amount;
    }

    public void modifyMaxFood(int amount){
        maxFood += amount;
    }

    public void modifyFood(int amount){
        food += amount;
        if(food > maxFood){
	    maxFood = maxFood + food / 2;
	    food = maxFood;
	    creature.notify("You can't believe your stomach can hold that much!");
	    modifyHp(-1);
	}else if(food < 1 && creature.isPlayer()){
	    creature.doAction("die of hunger");
	    modifyHp(-1000);

	}
    }

    public void modifyXp(int amount){
        xp += amount;
        
        creature.notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);

	while(xp > (int)(Math.pow(level, 1.5) * 20)){
	    level++;
	    creature.doAction("advance to level %d", level);
	    creature.ai().onGainLevel();
	    modifyHp(level * 2);
	}
    }

    public void modifyLevel(int amount){
        level += amount;
    }
    
}