package jgfe.gfx;

import jgfe.entity.GameObject;
import jgfe.GameContainer;

public class Camera{
    float offX, offY;
    int camX, camY;
    int camWidth, camHeight;
    private GameObject target;

    public Camera(int w, int h){
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
    
    public float getOffX(){
        return offX;
    }

    public void setOffX(float offX){
        this.offX = offX;
    }

    public float getOffY(){
        return offY;
    }

    public void setOffY(float offY){
        this.offY = offY;
    }

    public void update(GameContainer gc, float delta){
	
	float targetX;
	float targetY;
	
	if(target != null){
	    targetX = (target.getX() - camWidth /2);
	    targetY = (target.getY() - camHeight /2);
	}else{
	    targetX = (camX + camWidth /2) - gc.getWidth() / 2;
	    targetY = (camY + camHeight /2) - gc.getHeight() / 2;
	}
	
	offX -= delta * (offX - targetX);
	offY -= delta * (offY - targetY);
	gc.getRenderer().setTransX((int)offX);
	gc.getRenderer().setTransY((int)offY);
    }
    
    public String toString(){
	return camX + ", " + camY + " : " + camWidth + ", " + camHeight;
    }

    public GameObject getTarget(){
        return target;
    }

    public void setTarget(GameObject target){
        this.target = target;
    }

}
