package willtolive.game.util;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private int num;
	private BufferedImage[] images;
	
	public SpriteSheet(BufferedImage i, int w, int h) {
		num = 0;
		for(int y = 0; y < i.getHeight(); y+=h){
			for(int x = 0; x < i.getWidth(); x+=w){
				images[num] = i.getSubimage(x, y, w, h);
				num++;
			}
		}
	}
	
	public SpriteSheet(BufferedImage i, int w, int h, int r, int c) {
		num = 0;
		for(int y = 0; y < r*h; y+=h){
			for(int x = 0; x < c*w; x+=w){
				images[num] = i.getSubimage(x, y, w, h);
				num++;
			}
		}
	}
	
	public SpriteSheet(BufferedImage[] i){
		images = i;
		num = i.length;
	}
	
	public int getSize(){
		return num;
	}
	
	public void addImage(BufferedImage i){
		images[num] = i;
		num++;
	}
	
	public BufferedImage getImage(int i){
		return images[i];
	}

}
