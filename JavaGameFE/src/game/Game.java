package game;

import java.util.Random;

import jgfe.*;

public class Game extends AbstractGame{

    static Random random;

    public Game(){

    }

    @Override
    public void init(GameContainer gc){
	
    }

    @Override
    public void update(GameContainer gc, float delta){

    }

    @Override
    public void render(GameContainer gc, Renderer r){
	
    }

    public static void main(String[] args){
	GameContainer gc = new GameContainer(new Game());
	gc.setDebug(true);
	gc.setWidth(720);
	gc.setHeight(480);
	gc.setScale(1.5f);
	gc.setFrameCap(60);
	gc.setLockFrameRate(true);
	gc.setClearScreen(true);
	gc.start();
    }

}
