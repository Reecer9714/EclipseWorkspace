package consolegame.game.creatures;

public class Player {
	public int x, y;
	private char chixel;
	
	public Player() {
		x = 0;
		y = 0;
		chixel = '@';
	}

	public char getChixel(){
		return chixel;
	}
}
