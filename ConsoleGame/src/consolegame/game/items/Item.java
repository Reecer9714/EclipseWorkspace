package consolegame.game.items;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import consolegame.game.*;

public class Item extends GameObject{
    public String details;

    public ItemStats stats;

    public Effect quaffEffect() { return stats.quaffEffect; }
    public void setQuaffEffect(Effect effect) { this.stats.quaffEffect = effect; }
    
    private List<Spell> writtenSpells;
    public List<Spell> writtenSpells() { return writtenSpells; }
    
    private Inventory inventory;
    public Inventory inventory(){return inventory;}
    public void newInventory(int size){
	inventory = new Inventory(size);
    }

    public void addWrittenSpell(String name, int manaCost, Effect effect, boolean requiresTarget){
        writtenSpells.add(new Spell(name, manaCost, effect, requiresTarget));
    }

    public Item(String name, char glyph, Color color){
	super(name, glyph, color);
	stats = new ItemStats(this);
	writtenSpells = new ArrayList<Spell>();
    }

    public String details(){
	String details = "";

	if(stats.attackValue != 0) details += "   attack:" + stats.attackValue;

	if(stats.thrownAttackValue != 0) details += "   throw attack:" + stats.thrownAttackValue;

	if(stats.rangedAttackValue != 0) details += "   range attack:" + stats.rangedAttackValue;

	if(stats.defenseValue != 0) details += "   defense:" + stats.defenseValue;

	if(stats.foodValue != 0) details += "   food:" + stats.foodValue;

	return details;
    }

}
