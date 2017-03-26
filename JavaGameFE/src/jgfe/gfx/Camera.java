package jgfe.gfx;

import jgfe.Window;

public class Camera{

    int camX, camY;
    int camWidth, camHeight;
    Window window;

    public Camera(Window window){
	this(window,640,480);
    }

    public Camera(Window window, int w, int h){
	this.window = window;
	camX = 0;
	camY = 0;
	camWidth = w;
	camHeight = h;
    }
    
    public void centerOn(int x, int y){
	camX = x - (camWidth / 2);
	camY = y - (camHeight / 2);
    }

    public int getX(){
	return camX;
    }
    
    public int getCenterX(){
	return camX + (camWidth / 2);
    }

    public void setX(int camX){
	this.camX = camX;
    }

    public int getY(){
	return camY;
    }
    
    public int getCenterY(){
	return camY + (camHeight / 2);
    }

    public void setY(int camY){
	this.camY = camY;
    }

    public int getWidth(){
	return camWidth;
    }

    public void setWidth(int camWidth){
	this.camWidth = camWidth;
    }

    public int getHeight(){
	return camHeight;
    }

    public void setHeight(int camHeight){
	this.camHeight = camHeight;
    }

    public void update(float delta){
	window.getGameContainer().getRenderer().setTransX((int)(camX *delta));
	window.getGameContainer().getRenderer().setTransY((int)(camY *delta));
    }

}
