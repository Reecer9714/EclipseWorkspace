package game;

import java.util.LinkedList;

import jgfe.gfx.Pixel;

public class World{
    Game g;
    Terrain[] terrain;
    int width, height;
    
    private final boolean MASK = true;
    private int[] biomeColors;
    private int numColors = 7;

    public World(Game g, int width, int height){
	this.width = width;
	this.height = height;
	this.g = g;
	terrain = new Terrain[width * height];
	biomeColors = getBiomeColors();
	long sTime = System.currentTimeMillis();
	generateWorld();
	System.out.println("World generated in " + (System.currentTimeMillis() - sTime) + " ms");
    }

    private int[] getBiomeColors(){
	int[] colors = new int[numColors];
	colors[6] = Pixel.WHITE;
	colors[5] = ObjectColor.STONE.color;
	colors[4] = ObjectColor.GRASS.color;
	colors[3] = ObjectColor.SAND.color;
	colors[2] = ObjectColor.SAND.color;
	colors[1] = Pixel.BLUE;// Everything
	colors[0] = Pixel.BLUE;// Cliff side drop of to deep water

	return colors;
    }

    private void addTilesToRenderQueue(){
	for(Terrain t : terrain){
	    g.gameObjects.add(t);
	}
    }

    public void generateWorld(){
	int scWidth = (int)(width * 0.1);
	int scHeight = (int)(height * 0.1);
	float[][] elev = new float[scWidth][scHeight];
//	float[][] moist = new float[scWidth][scHeight];
	System.out.println("Randomizing World...");
	for(int x = 0; x < scWidth; x++){
	    for(int y = 0; y < scHeight; y++){
		elev[x][y] = Game.random.nextFloat();
//		moist[x][y] = Game.random.nextFloat();
	    }
	}

	smooth(elev, 1, scWidth, scHeight, !MASK);
//	smooth(moist, 2, scWidth, scHeight, !MASK);

	float[][] map = new float[width][height];
//	float[][] moistMap = new float[width][height];
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		int conX = Math.floorDiv(x, (width / scWidth));
		int conY = Math.floorDiv(y, (height / scHeight));
		map[x][y] = elev[conX][conY];
//		moistMap[x][y] = moist[conX][conY];
	    }
	}

	smooth(map, 9, width, height, MASK);
//	smooth(moistMap, 5, width, height, MASK);

	System.out.println("Filling World...");
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){

		float e = map[x][y];
//		float m = moistMap[x][y];

		Terrain tile = null;

		// int color = biomeColors[(int)(m*3)][(int)(e*3)];
		// int color = Pixel.getColor(0, (int)(e*255), (int)(m*255));
		// tile = new Terrain(x,y,color);
		//
		if(e < 0.5){//sea level
		    tile = new Terrain(x, y, ObjectColor.WATER.color);
		}else{
		    int color = biomeColors[(int)((e) * numColors)];
		    tile = new Terrain(x, y, color);
		}

		// if (e < 0.5){
		// tile = new Terrain(x,y,ObjectColor.WATER.color);
		// }else if (e >= 0.5 && e < 0.55){
		// tile = new Terrain(x,y,ObjectColor.SAND.color);
		// }else if (e >= 0.55) {
		// tile = new Terrain(x,y,ObjectColor.GRASS.color);
		// }
		terrain[x + (y * width)] = tile;

	    }
	}

	fill(ObjectColor.WATER, ObjectColor.OCEAN);
	addTilesToRenderQueue();
//	System.out.println("World Generated " + width * height);
    }

    public void fill(ObjectColor target, ObjectColor replace){
	System.out.println("Filling Ocean...");
	int x = width/2;
	int y = 0;
	if(target == replace) return;
	LinkedList<Terrain> stack = new LinkedList<Terrain>();
	int w, e;
	stack.add(terrainAt(x,y));
	while(!stack.isEmpty()){
	    w = e = (int)stack.peek().x;
	    y = (int)stack.peek().y;
	    stack.remove(terrainAt(x,y));

	    while(w > 0 && terrainAt(w,y).getPixel() == target.color)
		w--;
	    while(e < width && terrainAt(e,y).getPixel() == target.color)
		e++;
	    for(int n = w; n < e; n++){
		terrainAt(n,y).setPixel(replace.color);
		stack.remove(terrainAt(n,y));
		if(y > 0 && terrainAt(n,y-1).getPixel() == target.color){
		    stack.add(terrainAt(n,y-1));
		}
		if(y < height-1 && terrainAt(n,y+1).getPixel() == target.color){
		    stack.add(terrainAt(n,y+1));
		}
	    }
	}
    }

    private void smooth(float[][] d, int num, int width, int height, boolean doMask){
	for(int n = 0; n <= num; n++){
	    System.out.println("Smoothing World " + n);
	    for(int x = 0; x < width; x++){
		for(int y = 0; y < height; y++){

		    float up = (y == 0) ? d[x][y + 1] : d[x][y - 1];
		    float down = (y == height - 1) ? d[x][y - 1] : d[x][y + 1];
		    float left = (x == 0) ? d[x + 1][y] : d[x - 1][y];
		    float right = (x == width - 1) ? d[x - 1][y] : d[x + 1][y];

		    float ran = 0.5f;
		    float yyy = (float)Func.lerp(up, down, ran);
		    float xxx = (float)Func.lerp(left, right, ran);
		    float replace = (float)Func.lerp(yyy, xxx, ran);

		    replace = (float)Func.clamp(replace, 0.0, 1.0);
		    d[x][y] = replace;

		    if(n == 0 && doMask){
			// double distance =
			// Math.sqrt(Math.pow(width/2-x,2)+Math.pow(height/2-y,2))/(width*2);
			// d[x][y] -= distance;

			double mask = (-Math.pow((x - (width / 2)), 2.) / Math.pow(width, 2)
				- Math.pow((y - (height / 2)), 2) / Math.pow(height, 2)) + 0.1;
			if(mask < 0.1) d[x][y] += mask;
		    }

		}
	    }
	}
    }
    
    public Terrain terrainAt(int x, int y){
	if(x < 0 || y < 0 || x > width || y > height) return null;
	return terrain[x + y * width];
    }

}
