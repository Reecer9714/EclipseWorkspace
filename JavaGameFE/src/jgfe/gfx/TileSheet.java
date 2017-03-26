package jgfe.gfx;

import jgfe.util.Resource;

public class TileSheet extends Resource{

    private int tileW, tileH;
    private Image[] tiles;

    public TileSheet(String path, int tileW, int tileH){
	this.tileW = tileW;
	this.tileH = tileH;
	load(path);
    }

    public Image getTileImage(int id){
	if(id < 0 || id >= tiles.length) return null;
	return tiles[id];
    }

    public int getTilesLength(){
	return tiles.length;
    }

    public int getTileW(){
	return tileW;
    }

    public int getTileH(){
	return tileH;
    }

    public Image[] getTiles(){
	return tiles;
    }

    @Override
    public Resource load(String path){
	
	Image image = new Image(path);

	tiles = new Image[(image.getWidth() / tileW) * (image.getHeight() / tileH)];

	for(int i = 0; i < image.getWidth() / tileW; i++){
	    for(int j = 0; j < image.getHeight() / tileH; j++){
		int[] p = new int[tileW * tileH];

		for(int x = 0; x < tileW; x++){
		    for(int y = 0; y < tileH; y++){
			p[x + y * tileW] = image.pixels[(x + i * tileW) + (y + j * tileH) * image.getWidth()];
		    }
		}

		tiles[i + j * (image.getWidth() / tileW)] = new Image(tileW, tileH, p);
	    }
	}
	return this;
    }
}
