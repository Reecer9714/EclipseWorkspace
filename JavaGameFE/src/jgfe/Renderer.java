package jgfe;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import jgfe.gfx.*;
import jgfe.util.ShadowType;

public class Renderer{
    private GameContainer gc;

    private int width, height;
    private int[] pixels;
    private int[] lightMap;
    private ShadowType[] shadowMap;

    private Font font = Font.STANDARD;
    private int ambientColor = Pixel.getColor(0.5f, 0.1f, 0.1f, 0.1f);
    private int voidColor = Pixel.getColor(1, 0, 0, 0);
    private int transColor = Pixel.getColor(1, 1, 0, 1);

    private int transX, transY;
    private boolean translate = true;

    private ArrayList<LightRequest> lightRequests = new ArrayList<LightRequest>();

    public Renderer(GameContainer gc){
	this.gc = gc;
	System.out.println("Starting Renderer...");
	width = gc.getWidth();
	height = gc.getHeight();
	pixels = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
	lightMap = new int[pixels.length];
	shadowMap = new ShadowType[pixels.length];
    }

    public void setPixel(int x, int y, int color){
	setPixel(x, y, color, ShadowType.NONE);
    }

    public void setPixel(int x, int y, int color, ShadowType shadowType){
	x -= transX;
	y -= transY;

	if((x < 0 || x >= width || y < 0 || y >= height) || color == transColor || color == 0x00) return;

	pixels[x + (y * width)] = color;
	shadowMap[x + (y * width)] = shadowType;
    }

    public void setLightMap(int x, int y, int color){
	x -= transX;
	y -= transY;

	if((x < 0 || x >= width || y < 0 || y >= height)) return;

	lightMap[x + (y * width)] = Pixel.getMax(color, lightMap[x + y * width]);

    }

    public ShadowType getLightBlock(int x, int y){
	x -= transX;
	y -= transY;

	if(x < 0 || x >= width || y < 0 || y >= height) return ShadowType.TOTAL;
	return shadowMap[x + y * width];
    }

    public void drawImage(Image image, int offX, int offY){
	for(int x = 0; x < image.getWidth(); x++){
	    for(int y = 0; y < image.getHeight(); y++){
		setPixel(x + offX, y + offY, image.pixels[x + y * image.getWidth()], image.shadowType);
	    }
	}
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY){
	for(int x = 0; x < image.getTileWidth(); x++){
	    for(int y = 0; y < image.getTileHeight(); y++){
		setPixel(x + offX, y + offY, image.pixels[(x + (tileX * image.getTileWidth()))
			+ (y + (tileY * image.getTileHeight())) * image.getWidth()], image.shadowType);
	    }
	}
    }

    public void drawRect(int offX, int offY, int w, int h, int color, ShadowType type){
	for(int x = 0; x <= w; x++){
	    setPixel(x + offX, offY, color, type);
	    setPixel(x + offX, offY + h, color, type);
	}

	for(int y = 0; y <= h; y++){
	    setPixel(offX, y + offY, color, type);
	    setPixel(offX + w, y + offY, color, type);
	}
    }

    public void drawFillRect(int offX, int offY, int w, int h, int color, ShadowType type){
	for(int x = 0; x < w; x++){
	    for(int y = 0; y < h; y++){
		setPixel(x + offX, y + offY, color, type);
	    }
	}
    }

    public void drawString(String text, int color, int offX, int offY, boolean centerX, boolean centerY){
	text = text.toUpperCase();
	int offset = 0;

	if(centerX) offset = (int)(-Font.getStringSize(font, text) / 2f);

	for(int i = 0; i < text.length(); i++){
	    int unicode = text.codePointAt(i) - 32;

	    for(int x = 0; x < font.widths[unicode]; x++){
		for(int y = 1; y < font.image.getHeight(); y++){
		    if(font.image.pixels[(x + font.offsets[unicode]) + y * font.image.getWidth()] == 0xffffffff){
			if(centerY){
			    setPixel(x + offX + offset, y + offY - 1 - (font.image.getHeight() / 2), color,
				    ShadowType.NONE);
			}else{
			    setPixel(x + offX + offset, y + offY - 1, color, ShadowType.NONE);
			}
		    }
		}
	    }

	    offset += font.widths[unicode];
	}
    }

    public void drawLight(Light light, int offX, int offY){
	lightRequests.add(new LightRequest(light, offX, offY));
    }

