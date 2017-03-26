package jgfe.gfx;

public enum Font {

    STANDARD("/Fonts/standard.png");

    public final int NUM_UNICODES = 59;// Number of char in the image
    public int[] offsets = new int[NUM_UNICODES];
    public int[] widths = new int[NUM_UNICODES];
    public Image image;

    Font(String path){
	image = new Image(path);

	int unicode = -1;

	for(int x = 0; x < image.getWidth(); x++){
	    int color = image.pixels[x];

	    if(color == 0xff0000ff){
		unicode++;
		offsets[unicode] = x;
	    }

	    if(color == 0xffffff00){
		widths[unicode] = x - offsets[unicode];
	    }
	}
    }

    public static int getStringSize(Font font, String text){
	text = text.toUpperCase();

	int offset = 0;
	for(int i = 0; i < text.length(); i++){
	    int unicode = text.codePointAt(i) - 32;
	    offset += font.widths[unicode];
	}
	return offset;
    }
}
