package willtolive.game.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageLoader{
	public static BufferedImage grass, sand, water, tree, rock;
	private static BufferedImage player;
	
	public ImageLoader(){
		loadImages();
	}
	
	public void loadImages(){
		grass = getImage("grass.png");
		sand = getImage("sand.png");
		water = getImage("water.png");
		tree = getImage("tree.png");
		player = getImage("player.png");
		System.out.println("Images Loaded");
	}
	
	private BufferedImage getImage(String filename) {
		try {
	        InputStream in = this.getClass().getResourceAsStream("/willtolive/images/"+filename);
		    return ImageIO.read(in);
		} catch (IOException e) {
		    System.out.println("The image was not loaded.");
		}
		    return null;
	}
	
	public static BufferedImage getPlayer() {
		return player;
	}
	
}
