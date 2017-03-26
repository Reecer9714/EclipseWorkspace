package game;

import java.util.Random;

import jgfe.*;
import jgfe.gfx.Camera;
import jgfe.util.ResourceLoader;

public class Game extends AbstractGame{
    static GameContainer gc;
    static Random random;
    static int width = 500;
    static int height = 500;

    World world;
    Player player;
    Camera cam;
    ResourceLoader loader;
    SortedList<GameObject> gameObjects;

    public Game(){
	random = new Random();
	gameObjects = new NaturalSortedList<GameObject>();
	loader = new ResourceLoader();
	loader.loadTileSheet("human", "/images/human.png", 1, 2);
    }

    @Override
    public void init(GameContainer gc){
	world = new World(this, width, height);
	cam = gc.getWindow().getCamera();
	cam.setWidth(740 / 2);
	cam.setHeight(480 / 2);
	player = new Player(world.width / 2, world.height / 2, ObjectColor.PLAYER.color);
	gameObjects.add(player);
    }

    @Override
    public void update(GameContainer gc, float delta){
	player.update(gc, delta);
	cam.centerOn((int)(player.x), (int)(player.y));
	System.out.println(" Cam: " + cam.getCenterX() + " " + cam.getCenterY() + " Player: " + player.getX() + " "
		+ player.getY());
    }

    @Override
    public void render(GameContainer gc, Renderer r){
	for(GameObject o : gameObjects){
	    o.render(r);
	}
    }

    public static void main(String[] args){
	gc = new GameContainer(new Game());
	gc.setDebug(true);
	gc.setLockFrameRate(true);
	gc.setFrameCap(60);
	gc.setWidth(720);
	gc.setHeight(480);
	// gc.setScale(3.0f);
	gc.start();
    }

    public SortedList<GameObject> getGameObjects(){
	return gameObjects;
    }

}
