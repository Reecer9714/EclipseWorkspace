package jgfe;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import jgfe.gfx.Camera;

public class Window{

    private JFrame frame;
    private Canvas canvas;
    private BufferedImage image;
    private Graphics g;
    private BufferStrategy bs;
    private Camera cam;
    private float zoom;
    private GameContainer gc;

    public Window(GameContainer gc){
	this.gc = gc;
	//zoom = gc.getScale();
	cam = new Camera((int)(gc.getWidth()), (int)(gc.getHeight()));
	image = new BufferedImage(gc.getWidth(), gc.getHeight(), BufferedImage.TYPE_INT_RGB);

	canvas = new Canvas();
	Dimension s = new Dimension((int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
	canvas.setPreferredSize(s);
	canvas.setMaximumSize(s);
	canvas.setMinimumSize(s);

	frame = new JFrame(gc.getTitle());
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setLayout(new BorderLayout());
	frame.add(canvas, BorderLayout.CENTER);
	frame.pack();
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);
	frame.requestFocus();
	frame.setVisible(true);

	canvas.createBufferStrategy(1);
	bs = canvas.getBufferStrategy();
	g = bs.getDrawGraphics();
    }

    public void render(){
	BufferedImage view = image.getSubimage(cam.getX(), cam.getY(), cam.getWidth(), cam.getHeight());
	g.drawImage(view, 0, 0, canvas.getWidth(), canvas.getHeight(),  canvas);
	//g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), canvas);
	bs.show();
    }
    
    public void renderImage(BufferedImage img){
	g.drawImage(img, 0, 0, canvas.getWidth(), canvas.getHeight(),  canvas);
	bs.show();
    }
    
    public BufferedImage renderBuffer(){
	BufferedImage view = image.getSubimage(cam.getX(), cam.getY(), cam.getWidth(), cam.getHeight());
	return view;
    }

    public void cleanup(){
	g.dispose();
	bs.dispose();
	image.flush();
	frame.dispose();
    }

    public void setZoom(float z){
	zoom = z;
    }

    public float getZoom(){
	return zoom;
    }

    public Canvas getCanvas(){
	return canvas;
    }

    public BufferedImage getImage(){
	return image;
    }

    public Camera getCamera(){
	return cam;
    }

    public void setCamera(Camera cam){
	this.cam = cam;
    }

    public GameContainer getGameContainer(){
	return gc;
    }
}
