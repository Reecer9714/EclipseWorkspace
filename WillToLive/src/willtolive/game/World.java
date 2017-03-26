package willtolive.game;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import willtolive.game.objects.Entity;
import willtolive.game.util.NaturalSortedList;
import willtolive.game.util.SortedList;

public class World extends JPanel{
    private static final long serialVersionUID = 1L;

    private static Game game;
    private static Island map;
    private static SortedList<Entity> objects = new NaturalSortedList<Entity>();
    private Graphics2D g2d;

    private static int width;
    private static int height;
    private final int SIZE = 200;

    public World(Game g){
	game = g;
	width = SIZE * Game.TILE_SIZE;
	height = SIZE * Game.TILE_SIZE;
	setSize(width, height);
	map = new Island(SIZE);
	g2d = (Graphics2D)getGraphics();
    }

    public void paintComponents(Graphics g){
	super.paintComponents(g);
	render(g, game.getDelta());
    }

    public synchronized void render(Graphics g, double delta){
	// g2d.scale(1.3, 1.3);//Player is placed in the wrong place
	game.getView().render(g2d, this, delta);
	// Lighting/effects
	// GUI
	if(Game.DEBUG) Game.bug.render(g2d);
    }

    public static int getWorldWidth(){
	return width;
    }

    public static int getWorldHeight(){
	return height;
    }

    public Game getGame(){
	return game;
    }

    public Island getMap(){
	return map;
    }

    public static void addObject(Entity e){
	objects.add(e);
    }

    public static SortedList<Entity> getObjects(){
	return objects;
    }
}