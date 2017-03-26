package consolegame.game;

import java.util.ArrayList;
import java.util.List;

import consolegame.game.creatures.Creature;
import consolegame.game.items.Item;

public class Room{
    private int x, y, sx, sy;
    private int z;
    private double diff;// determines creature spawns and loot
    private List<Point> exits;

    public Room(int x, int y, int z, int sx, int sy, double diff){
	this.x = x;
	this.y = y;
	this.sx = sx;
	this.sy = sy;
	this.z = z;
	this.diff = diff;
	createExits();
    }

    private void createExits(){
	exits = new ArrayList<Point>();
	int num = (int)(Math.random()%3)+1;
	int hor, ver;
	Point p;
	for(int i = 0; i < num; i++){
	    do{
		hor = (int)(Math.random()%1);
		ver = (int)(Math.random()%1);

		p = new Point(x + hor * sx,y + ver * sy, z);
	    }
	    while(exits.contains(p));

	    exits.add(p);
	}
    }

    public void placeItem(World world, Item item){
	int dx;
	int dy;

	dx = (int)(Math.random() * (sx - 1)) + 1;
	dy = (int)(Math.random() * (sy - 1)) + 1;

	item.x = x + dx;
	item.y = y + dy;
	world.add(item);
    }

    public void placeCreature(World world, Creature creature){
	int dx;
	int dy;

	dx = (int)(Math.random() * (sx - 1)) + 1;
	dy = (int)(Math.random() * (sy - 1)) + 1;

	creature.x = x + dx;
	creature.y = y + dy;
	creature.z = z;
	world.add(creature);
    }

    public double getDiff(){
	return diff;
    }

    public void setDiff(double diff){
	this.diff = diff;
    }

    public List<Point> getExits(){
	return exits;
    }

    public int getWidth(){
        return sx;
    }

    public void setWidth(int w){
        this.sx = w;
    }

    public int getHeight(){
        return sy;
    }

    public void setHeight(int h){
        this.sy = h;
    }
    
    @Override
    public String toString(){
	return x + "," + y + " " + sx + "," + sy;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public int getZ(){
        return z;
    }

    public void setZ(int z){
        this.z = z;
    }
}
