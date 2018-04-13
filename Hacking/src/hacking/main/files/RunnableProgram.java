package hacking.main.files;

public interface RunnableProgram {

	public default void run(){
		// TODO Auto-generated method stub
		
	}
	
	public abstract void handleInput(String cmd, String[] args);
	
}
