package game;

public class OldWorld{
    Game g;
    Terrain[] terrain;
    int width, height;

    public OldWorld(Game g, int width, int height){
	this.width = width;
	this.height = height;
	this.g = g;
	terrain = new Terrain[width * height];
	generateWorld();
    }

    private void addTilesToRenderQueue(){
	for(Terrain t : terrain){
	    g.gameObjects.add(t);
	}
    }

    // public void render(GameContainer gc, Renderer r){
    // for(int x = gc.cam; x < width; x++){
    // for(int y = 0; y < height; y++){
    // terrain[x][y]
    // }
    // }
    // }

    public void generateWorld(){

	double[][] elev = new double[width][height];
	System.out.println("Randomizing World...");
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		elev[x][y] = Game.random.nextDouble();
	    }
	}

	smooth(elev, 6);

	System.out.println("Filling World...");
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){

		double e = elev[x][y];

		Terrain tile = null;
		// tile = new Terrain(x,y,Pixel.getColor(0, 0, (int)(e*255)));
		if(e < 0.5){
		    tile = new Terrain(x, y, ObjectColor.WATER.color);
		}else if(e >= 0.5 && e < 0.55){
		    tile = new Terrain(x, y, ObjectColor.SAND.color);
		}else if(e >= 0.55){
		    tile = new Terrain(x, y, ObjectColor.GRASS.color);
		}
		terrain[x + (y * width)] = tile;
		// tile.setElev(e);
	    }
	}

	addTilesToRenderQueue();
	System.out.println("World Generated " + width * height);
    }

    private void smooth(double[][] d, int num){
	for(int n = 0; n <= num; n++){
	    System.out.println("Smoothing World " + n);
	    for(int x = 0; x < width; x++){
		for(int y = 0; y < height; y++){

		    double up = (y == 0) ? d[x][y + 1] : d[x][y - 1];
		    double down = (y == height - 1) ? d[x][y - 1] : d[x][y + 1];
		    double left = (x == 0) ? d[x + 1][y] : d[x - 1][y];
		    double right = (x == width - 1) ? d[x - 1][y] : d[x + 1][y];

		    double ran = 0.5;
		    double yyy = Func.lerp(up, down, ran);
		    double xxx = Func.lerp(left, right, ran);
		    double replace = Func.lerp(yyy, xxx, ran);

		    replace = Func.clamp(replace, 0.0, 1.0);
		    d[x][y] = replace;

		    if(n == num){
			double mask = (-Math.pow((x - (width / 2)), 2.) / Math.pow(width, 2)
				- Math.pow((y - (height / 2)), 2) / Math.pow(height, 2));
			if(mask < 0) d[x][y] += mask;
		    }

		}
	    }
	}
    }
}
