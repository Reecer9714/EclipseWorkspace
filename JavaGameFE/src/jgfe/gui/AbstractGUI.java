package jgfe.gui;

import jgfe.GameContainer;
import jgfe.Renderer;

public abstract class AbstractGUI{
    protected String name;
    protected boolean interacted;

    public abstract void init(GameContainer gc);

    public abstract void update(GameContainer gc, float dt);

    public abstract void render(GameContainer gc, Renderer r);

    public String getName(){
	return name;
    }

    public void setName(String name){
	this.name = name;
    }

    public boolean isInteracted(){
	return interacted;
    }

    public void setInteracted(boolean interacted){
	this.interacted = interacted;
    }
}