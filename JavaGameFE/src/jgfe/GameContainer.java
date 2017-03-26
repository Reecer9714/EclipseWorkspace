package jgfe;

import java.util.ArrayList;
import java.util.List;

import jgfe.gfx.Pixel;

public class GameContainer implements Runnable{
    private Thread thread;
    private AbstractGame game;
    private Window window;
    private Renderer renderer;
    private Input input;

    private int width = 320, height = 240;
    private float scale = 2.0f;
    private String title = "JavaGame Framework and Engine  0.1";

    private boolean lockFrameRate = false;
    private boolean lightingEnabled = false;
    private boolean dynamicLights = false;
    private boolean clearScreen = false;
    private boolean debug = false;

    private double frameCap = 1.0 / 60.0;
    private int fps;
    private boolean isRunning = false;

    private List<AbstractGameState> gameStates = new ArrayList<AbstractGameState>();

    public GameContainer(AbstractGame game){
	this.game = game;
    }

    public GameContainer setGameState(AbstractGameState gs){
	this.game = gs;
	return this;
    }

    public void start(){
	if(isRunning) return;
	if(debug) System.out.println("Starting Game...");
	window = new Window(this);
	renderer = new Renderer(this);
	input = new Input(this);

	thread = new Thread(this);
	thread.run();
    }

    public void stop(){
	if(!isRunning) return;
	if(debug) System.out.println("Stopping Game...");
	isRunning = false;
    }

    public void run(){
	isRunning = true;
	if(debug) System.out.println("Running Game...");
	double firstTime = 0;
	double lastTime = System.nanoTime() / 1000000000.0;
	double passedTime = 0;
	double unproccessedTime = 0;
	double frameTime = 0;
	int frames = 0;

	game.init(this);

	while(isRunning){
	    boolean render = !lockFrameRate;// FrameLock to frameCap: true is
					    // off

	    firstTime = System.nanoTime() / 1000000000.0;
	    passedTime = firstTime - lastTime;
	    lastTime = firstTime;

	    unproccessedTime += passedTime;
	    frameTime += passedTime;

	    while(unproccessedTime >= frameCap){
		game.update(this, (float)frameCap);
		input.update();
		window.getCamera().update((float)frameCap);
		unproccessedTime -= frameCap;
		render = true;

		if(frameTime >= 1){
		    frameTime = 0;
		    fps = frames;
		    frames = 0;
		}
	    }

	    if(render){
		if(clearScreen) renderer.clear();
		game.render(this, renderer);

		if(lightingEnabled || dynamicLights){
		    renderer.drawLightArray();
		    renderer.flushMaps();
		}
		renderer.setTranslate(false);
		if(debug) renderer.drawGUIString("FPS: " + fps, Pixel.WHITE, 0, 0);
		renderer.setTranslate(false);

		window.update();
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

	cleanup();
    }

    public void cleanup(){
	if(debug) System.out.println("Cleaning Game...");
	window.cleanup();
    }

    // Inner protected functions ------------------

    protected List<AbstractGameState> getGameStates(){
	return gameStates;
    }

    // Gets and Sets -----------------------------

    public int getWidth(){
	return width;
    }

    public GameContainer setWidth(int width){
	this.width = width;
	return this;
    }

    public int getHeight(){
	return height;
    }

    public GameContainer setHeight(int height){
	this.height = height;
	return this;
    }

    public float getScale(){
	return scale;
    }

    public GameContainer setScale(float scale){
	this.scale = scale;
	return this;
    }

    public String getTitle(){
	return title;
    }

    public GameContainer setTitle(String title){
	this.title = title;
	return this;
    }

    public GameContainer setVoidColor(int voidColor){
	renderer.setVoidColor(voidColor);
	return this;
    }

    public Window getWindow(){
	return window;
    }

    public boolean isDynamicLights(){
	return dynamicLights;
    }

    public GameContainer setDynamicLights(boolean dynamicLights){
	this.dynamicLights = dynamicLights;
	return this;
    }

    public boolean isLightingEnabled(){
	return lightingEnabled;
    }

    public GameContainer setLightingEnabled(boolean lightingEnabled){
	this.lightingEnabled = lightingEnabled;
	return this;
    }

    public boolean isClearScreen(){
	return clearScreen;
    }

    public GameContainer setClearScreen(boolean clearScreen){
	this.clearScreen = clearScreen;
	return this;
    }

    public double getFrameCap(){
	return frameCap;
    }

    public GameContainer setFrameCap(double cap){
	frameCap = 1.0 / cap;
	return this;
    }

    public boolean isDebug(){
	return debug;
    }

    public GameContainer setDebug(boolean debug){
	this.debug = debug;
	return this;
    }

    public AbstractGame getGame(){
	return game;
    }

    public GameContainer setGame(AbstractGame game){
	this.game = game;
	return this;
    }

    public Input getInput(){
	return input;
    }

    public GameContainer setInput(Input input){
	this.input = input;
	return this;
    }

    public boolean isLockFrameRate(){
	return lockFrameRate;
    }

    public GameContainer setLockFrameRate(boolean lockFrameRate){
	this.lockFrameRate = lockFrameRate;
	return this;
    }

    public int getFps(){
	return fps;
    }

    public GameContainer setWindow(Window window){
	this.window = window;
	return this;
    }

    public Renderer getRenderer(){
	return renderer;
    }

}
