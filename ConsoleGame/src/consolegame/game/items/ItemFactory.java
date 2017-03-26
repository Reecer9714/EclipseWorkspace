package consolegame.game.items;

import asciiPanel.AsciiPanel;
import consolegame.game.*;
import consolegame.game.creatures.Creature;

public class ItemFactory{
    private World world;

    public ItemFactory(World world){
	this.world = world;
    }

    public Item newRock(int depth){
	Item rock = new Item("rock", ',', AsciiPanel.yellow);
	world.addAtEmptyLocation(rock, depth);
	return rock;
    }

    public Item newVictoryItem(int depth){
	Item item = new Item("Trophy", '*', AsciiPanel.brightYellow);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newChest(int depth){
	Item item = new Item("Chest", '+', AsciiPanel.yellow);
	item.newInventory(10);
	//Dont really want to place in the normal way: Not going to be in caves.
	return item;
    }
    
    public Item newPotion(int depth){
	Item item = new Item("Expired Potion", '^', AsciiPanel.cyan);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newHealthPotion(int depth){
	Item item = new Item("Health Potion", '^', AsciiPanel.green);
	item.setQuaffEffect(new Effect(1){
	    public void start(Creature creature){
		if(creature.stats.hp == creature.stats.maxHp) return;

		creature.stats.modifyHp(5);
		creature.doAction("look healthier");
	    }
	});
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newPoisenPotion(int depth){
	Item item = new Item("poison potion", '^', AsciiPanel.white);
	item.setQuaffEffect(new Effect(20){
	    public void start(Creature creature){
		creature.doAction("look sick");
	    }

	    public void update(Creature creature){
		super.update(creature);
		creature.stats.modifyHp(-1);
	    }
	});

	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newWarriorPotion(int depth){
	Item item = new Item("rage potion", '^', AsciiPanel.white);
	item.setQuaffEffect(new Effect(20){
	    public void start(Creature creature){
		creature.stats.modifyAttackValue(5);
		creature.stats.modifyDefenseValue(5);
		creature.doAction("look stronger");
	    }

	    public void end(Creature creature){
		creature.stats.modifyAttackValue(-5);
		creature.stats.modifyDefenseValue(-5);
		creature.doAction("look less strong");
	    }
	});

	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newVisionPotion(int depth){
	Item item = new Item("Sight Potion", '^', AsciiPanel.yellow);
	item.setQuaffEffect(new Effect(40){
	    public void start(Creature creature){
		creature.stats.modifyVisionValue(3);
		creature.doAction("see farther");
	    }

	    public void end(Creature creature){
		creature.stats.modifyVisionValue(-3);
		creature.doAction("can't see as well");
	    }
	});
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newRandomPotion(int depth){
	switch((int)(World.random.nextDouble() * 3) - 1){
	    case 0:
		return newVisionPotion(depth);
	    case 1:
		return newWarriorPotion(depth);
	    default:
		return newHealthPotion(depth);
	}
    }

    public Item newDagger(int depth){
	Item item = new Item("dagger", '/', AsciiPanel.white);
	item.stats.modifyAttackValue(5);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newSword(int depth){
	Item item = new Item("sword", '/', AsciiPanel.brightWhite);
	item.stats.modifyAttackValue(10);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newStaff(int depth){
	Item item = new Item("staff", '/', AsciiPanel.yellow);
	item.stats.modifyAttackValue(5);
	item.stats.modifyDefenseValue(3);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newBow(int depth){
	Item item = new Item("bow", ')', AsciiPanel.yellow);
	item.stats.modifyAttackValue(1);
	item.stats.modifyRangedAttackValue(5);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newTorch(int depth){
	Item item = new Item("torch", '/', AsciiPanel.yellow);
	item.stats.modifyAttackValue(2);
	item.stats.modifyVisionValue(2);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    ///////////////////////////// ARMOR/////////////////////////////////////////////////
    public Item newLightArmor(int depth){
	Item item = new Item("tunic", '[', AsciiPanel.green);
	item.stats.modifyDefenseValue(2);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newMediumArmor(int depth){
	Item item = new Item("chainmail", '[', AsciiPanel.white);
	item.stats.modifyDefenseValue(4);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newHeavyArmor(int depth){
	Item item = new Item("platemail", '[', AsciiPanel.brightWhite);
	item.stats.modifyDefenseValue(6);
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newGoldPile(int depth){
	Item item = new Item("gold pile", '$', AsciiPanel.yellow.brighter());
	// item.modifyGoldValue();
	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newRandomWeapon(int depth){
	switch((int)(World.random.nextDouble() * 3)){
	    case 0:
		return newDagger(depth);
	    case 1:
		return newSword(depth);
	    case 2:
		return newBow(depth);
	    default:
		return newStaff(depth);
	}
    }

    public Item newRandomArmor(int depth){
	switch((int)(World.random.nextDouble() * 3)){
	    case 0:
		return newLightArmor(depth);
	    case 1:
		return newMediumArmor(depth);
	    default:
		return newHeavyArmor(depth);
	}
    }

    public Item newHolyMagesSpellbook(int depth){
	Item item = new Item("holy mage's spellbook", '+', AsciiPanel.brightWhite);
	item.addWrittenSpell("minor heal", 4, Spell.newMinorHeal(), true);

	item.addWrittenSpell("major heal", 8, Spell.newMajorHeal(), true);

	item.addWrittenSpell("slow heal", 12, Spell.newSlowHeal(), true);

	item.addWrittenSpell("inner strength", 16, Spell.newInnerStrength(), true);

	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item newUselessMagesSpellbook(int depth){
	Item item = new Item("useless mage's spellbook", '+', AsciiPanel.brightBlue);

	world.addAtEmptyLocation(item, depth);
	return item;
    }

    public Item randomSpellBook(int depth){
	switch((int)(World.random.nextDouble() * 2)){
	    case 0:
		return newHolyMagesSpellbook(depth);
	    default:
		return newUselessMagesSpellbook(depth);
	}
    }

}
