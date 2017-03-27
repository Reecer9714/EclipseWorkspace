package consolegame.game;

import java.util.ArrayList;
import java.util.List;

import consolegame.game.creatures.Creature;

public class LevelUpController{
    private static LevelUpOption[] options = new LevelUpOption[]{ new LevelUpOption("Increased hit points"){
	public void invoke(Creature creature){
	    creature.stats.modifyMaxHp(10);
	    creature.stats.modifyHp(10);
	    creature.doAction("look a lot healthier");
	}
    }, new LevelUpOption("Increased mana"){
	public void invoke(Creature creature){
	    creature.stats.modifyMaxMana(10);
	    creature.stats.modifyMana(5);
	    creature.doAction("look more magical");
	}
    }, new LevelUpOption("Increased attack value"){
	public void invoke(Creature creature){
	    creature.stats.modifyAttackValue(2);
	    creature.doAction("look stronger");
	}
    }, new LevelUpOption("Increased defense value"){
	public void invoke(Creature creature){
	    creature.stats.modifyDefenseValue(1);
	    creature.doAction("look a little tougher");
	}
    }, new LevelUpOption("Increased vision"){
	public void invoke(Creature creature){
	    creature.stats.modifyVisionValue(1);
	    creature.doAction("look a little more aware");
	}
    }, new LevelUpOption("Increased hp regeneration"){
	public void invoke(Creature creature){
	    creature.stats.modifyRegenHpPer1000(10);
	    creature.doAction("look a little less bruised");
	}
    }, new LevelUpOption("Increased mana regeneration"){
	public void invoke(Creature creature){
	    creature.stats.modifyRegenManaPer1000(10);
	    creature.doAction("look a little less tired");
	}
    } };

    public void autoLevelUp(Creature creature){
	options[(int)(Math.random() * options.length)].invoke(creature);
    }

    public List<String> getLevelUpOptions(){
	List<String> names = new ArrayList<String>();
	for(LevelUpOption option : options){
	    names.add(option.name());
	}
	return names;
    }

    public LevelUpOption getLevelUpOption(String name){
	for(LevelUpOption option : options){
	    if(option.name().equals(name)) return option;
	}
	return null;
    }
}