package jgfe.gui;

import java.util.ArrayList;

import jgfe.GameContainer;
import jgfe.Renderer;

public class GUIContainer{
    private ArrayList<AbstractGUI> guiObjects = new ArrayList<AbstractGUI>();

    private String ouputName = null;

    public void update(GameContainer gc, float dt){
	boolean interacted = false;

	for(AbstractGUI ag : guiObjects){
	    ag.update(gc, dt);

	    if(ag.isInteracted()){
		ouputName = ag.getName();
		interacted = true;
	    }
	}

	if(interacted == false){
	    ouputName = null;
	}
    }

    public void render(GameContainer gc, Renderer r){
	for(int i = 0; i < guiObjects.size(); i++){
	    guiObjects.get(i).render(gc, r);
	}
    }

    public void addGUIObject(AbstractGUI gui){
	guiObjects.add(gui);
    }

    public void removeGUIObject(String name){
	for(int i = 0; i < guiObjects.size(); i++){
	    if(guiObjects.get(i).getName().equals(name)){
		guiObjects.remove(i);
	    }
	}
    }

    public ArrayList<AbstractGUI> getGuiObjects(){
	return guiObjects;
    }

    public String getOuputName(){
	return ouputName;
    }

}