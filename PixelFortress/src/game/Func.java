package game;

import java.util.Random;

public class Func {
	static Random ran = new Random();
	
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
		return ran.nextInt(bigger)+smaller;
	}
	
	public static double distanceBetween(int x, int y, int xx, int yy){
		return Math.sqrt(sqr(xx-x)+sqr(yy-y));
	}
	
	public static double pointDirection(int x1, int y1, int x2, int y2) {
		 double dir1, dir2, dist;
		 dist=distanceBetween(x1,y1,x2,y2); //Have to get the radius first
		 dir1=Math.acos((x2-x1)/dist) * (180.0/Math.PI); //X-derrived angle
		 dir2=Math.asin((y1-y2)/dist) * (180.0/Math.PI); //Y-derrived angle
		 //Combine the two (from [0,180] and [-90,90] to [0,360])
		 if (dir2<0) { return dir1-(2*dir2); }
		 else { return dir1; }
	}
	
	public static double sqr(double d){
		return d*d;
	}
	
	public static void stepTowards(Entity i, int x, int y, int spd){
		i.setX(i.getX()+(x-i.getX())/spd);
		i.setY(i.getX()+(y-i.getY())/spd);
	}
	
	public static double tileToPixel(int x){
		return x*32;
	}
	
	public static int pixelToTile(double x){
		return (int)(x/32);
	}
}
