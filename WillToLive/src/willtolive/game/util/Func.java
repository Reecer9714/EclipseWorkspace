package willtolive.game.util;

import willtolive.game.Game;
import willtolive.game.objects.Entity;

public class Func {
	
	public static void main(String[] args){
		if(clamp(16,0,10)!=10)System.out.println("Clamp Failed on Upper end");
		if(clamp(-2,0,10)!=0)System.out.println("Clamp Failed on Lower end");
		if(lerp(0,1,0.5)!=0.5)System.out.println("Lerp Failed");
		if(lerp(0,1,1)!=1.0)System.out.println("Leap Failed");
		System.out.println("Finished Func class Test");
	}
	
	public static double clamp(double replace, double i, double j) {
		if(replace<=i){
			return i;
		}else if(replace>=j){
			return j;
		}
		return replace;
	}

	public static double lerp(double x, double y, double a) {
		double smaller = (x>y) ? y:x;
		double bigger = (x>y) ? x:y;
		return (1-a)*smaller + a*bigger;
		//return (smaller + a*(bigger - smaller));
	}
	
	public static int randomRange(int sml, int bgr){
		int smaller = (sml>bgr) ? bgr:sml;
		int bigger = (sml>bgr) ? sml:bgr;
		return Game.ran.nextInt(bigger)+smaller;
	}
	
	public static double distanceBetween(int x1, int y1, int x2, int y2){
		return Math.sqrt(sqr(x2-x1)+sqr(y2-y1));
	}
	
	public static double pointDirection(int x1, int y1, int x2, int y2) {
		 return Math.atan2(y2-y1, x2-x1);
	}
	
	public static double sqr(double d){
		return d*d;
	}
	
	//Once movement vectorized will be easier
	public static void stepTowards(Entity i, Entity j, int spd){
		stepTowards(i,j.x,j.y,spd);
	}
	
	public static void stepTowards(Entity i, int x, int y, int spd){
		i.x = (i.x+(x-i.y)/spd);
		i.y = (i.x+(y-i.y)/spd);
	}
	
	public static double tileToPixel(int x){
		return x*32;
	}
	
	public static int pixelToTile(double x){
		return (int)(x/32);
	}
}
