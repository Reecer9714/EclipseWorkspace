package consolegame.game;

import consolegame.game.creatures.Creature;

public class Effect implements Cloneable{
    protected int duration;

    public boolean isDone(){
	return duration < 1;
    }   
    
    public Effect(int duration){
	this.duration = duration;
    }

    public Effect(Effect other){
	this.duration = other.duration;
    }

    public void update(Creature creature){
	duration--;
    }

    public void start(Creature creature){
    }

    public void end(Creature creature){}

    @Override
    public Object clone(){
	try{
	    return super.clone();
	}
	catch(CloneNotSupportedException e){
	    // This should never happen
	    throw new InternalError(e.toString());
	}
    }

}
