package jgfe.util;

import java.util.*;

import jgfe.gfx.*;

public class ResourceLoader{
    public static final int IMAGE = 0;
    public static final int IMAGE_TILE = 1;
    public static final int TILE_SHEET = 2;
    public static final int SOUND_CLIP = 3;
    public static final int ANIMATION = 4;// not usable yet

    private Stack<ResourceRequest> toBeLoaded = new Stack<ResourceRequest>();
    private static Map<String, Resource> resources = new HashMap<String, Resource>();

    public void loadResource(String name, String file, int resourceType){
	toBeLoaded.add(new ResourceRequest(name, file, resourceType));
    }

    public void loadFolder(String folder, int resourceType){
	// loadResource for all the files in folder
    }

    public Image loadImage(String name, String file){
	Image img = new Image(file);
	img.name = name;
	resources.put(name, img);
	return img;
    }

    public ImageTile loadImageTile(String name, String file){
	Image img = new Image(file);
	ImageTile it = new ImageTile(img, img.getWidth(), img.getHeight());
	it.name = name;
	resources.put(name, it);
	return it;
    }

    public TileSheet loadTileSheet(String name, String file, int tileW, int tileH){
	TileSheet ts = new TileSheet(file, tileW, tileH);
	ts.name = name;
	resources.put(name, ts);
	return ts;
    }

    public SoundClip loadSoundClip(String name, String file){
	SoundClip sc = new SoundClip(file);
	sc.name = name;
	resources.put(name, sc);
	return sc;
    }

    // Font
    // Animation

    public static Map<String, Resource> getResources(){
	return resources;
    }

    protected Map<String, Resource> load(){
	while(!toBeLoaded.isEmpty()){

	    // Resource r = new Resource();
	}
	return resources;
    }

}
