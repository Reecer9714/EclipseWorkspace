package jgfe;

public abstract class AbstractGameState extends AbstractGame{
    public AbstractGameState currentState;
    private int id;
    
    public AbstractGameState(GameContainer gc){
	this.id = gc.getGameStates().size()+1;
	gc.getGameStates().add(this);
    }
    
    public GameContainer setGameState(GameContainer gc, AbstractGameState gs){
	this.currentState = gs;
	gc.setGameState(gs);
	return gc;
    }
    
    public GameContainer setGameState(GameContainer gc, int id){
	this.currentState = gc.getGameStates().get(id);
	gc.setGameState(currentState);
	return gc;
    }
    
    public AbstractGameState getGameState(GameContainer gc, int id){
	return gc.getGameStates().get(id);
    }
    
    public int getID(){
	return id;
    }
    //public abstract AbstractGameState input(KeyEvent e);
}
