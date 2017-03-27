package consolegame.game;

import java.util.*;

public class BasicPath{
    private List<Point> points;
    private ArrayList<Point> open;
    private ArrayList<Point> closed;
    private HashMap<Point, Point> parents;
    private HashMap<Point, Integer> totalCost;

    public List<Point> points(){
	return points;
    }

    public BasicPath(Tile[][][] tiles, Point a, Point b){
	open = new ArrayList<Point>();
	closed = new ArrayList<Point>();
	parents = new HashMap<Point, Point>();
	totalCost = new HashMap<Point, Integer>();
	points = findPath(tiles, a, b, 300);
    }

    private int heuristicCost(Point from, Point to){
	return Math.max(Math.abs(from.x - to.x), Math.abs(from.y - to.y));
    }

    private int costToGetTo(Point from){
	return parents.get(from) == null ? 0 : (1 + costToGetTo(parents.get(from)));
    }

    private int totalCost(Point from, Point to){
	if(totalCost.containsKey(from)) return totalCost.get(from);

	int cost = costToGetTo(from) + heuristicCost(from, to);
	totalCost.put(from, cost);
	return cost;
    }

    private void reParent(Point child, Point parent){
	parents.put(child, parent);
	totalCost.remove(child);
    }

    public ArrayList<Point> findPath(Tile[][][] tiles, Point start, Point end, int maxTries){
	open.clear();
	closed.clear();
	parents.clear();
	totalCost.clear();

	open.add(start);

	for(int tries = 0; tries < maxTries && open.size() > 0; tries++){
	    Point closest = getClosestPoint(end);

	    open.remove(closest);
	    closed.add(closest);

	    if(closest.equals(end))
		return createPath(start, closest);
	    else checkNeighbors(tiles, end, closest);
	}
	return null;
    }

    private Point getClosestPoint(Point end){
	Point closest = open.get(0);
	for(Point other : open){
	    if(totalCost(other, end) < totalCost(closest, end)) closest = other;
	}
	return closest;
    }

    private void checkNeighbors(Tile[][][] tiles, Point end, Point closest){
	for(Point neighbor : closest.neighbors8()){
	    if(neighbor.x < 0||neighbor.y < 0||neighbor.x > tiles.length||neighbor.y > tiles[0].length) continue;
	    if(closed.contains(neighbor)
		    || !tiles[neighbor.x][neighbor.y][neighbor.z].isGround() && !neighbor.equals(end))
		continue;

	    if(open.contains(neighbor))
		reParentNeighborIfNecessary(closest, neighbor);
	    else reParentNeighbor(closest, neighbor);
	}
    }

    private void reParentNeighbor(Point closest, Point neighbor){
	reParent(neighbor, closest);
	open.add(neighbor);
    }

    private void reParentNeighborIfNecessary(Point closest, Point neighbor){
	Point originalParent = parents.get(neighbor);
	double currentCost = costToGetTo(neighbor);
	reParent(neighbor, closest);
	double reparentCost = costToGetTo(neighbor);

	if(reparentCost < currentCost)
	    open.remove(neighbor);
	else reParent(neighbor, originalParent);
    }

    private ArrayList<Point> createPath(Point start, Point end){
	ArrayList<Point> path = new ArrayList<Point>();

	while(!end.equals(start)){
	    path.add(end);
	    end = parents.get(end);
	}

	Collections.reverse(path);
	return path;
    }

}