    private void drawLightRequest(Light light, int offX, int offY){
	if(gc.isDynamicLights()){
	    for(int i = 0; i <= light.diameter; i++){
		drawLightLine(light.radius, light.radius, i, 0, light, offX, offY);
		drawLightLine(light.radius, light.radius, i, light.diameter, light, offX, offY);
		drawLightLine(light.radius, light.radius, 0, i, light, offX, offY);
		drawLightLine(light.radius, light.radius, light.diameter, i, light, offX, offY);
	    }
	}else{
	    for(int x = 0; x < light.diameter; x++){
		for(int y = 0; y < light.diameter; y++){
		    setLightMap(x + offX, y + offY, light.getLightValue(x, y));
		}
	    }
	}
    }

    private void drawLightLine(int x0, int y0, int x1, int y1, Light light, int offX, int offY){
	int dx = Math.abs(x1 - x0);
	int dy = Math.abs(y1 - y0);

	int sx = x0 < x1 ? 1 : -1;
	int sy = y0 < y1 ? 1 : -1;

	int err = dx - dy;
	int e2;

	float power = 1.0f;
	boolean hit = false;

	while(true){
	    if(light.getLightValue(x0, y0) == Pixel.BLACK) break;

	    int screenX = x0 - light.radius + offX;
	    int screenY = y0 - light.radius + offY;

	    if(power == 1){
		setLightMap(screenX, screenY, light.getLightValue(x0, y0));
	    }else{
		setLightMap(screenX, screenY, Pixel.getColorPower(light.getLightValue(x0, y0), power));
	    }

	    if(x0 == x1 && y0 == y1) break;
	    if(getLightBlock(screenX, screenY) == ShadowType.TOTAL) break;
	    if(getLightBlock(screenX, screenY) == ShadowType.FADE) power -= 0.1f;
	    if(getLightBlock(screenX, screenY) == ShadowType.HALF && hit == false) hit = true;
	    if(getLightBlock(screenX, screenY) == ShadowType.NONE && hit == true){
		power /= 2;
		hit = false;
	    }
	    if(power <= 0.1f) break;

	    e2 = 2 * err;

	    if(e2 > -1 * dy){
		err -= dy;
		x0 += sx;
	    }

	    if(e2 < dx){
		err += dx;
		y0 += sy;
	    }
	}
    }

    public void drawLightArray(){
	for(LightRequest lr : lightRequests){
	    drawLightRequest(lr.light, lr.x, lr.y);
	}

	lightRequests.clear();
    }

    public void flushMaps(){
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		if(gc.isDynamicLights()){
		    setPixel(x, y, Pixel.getLightBlend(pixels[x + y * width], lightMap[x + y * width], ambientColor),
			    getLightBlock(x, y));
		}else{
		    setPixel(x, y, Pixel.getLightBlend(pixels[x + y * width], lightMap[x + y * width], ambientColor),
			    ShadowType.NONE);
		}
		lightMap[x + y * width] = ambientColor;
		shadowMap[x + y * width] = ShadowType.NONE;
	    }
	}
    }

    public void clear(){
	for(int x = 0; x < width; x++){
	    for(int y = 0; y < height; y++){
		pixels[x + y * width] = voidColor;
		// lightMap[x + y * width] = ambientColor;
	    }
	}
    }

    public void setTransY(int transY){
	this.transY = transY;
    }

    public void drawImage(Image image){
	drawImage(image, 0, 0);
    }

    public void drawImageTile(ImageTile image, int tileX, int tileY){
	drawImageTile(image, 0, 0, tileX, tileY);
    }

    public void drawString(String text, int color, int offX, int offY){
	drawString(text, color, offX, offY, false, false);
    }

    public void drawLight(Light light){
	drawLight(light, 0, 0);
    }

    public void drawFillRect(int offX, int offY, int w, int h, int color){
	drawFillRect(offX, offY, w, h, color, ShadowType.NONE);
    }
    
    //Get and Sets =======================================
    
    public void setVoidColor(int voidColor){
	this.voidColor = voidColor;
    }

    public int getTransX(){
	return transX;
    }

    public void setTransX(int transX){
	this.transX = transX;
    }

    public int getTransY(){
	return transY;
    }
    
    public Font getFont(){
	return font;
    }

    public void setFont(Font font){
	this.font = font;
    }

    public int getAmbientColor(){
	return ambientColor;
    }

    public void setAmbientColor(int ambientColor){
	this.ambientColor = ambientColor;
    }

    public int getTransColor(){
	return transColor;
    }

    public void setTransColor(int transColor){
	this.transColor = transColor;
    }

    public int getVoidColor(){
	return voidColor;
    }

    public boolean isTranslate(){
	return translate;
    }

    public void setTranslate(boolean translate){
	this.translate = translate;
    }

    public void drawGUIString(String string, int color, int x, int y){
	drawString(string, color, x - transX, y - transY);
    }
}
