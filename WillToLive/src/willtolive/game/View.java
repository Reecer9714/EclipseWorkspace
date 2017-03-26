package willtolive.game;

import java.awt.Graphics2D;

import willtolive.game.objects.Entity;
import willtolive.game.objects.Player;
import willtolive.game.tiles.Terrain;
import willtolive.game.util.Func;
import willtolive.game.util.SquareMap;

public class View{
    private int x;
    private int y;
    private int width;
    private int height;
    private int dwidth;
    private int dheight;

    public View(int x, int y, int w, int h){
	this.x = (x);
	this.y = (y);
	width = w;
	height = h;
	dwidth = 0 - x;
	dheight = 0 - y;
    }

    public void update(Player p){
	x = (p.x - (width / 2));
	y = (p.y - (height / 2));
	dwidth = 0 - x;
	dheight = 0 - y;
    }

    public void render(Graphics2D g, World w, double delta){
	SquareMap<Terrain> temp = w.getMap().getTiles();

	for(int i = Func.pixelToTile(x) - 1; i < Func.pixelToTile(x + width) + 1; i++){
	    for(int j = Func.pixelToTile(y) - 1; j < Func.pixelToTile(y + height) + 1; j++){
		if(i > -1 && i < temp.getSize() && j > -1 && j < temp.getSize()){
		    Terrain tile = ((Terrain)temp.get(i, j));
		    tile.renderOffset(g, w, tile.x + dwidth, tile.y + dheight);
		}
	    }
	}

	for(Entity e : World.getObjects()){
	    if(e instanceof Player){
		e.render(g, w, delta);
	    }else{
		if(e.x > x - Game.TILE_SIZE * 2 && e.x < x + width + Game.TILE_SIZE * 2 && e.y > y - Game.TILE_SIZE * 2
			&& e.y < y + height + Game.TILE_SIZE * 2){
		    e.renderOffset(g, w, dwidth, dheight);
		}
	    }
	}

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

    public int getWidth(){
	return width;
    }

    public void setWidth(int width){
	this.width = width;
    }

    public int getHeight(){
	return height;
    }

    public void setHeight(int height){
	this.height = height;
    }
}
