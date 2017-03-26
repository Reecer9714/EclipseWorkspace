package game;

import jgfe.gfx.Pixel;

public enum ObjectColor {
	
	WATER(Pixel.getColor(0, 162, 232)), SAND(Pixel.getColor(239, 228, 176)), GRASS(Pixel.getColor(34, 177, 76)), PLAYER(Pixel.getColor(255, 100, 100)),
	STONE(Pixel.getColor(100,100,100)), OCEAN(Pixel.getColor(63, 73, 204)), DEBUG(Pixel.getColor(155, 20, 86));
	
	int color;
	ObjectColor(int c){
		color = c;
	}
}
