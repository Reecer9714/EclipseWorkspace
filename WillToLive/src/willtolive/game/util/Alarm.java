package willtolive.game.util;

public abstract class Alarm {
	private int counter;
	private int goal;
	
	public Alarm(int g) {
		counter = 0;
		goal = g;
	}
	
	public abstract void tick();
	
	public void update(){
		counter++;
		if(counter == goal){
			counter = 0;
			tick();
		}
	}
}
