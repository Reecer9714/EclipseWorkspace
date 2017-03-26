package willtolive.game.util;

import java.lang.Math;

public class Vector2D {
	
	public static Vector2D zero = new Vector2D(1,0);
	private double compX;
	private double compY;
	private double mag;
	private double theta;
	
	// Constructor methods ....
	public Vector2D() {
		this(0,0);
	}
	
	public static Vector2D Vector2DComp(double compX, double compY){
		double m = Math.sqrt(Func.sqr(compX) + Func.sqr(compY));
		double d = Math.atan2(compY,compX);
		return new Vector2D(m,d);
	}
	/**
	 * 
	 * @param mag
	 * @param dir in radians
	 */
	public Vector2D(double mag, double dir){
		this.mag = mag;
		this.theta = dir;
		
		compX = mag * Math.cos(dir);
		compY = mag * Math.sin(dir);
	}

	// Convert vector to a string ...
	public String toString() {
		return "Vector2D<" + compX + ", " + compY + "> :" + mag + " direction of " + Math.toDegrees(theta);
	}

	// Get and Set ....
	public double getMag() {
		return Math.sqrt(Func.sqr(compX) + Func.sqr(compY));
	}

	public void setMag(double mag) {
		this.mag = mag;
		compX = mag*Math.cos(theta);
		compY = mag*Math.sin(theta);
	}	

	public double getDir() {
		return Math.atan2(compY,compX);
	}

	public void setDir(double theta) {
		this.theta = theta;
		compX = mag*Math.cos(theta);
		compY = mag*Math.sin(theta);
	}
	
	public double getXComp(){
		return compX;
	}
	public double getYComp(){
		return compY;
	}
	
	public void setXComp(double compX){
		this.compX = compX;
		mag = Math.sqrt(Func.sqr(compX) + Func.sqr(compY));
		theta = Math.atan2(compY,compX);
	}
	public void setYComp(double compY){
		this.compY = compY;
		mag = Math.sqrt(Func.sqr(compX) + Func.sqr(compY));
		theta = Math.atan2(compY,compX);
	}
	// Sum of two vectors ....

	public Vector2D add(Vector2D v1) {
		Vector2D v2 = new Vector2D(this.compX + v1.getXComp(), this.compY + v1.getYComp());
		return v2;
	}

	// Subtract vector v1 from v .....
	public Vector2D sub(Vector2D v1) {
		Vector2D v2 = new Vector2D(compX - v1.getXComp(), compY - v1.getYComp());
		return v2;
	}
	
	// Scale vector by a constant ...
	public Vector2D scale(double scaleFactor) {
		return Vector2DComp(compX * scaleFactor, compY * scaleFactor);
	}

	// Normalize a vectors length....
	public Vector2D normalize() {
		return scale(1/mag);
	}

	// Dot product of two vectors .....
	public double dotProduct(Vector2D v1) {
		return (compX * v1.getXComp()) + (compY * v1.getYComp());
	}
	
	public double getAngleBetween(Vector2D v){
		if(mag == 0 || v.getMag() == 0) return 0.0;
		return Math.acos((dotProduct(v))/(mag*v.getMag()));
	}
	
}