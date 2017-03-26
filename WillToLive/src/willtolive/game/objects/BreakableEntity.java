package willtolive.game.objects;

import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public abstract class BreakableEntity extends Entity{
	private int hp;
	
	public BreakableEntity(int x, int y, BufferedImage s, int d, int hp) {
		super(x, y, s, d);
		this.hp = hp;
	}

	public abstract void onBreak();

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}
	
	public void leftClicked(MouseEvent e) {
			System.out.println("Breakable left Clicked");
			if(hp>0){
				hp--;
			}else{
				onBreak();
			}
	}	
}
