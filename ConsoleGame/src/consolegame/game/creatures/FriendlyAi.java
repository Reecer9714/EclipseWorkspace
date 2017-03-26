package consolegame.game.creatures;

public class FriendlyAi extends CreatureAi{
    private Creature owner;

    public FriendlyAi(Creature creature, Creature owner){
	super(creature);
	this.owner = owner;
    }

    @Override
    public void onUpdate(){
	// wander around owner
	// dont get to far away from owner
	// attack enemies
	if(creature.distanceTo(owner) > creature.stats.visionValue){
	    // move towards
	    int dx = (creature.x > owner.x) ? -1 : 1;
	    int dy = (creature.y > owner.y) ? 1 : -1;

	    creature.moveBy(dx, dy, 0);
	    return;
	}

	if(Math.random() < 0.2)
	    return;
	else wander();
    }

}
