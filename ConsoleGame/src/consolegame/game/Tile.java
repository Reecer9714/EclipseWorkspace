package consolegame.game;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public enum Tile
{
    FLOOR('.', Color.LIGHT_GRAY, "A dirt and rock cave floor."),
    WALL('#', AsciiPanel.brightBlack, "A dirt and rock cave wall."),
    STAIRS_DOWN('>', AsciiPanel.white, "A stone staircase that goes down."),
    STAIRS_UP('<', AsciiPanel.white, "A stone staircase that goes up."),
    UNKNOWN(' ', AsciiPanel.black, "?????????"),
    BOUNDS('x', AsciiPanel.brightBlack, "Beyond the edge of the world."), 
    DOOR('+', AsciiPanel.brightBlack, "A way into another room.");

    private char glyph;
    public char glyph(){ return glyph; }

    private Color color;
    public Color color(){ return color; }
    
    private String details;
    public String details(){ return details; }

    Tile(char glyph, Color color, String details){
        this.glyph = glyph;
        this.color = color;
        this.details = details;
    }

    public boolean isGround() {
	return this != WALL && this != BOUNDS;
    }

    public boolean isDiggable() {
        return this == Tile.WALL;
    }
}
