package jgfe.gfx;

public class Pixel {
	
	public static int BLACK = 0xff000000;
	public static int WHITE = 0xffffffff;
	public static int RED = 0xffff0000;
	public static int BLUE = 0xff0000ff;
	public static int GREEN = 0xff00ff00;
	
	public static float getAlpha(int color){
		return (0xff & (color >> 24)) / 255f;
	}
	
	public static float getRed(int color){
		return (0xff & (color >> 16)) / 255f;
	}
	
	public static float getGreen(int color){
		return (0xff & (color >> 8)) / 255f;
	}
	
	public static float getBlue(int color){
		return (0xff & (color)) / 255f;
	}
	
	public static int getColor(float a, float r, float g, float b){
		return ((int)(a * 255f + 0.5f) << 24 |
				(int)(r * 255f + 0.5f) << 16 |
				(int)(g * 255f + 0.5f) << 8 |
				(int)(b * 255f + 0.5f) );
	}
	
	public static int getColor(int a, int r, int g, int b){
		return (a << 24 |
				r << 16 |
				g << 8 |
				b );
	}
	
	public static int getColorPower(int color, float power){
		return getColor(1, getRed(color) * power,
						   getGreen(color) * power,
						   getBlue(color) * power);
	}

	public static int getLightBlend(int color, int light, int ambientColor) {
		float r = getRed(light);
		float g = getGreen(light);
		float b = getBlue(light);
		float rr = getRed(ambientColor);
		float gg = getGreen(ambientColor);
		float bb = getBlue(ambientColor);
		
		if(r < rr) r = rr;
		if(g < gg) g = gg;
		if(b < bb) b = bb;
		
		return getColor(1, r * getRed(color), g * getGreen(color), b * getBlue(color));
	}
	
	public static int getMax(int color1, int color2){
		return getColor(1, Math.max(getRed(color1), getRed(color2)),
						   Math.max(getGreen(color1), getGreen(color2)),
						   Math.max(getBlue(color1), getBlue(color2)));
	}
	
	public static int getColor(float r, float g, float b){ return getColor(1.0f,r,g,b);}
	public static int getColor(int r, int g, int b){ return getColor(255,r,g,b);}
}


