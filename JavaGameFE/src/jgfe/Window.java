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
	zoom = gc.getScale();
	cam = new Camera(this, (int)(gc.getWidth() * gc.getScale()), (int)(gc.getHeight() * gc.getScale()));
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

    public void update(){
	// g.translate(cam.getX(), cam.getY());
	// g.setClip(cam.getX(), cam.getY(), cam.getWidth(), cam.getHeight());
	// g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(),
	// canvas);
	// g.translate(-cam.getX(), -cam.getY());
	// BufferedImage clip = image.getSubimage(cam.getX(), cam.getY(),
	// cam.getWidth(), cam.getHeight());
	g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), cam.getX(), cam.getY(), cam.getWidth(), cam.getHeight(), canvas);
	bs.show();
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
