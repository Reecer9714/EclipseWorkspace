package consolegame.game;

import consolegame.game.creatures.Creature;

public class Spell{
    private String name;

    public String name(){
	return name;
    }

    private int manaCost;

    public int manaCost(){
	return manaCost;
    }

    private Effect effect;

    public Effect effect(){
	return (Effect)effect.clone();
    }

    public Spell(String name, int manaCost, Effect effect, boolean requiresTarget){
	this.name = name;
	this.manaCost = manaCost;
	this.effect = effect;
	this.requiresTarget = requiresTarget;
    }

    public boolean requiresTarget;

    public boolean requiresTarget(){
	return requiresTarget;
    }

    public Effect newEffect(int duration){
	return new Effect(duration);
    }

    // Effect Factory
    public static Effect newMinorHeal(){
	return new Effect(1){
	    public void start(Creature creature){
                if (creature.stats.hp == creature.stats.maxHp)
                    return;
                
                creature.stats.modifyHp(5);
                creature.doAction("look healthier");
            }
	};
    }
    
    public static Effect newMajorHeal(){
	return new Effect(1){
	    public void start(Creature creature){
                if (creature.stats.hp == creature.stats.maxHp)
                    return;
                
                creature.stats.modifyHp(15);
                creature.doAction("look healthier");
            }
	};
    }
    
    public static Effect newSlowHeal(){
	return new Effect(40){
	    public void update(Creature creature){
                super.update(creature);
                creature.stats.modifyHp(1);
            }
	};
    }
    
    public static Effect newInnerStrength(){
	return new Effect(50){
	    @Override
	    public void start(Creature creature){
                creature.stats.modifyAttackValue(2);
                creature.stats.modifyDefenseValue(2);
                creature.stats.modifyVisionValue(1);
                creature.stats.modifyRegenHpPer1000(10);
                creature.stats.modifyRegenManaPer1000(-10);
                creature.doAction("seem to glow with inner strength");
            }
            public void update(Creature creature){
                super.update(creature);
                if (Math.random() < 0.25)
                    creature.stats.modifyHp(1);
            }
            public void end(Creature creature){
                creature.stats.modifyAttackValue(-2);
                creature.stats.modifyDefenseValue(-2);
                creature.stats.modifyVisionValue(-1);
                creature.stats.modifyRegenHpPer1000(-10);
                creature.stats.modifyRegenManaPer1000(10);
            }
	};
    }
}
