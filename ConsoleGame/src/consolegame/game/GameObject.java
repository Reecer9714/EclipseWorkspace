package consolegame.game;

import java.awt.Color;

public class GameObject{
    public int x;
    public int y;
    public int z;

    protected String name;
    public String name(){return name;}
    public void name(String name){
	this.name = name;
    }
    
    protected char glyph;
    public char glyph(){return glyph;}
    
    protected Color color;
    public Color color(){return color;}
    
    public Stats stats;
    
    public GameObject(String name, char glyph, Color color){
	this.name = name;
	this.glyph = glyph;
	this.color = color;
	stats = new Stats();
    }

    public String details(){
	return "";
    }

}