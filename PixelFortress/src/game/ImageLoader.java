package game;

import java.util.HashMap;

import jgfe.gfx.Image;
import jgfe.gfx.TileSheet;

public class ImageLoader {
	
	public static HashMap<String,Image> images;
	
	public ImageLoader() {
		images = new HashMap<String,Image>();
		TileSheet human = new TileSheet("/images/human.png",1,2);
		images.put("Human", human.getTileImage(1));
	}

}
