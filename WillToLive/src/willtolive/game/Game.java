package willtolive.game;

import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

import willtolive.game.objects.Player;
import willtolive.game.util.ImageLoader;
import willtolive.game.util.MouseHandler;

public class Game extends JFrame implements Runnable{
    private static final long serialVersionUID = 1L;
    public static final int TILE_SIZE = 32;
    public static final boolean DEBUG = true;

    public static Game game;
    private static World world;
    private static View view;
    private static Input input;
    private static MouseHandler mouse;
    public static Player p;
    public static Debug bug;
    private static Thread gameThread;

    public static Random ran = new Random();

    private static int height = 680;
    private static int width = (height * 16) / 9;
    private boolean running = false;

    private boolean lockFrameRate = true;

    private static double frameCap = 1.0 / 60.0;
    private int fps;
    private double delta;

    public Game(){
	game = this;
	setTitle("Will to Live");
	setSize(width, height);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	System.out.println("Initializing Game");
	new ImageLoader();

	if(DEBUG) ran.setSeed(1L);

	world = new World(this);

	p = new Player(World.getWorldWidth() / 2, World.getWorldHeight() / 2);
	World.addObject(p);

	view = new View(p.x - width / 2, p.y - height / 2, width, height);

	input = new Input(this);

	add(world);

	init();
    }

    public void init(){
	if(DEBUG) bug = new Debug(this);
    }

    public synchronized void start(){
	if(running) return;
	running = true;
	gameThread = new Thread(this, "Game");
	gameThread.start();
    }

    public void run(){
	System.out.println("Running Game");
	requestFocus();

	double firstTime = 0;
	double lastTime = System.nanoTime() / 1000000000.0;
	double passedTime = 0;
	double unproccessedTime = 0;
	double frameTime = 0;
	int frames = 0;

	while(running){
	    boolean render = !lockFrameRate;// True is off

	    firstTime = System.nanoTime() / 1000000000.0;
	    passedTime = firstTime - lastTime;
	    lastTime = firstTime;

	    unproccessedTime += passedTime;
	    frameTime += passedTime;
	    delta = passedTime;

	    while(unproccessedTime >= frameCap){
		update();
		// input.update();
		unproccessedTime -= frameCap;
		render = true;

		if(frameTime >= 1){
		    frameTime = 0;
		    fps = frames;
		    frames = 0;
		}
	    }

	    if(render){
		world.repaint();
		frames++;
	    }else{
		try{
		    Thread.sleep(1);
		}
		catch(InterruptedException e){
		    e.printStackTrace();
		}
	    }
	}
    }

    public synchronized void update(){
	// updates++;
	input.update();
	if(input.isKey(KeyEvent.VK_W)){
	    p.speed = 4;
	}else if(input.isKey(KeyEvent.VK_S)){
	    p.speed = -2;
	}else{
	    p.speed = 0;
	}

	if(input.isKey(KeyEvent.VK_A)) p.getMovement().setXComp(-p.getSpeed());
	if(input.isKey(KeyEvent.VK_D)) p.getMovement().setXComp(p.getSpeed());
	// p.setMovement(p.getMovement().scale(p.getSpeed()));
	p.update();
	p.getMovement().setXComp(0);
	p.getMovement().setYComp(0);
	// Entities update
	view.update(p);

    }

    public static World getWorld(){
	return world;
    }

    public View getView(){
	return view;
    }

    public Player getPlayer(){
	return p;
    }

    public static MouseHandler getMouse(){
	return mouse;
    }

    public static void main(String[] args){
	Game game = new Game();
	game.setVisible(true);
	game.start();
    }

    public static Input getInput(){
	return input;
    }

    public int getFPS(){
	return fps;
    }

    public double getDelta(){
	return delta;
    }

}
