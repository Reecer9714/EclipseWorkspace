package game;

import java.util.Random;

import game.objects.Player;
import game.objects.Tree;
import jgfe.*;
import jgfe.entity.GameObject;
import jgfe.gfx.Camera;
import jgfe.util.*;

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
    private Tree tree;

    public Game(){
	random = new Random();
	gameObjects = new NaturalSortedList<GameObject>();
	loader = new ResourceLoader();
	loader.loadTileSheet("human", "/images/human.png", 1, 2);
	loader.loadTileSheet("animals", "/images/animals.png", 4, 2);
	loader.loadTileSheet("trees", "/images/tree.png", 5, 5);
	loader.loadTileSheet("ores", "/images/ores.png", 2, 2);
	loader.loadTileSheet("objects", "/images/objects1.png", 2, 2);
    }

    @Override
    public void init(GameContainer gc){
	world = new World(this, width, height);
	cam = new Camera(720/10, 480/10);
	//gc.getWindow().setCamera(cam);
	player = new Player(this, width/2, height/2);
	cam.setTarget(player);
    }

    @Override
    public void update(GameContainer gc, float delta){
//	for(GameObject o : gameObjects){
//	    o.update(gc, delta);
//	}
	player.update(gc, delta);
	cam.update(gc, delta);
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
	gc.setScale(2.0f);
	gc.start();
    }

    public SortedList<GameObject> getGameObjects(){
	return gameObjects;
    }

}
