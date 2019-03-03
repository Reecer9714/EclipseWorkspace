package hacking.terminal;

public interface TerminalListener{
	
	public void onEnter();
	
	public void onBack();
	
	public void onTab();
	
	public void onUp();
	
	public void onDown();
	
	public void onLeft();
	
	public void onRight();
	
	public void onKey(char c);
	
}
