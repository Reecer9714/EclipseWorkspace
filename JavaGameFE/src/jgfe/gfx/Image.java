package jgfe.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import jgfe.util.Resource;
import jgfe.util.ShadowType;

public class Image extends Resource{

    private int width, height;
    public ShadowType shadowType = ShadowType.NONE;
    public int[] pixels;
   
    public Image(String path){
	load(path);
    }
    
    public Image(int w, int h, int[] p){
	this.width = w;
	this.height = h;
	this.pixels = p;
    }

    public int getWidth(){
	return width;
    }

    public void setWidth(int width){
	this.width = width;
    }

    public int getHeight(){
	return height;
    }

    public void setHeight(int height){
	this.height = height;
    }

    @Override
    public Image load(String path){
	BufferedImage image = null;

	try{
	    image = ImageIO.read(Image.class.getResourceAsStream(path));
	}
	catch(IOException e){
	    e.printStackTrace();
	}

	width = image.getWidth();
	height = image.getHeight();
	pixels = image.getRGB(0, 0, width, height, null, 0, width);

	image.flush();
	
	return this;
    }
}