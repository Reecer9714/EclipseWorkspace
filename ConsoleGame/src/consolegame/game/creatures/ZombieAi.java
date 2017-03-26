package consolegame.game.creatures;

import consolegame.game.GameObject;

public class ZombieAi extends CreatureAi {
	private GameObject player;
	
	public ZombieAi(Creature creature, GameObject player){
		super(creature);
		this.player = player;
	}
	
	@Override
	public void onUpdate(){
		if(Math.random() < 0.2)
			return;
		
		if(creature.canSee(player.x, player.y, player.z))
			hunt(player);
		else
			wander();
	}
}
