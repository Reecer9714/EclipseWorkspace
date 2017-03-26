package willtolive.game.tiles;

import java.awt.image.BufferedImage;

import willtolive.game.objects.GameObject;

public class Terrain extends GameObject{
	private double elev = 0.0;
	private double moist;
	
	public Terrain(int x, int y) {
		this(x, y, null);
	}
	
	public Terrain(int x, int y, BufferedImage s) {
		super(x, y);
	}
	
	public double getElev() {
		return elev;
	}

	public void setElev(double elev) {
		this.elev = elev;
	}
	
	public double getMoist() {
		return moist;
	}

	public void setMoist(double moist) {
		this.moist = moist;
	}
	
}