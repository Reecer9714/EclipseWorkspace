package consolegame.screens;

import consolegame.game.Spell;
import consolegame.game.creatures.Creature;

public class CastSpellScreen extends TargetBasedScreen{

    private Spell spell;

    public CastSpellScreen(Creature player, String caption, int sx, int sy, Spell spell){
	super(player, caption, sx, sy);
	this.spell = spell;
    }
    
    @Override
    public void selectWorldCoordinate(int x, int y, int screenX, int screenY){
        player.castSpell(spell, x, y);
    }

}
