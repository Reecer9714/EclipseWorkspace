package willtolive.game;

import java.awt.Color;

import willtolive.game.tiles.*;
import willtolive.game.util.Func;
import willtolive.game.util.SquareMap;

public class Island {
	private final int SMOOTH = 7;
	
	private SquareMap<Terrain> tiles;
	private double[][] elev;
//	private double[][] moist;
	private int size;
	
	public Island(int s){
		size = s;
		tiles = new SquareMap<Terrain>(size);
		generateIsland();
	}
	
	public synchronized void generateIsland(){
		elev = new double[size][size];
//		moist = new double[size][size];
		System.out.println("Randomizing World...");
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				elev[x][y] = Game.ran.nextDouble();
//				moist[x][y] = Game.ran.nextDouble();
			}
		}
		
		smooth(elev,SMOOTH);
//		smooth(moist,SMOOTH-3);
		
		System.out.println("Filling World...");
		for(int y = 0; y < size; y++){
			for(int x = 0; x < size; x++){
				double e = elev[x][y];
//				double m = moist[x][y];
//				Terrain tile;
//				if(e<0.45){
//					tile = new Water(x*32,y*32);
//				}else{
//					tile = new Terrain(x*32,y*32);
//					tile.setColor(setBiome(e));
//				}
//				tiles.set(x,y, tile);
//				tile.setElev(e);
//				tile.setMoist(m);
				
				Terrain tile = null;
				
				if (e < 0.45){
					tile = new Water(x*32,y*32);
				}else if (e >= 0.45 && e < 0.50){
					tile = new Sand(x*32,y*32);
				}else if (e >= 0.50) {
					tile = new Grass(x*32,y*32);
				}
				tiles.set(x,y,tile);
				//tile.setElev(e);
		    }
		}
		System.out.println("World Generated "+size+", "+size);
	}
	
	public double smoothG(int x, int y, double o){
//		e^(-(((x-0.5)^2)/(2*0.2^2)+((y-0.5)^2)/(2*0.2^2)))
		return Math.pow(Math.E, -((Math.pow((x/size-0.5),2)+Math.pow((y/size-0.5),2))/(2*(o*o))));
//		return (1/(2*Math.PI*Math.pow(o, 2.0))) * Math.pow(Math.E,-(Math.pow(x/size-0.5,2)+Math.pow(y/size-0.5,2))/(2*Math.pow(o,2)));
	}
	
	public void smooth(double[][] d, int num){
		for(int n = 0; n<=num; n++){
			System.out.println("Smoothing World "+n);
			for(int y = 0; y < size; y++){
				for(int x = 0; x < size; x++){
					double up = (y==0) ? d[x][y+1]:d[x][y-1];
					double down = (y==size-1) ? d[x][y-1]:d[x][y+1];
					double left = (x==0) ? d[x+1][y]:d[x-1][y];
					double right = (x==size-1) ? d[x-1][y]:d[x+1][y];
					
					double ran = 0.5;
			        double yyy = Func.lerp(up,down,ran);
			        double xxx = Func.lerp(left,right,ran);
			        double replace = Func.lerp(yyy,xxx,ran);
			        
			        replace = Func.clamp(replace,0.0,1.0);
			        d[x][y] = replace;
			        
			        if(n==SMOOTH){
			        	double mod = Func.distanceBetween(size/2, size/2, x, y);
//			        	double mod = smoothG(x,y,0.2);
						double delta = mod/size;
						if(x==0&&y==0)System.out.println("before"+(d[x][y]));
			        	if(x==size/2&&y==size/2)System.out.println("before"+(d[x][y]));
						d[x][y] *= 1-Math.pow(delta, 3);
						if(x==0&&y==0)System.out.println("after "+(d[x][y]));
			        	if(x==size/2&&y==size/2)System.out.println("after "+(d[x][y]));
//						d[x][y] /= Math.max(0.0, 1-delta*delta); Creates a mountain wrap
			        }
	
			    }
			}
		}
	}
	
	public Color setBiome(double elev){
		if(elev >= 0.45 && elev < 0.50){
			return Color.yellow;
		}else if(elev >= 0.50 && elev < 0.55){
			return Color.green;
		}else if(elev >= 0.55){
			return Color.lightGray;
		}
		return null;
	}
	
	public SquareMap<Terrain> getTiles(){
		return tiles;
	}
}