package consolegame.game;

import java.util.*;

public class WorldFactory{
    private long seed;
    private Random ran;
    private int width;
    private int height;
    private int depth;
    private Tile[][][] tiles;
    private int[][][] regions;
    private int nextRegion;

    ArrayList<Room> rooms;

    public WorldFactory(int width, int height, int depth){
	this(System.currentTimeMillis(), width, height, depth);
    }

    public WorldFactory(long seed, int width, int height, int depth){
	this.seed = seed;
	this.width = width;
	this.height = height;
	this.depth = depth;
	ran = new Random(seed);
	tiles = new Tile[width][height][depth];
	regions = new int[width][height][depth];
	nextRegion = 1;
    }

    public World build(){
	return new World(tiles, seed);
    }

    private WorldFactory randomizeTiles(){
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		for(int z = 0; z < depth; z++){
		    tiles[x][y][z] = Math.random() < 0.5 ? Tile.FLOOR : Tile.WALL;
		}
	    }
	}
	return this;
    }

    private WorldFactory smooth(int times){
	Tile[][][] tiles2 = new Tile[width][height][depth];
	for(int time = 0; time < times; time++){

	    for(int x = 0; x < width; x++){
		for(int y = 0; y < height; y++){
		    for(int z = 0; z < depth; z++){
			int floors = 0;
			int rocks = 0;

			for(int ox = -1; ox < 2; ox++){
			    for(int oy = -1; oy < 2; oy++){
				if(x + ox < 0 || x + ox >= width || y + oy < 0 || y + oy >= height) continue;

				if(tiles[x + ox][y + oy][z] == Tile.FLOOR)
				    floors++;
				else rocks++;
			    }
			}
			tiles2[x][y][z] = floors >= rocks ? Tile.FLOOR : Tile.WALL;
		    }
		}
	    }
	    tiles = tiles2;
	}
	return this;
    }

    private WorldFactory createRegions(){
	regions = new int[width][height][depth];

	for(int z = 0; z < depth; z++){
	    for(int x = 0; x < width; x++){
		for(int y = 0; y < height; y++){
		    if(tiles[x][y][z] != Tile.WALL && regions[x][y][z] == 0){
			int size = fillRegion(nextRegion++, x, y, z);

			if(size < 25) removeRegion(nextRegion - 1, z);
		    }
		}
	    }
	}
	return this;
    }

    private void removeRegion(int region, int z){
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		if(regions[x][y][z] == region){
		    regions[x][y][z] = 0;
		    tiles[x][y][z] = Tile.WALL;
		}
	    }
	}
    }

    private int fillRegion(int region, int x, int y, int z){
	int size = 1;
	ArrayList<Point> open = new ArrayList<Point>();
	open.add(new Point(x, y, z));
	regions[x][y][z] = region;

	while(!open.isEmpty()){
	    Point p = open.remove(0);

	    for(Point neighbor : p.neighbors8()){
		if(neighbor.x < 0 || neighbor.y < 0 || neighbor.x >= width || neighbor.y >= height) continue;

		if(regions[neighbor.x][neighbor.y][neighbor.z] > 0
			|| tiles[neighbor.x][neighbor.y][neighbor.z] == Tile.WALL)
		    continue;

		size++;
		regions[neighbor.x][neighbor.y][neighbor.z] = region;
		open.add(neighbor);
	    }
	}
	return size;
    }

    public WorldFactory connectRegions(){
	for(int z = 0; z < depth - 1; z++){
	    connectRegionsDown(z);
	}
	return this;
    }

    private void connectRegionsDown(int z){
	List<String> connected = new ArrayList<String>();

	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		String region = regions[x][y][z] + "," + regions[x][y][z + 1];
		if(tiles[x][y][z] == Tile.FLOOR && tiles[x][y][z + 1] == Tile.FLOOR && !connected.contains(region)){
		    connected.add(region);
		    connectRegionsDown(z, regions[x][y][z], regions[x][y][z + 1]);
		}
	    }
	}
    }

    private void connectRegionsDown(int z, int r1, int r2){
	List<Point> candidates = findRegionOverlaps(z, r1, r2);

	int stairs = 0;
	do{
	    Point p = candidates.remove(0);
	    tiles[p.x][p.y][z] = Tile.STAIRS_DOWN;
	    tiles[p.x][p.y][z + 1] = Tile.STAIRS_UP;
	    stairs++;
	}
	while(candidates.size() / stairs > 500);// higher less stairs
    }

    public List<Point> findRegionOverlaps(int z, int r1, int r2){
	ArrayList<Point> candidates = new ArrayList<Point>();

	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		if(tiles[x][y][z] == Tile.FLOOR && tiles[x][y][z + 1] == Tile.FLOOR && regions[x][y][z] == r1
			&& regions[x][y][z + 1] == r2){
		    candidates.add(new Point(x, y, z));
		}
	    }
	}

	Collections.shuffle(candidates);
	return candidates;
    }

    private WorldFactory addExitStairs(){
	int x = -1;
	int y = -1;

	do{
	    x = (int)(ran.nextDouble() * width);
	    y = (int)(ran.nextDouble() * height);
	}
	while(tiles[x][y][0] != Tile.FLOOR);

	tiles[x][y][0] = Tile.STAIRS_UP;
	return this;
    }

    public WorldFactory fillArea(){
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		for(int z = 0; z < depth; z++){
		    tiles[x][y][z] = Tile.UNKNOWN;
		}
	    }
	}
	return this;
    }
    
    public WorldFactory createRooms(int numRoom, int minSize, int maxSize, int depth){
	rooms = new ArrayList<Room>();

	do{
	    
	    int w = (int)(Math.random() * (maxSize - minSize)) + minSize;
	    int h = (int)(Math.random() * (maxSize - minSize)) + minSize;
	    int x = (int)(Math.random() * (width-w));
	    int y = (int)(Math.random() * (height-h));
	    Room r = new Room(x, y, depth, w, h, ran.nextDouble());

	    rooms.add(r);
	}
	while(rooms.size() < numRoom);
	
	for(Room r : rooms){
	    for(int x = r.getX(); x <= r.getX() + r.getWidth(); x++){
		for(int y = r.getY(); y <= r.getY() + r.getHeight(); y++){
		    if(x > width) x = width-1;
		    if(x < 0) x = 0;
		    if(y > height) y = height-1;
		    if(y < 0) y  = 0;
		    if(x==r.getX()||y==r.getY()||x==r.getX() + r.getWidth()||y==r.getY() + r.getHeight()){
			tiles[x][y][depth] = Tile.WALL;
		    }else{
			tiles[x][y][depth] = Tile.FLOOR;
		    }
		}
	    }
	}
	
	return this;
    }

    public WorldFactory connectRooms(){
	ArrayList<Point> doors = new ArrayList<Point>();
	for(Room r : rooms){
//	    System.out.println(r.getExits());
	    doors.addAll(r.getExits());
	}
	while(!doors.isEmpty()){
	    // if one door left delete
	    // could be changed later to allow branching halls
	    if(doors.size() == 1){
		Point last = doors.get(0);
		tiles[last.x][last.y][last.z] = Tile.WALL;
		doors.remove(last);
		break;
	    }

	    Point a = doors.get((ran.nextInt(doors.size())));
	    Point b = doors.get((ran.nextInt(doors.size())));
	    if(a == b) continue;

	    BasicPath hall = new BasicPath(tiles, a, b);

	    for(Point p : hall.points()){
		tiles[p.x][p.y][p.z] = Tile.FLOOR;
		for(Point d: p.neighbors8()){
		    if(tiles[d.x][d.y][d.z] == Tile.UNKNOWN) tiles[d.x][d.y][d.z] = Tile.WALL;
		}
	    }

	    tiles[a.x][a.y][a.z] = Tile.DOOR;
	    tiles[b.x][b.y][b.z] = Tile.DOOR;

	    doors.remove(a);
	    doors.remove(b);
	}
	return this;
    }

    public WorldFactory makeCaves(){
	return randomizeTiles().smooth(8).createRegions().connectRegions().addExitStairs();
    }

    /*
     * public WorldFactory makeDungeon() { //TODO: place random nodes choose
     * four nodes find area if area is too big select new nodes inside area if
     * area is right sixe make it a room with up to three nodes on sides repeat
     * till no area is too big or desired room number connect room nodes places
     * stairs repeat whole precess starting at stair location }
     */
    public WorldFactory makeDungeon(int numRoom, int minSize, int maxSize){

	return fillArea().createRooms(numRoom, minSize, maxSize, 0).connectRooms();
    }
}