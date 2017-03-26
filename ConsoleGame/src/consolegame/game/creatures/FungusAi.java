package consolegame.game.creatures;

import consolegame.game.GameObject;

public class FungusAi extends CreatureAi {
	private CreatureFactory factory;
	private int spreadcount;

	public FungusAi(Creature creature, CreatureFactory creatureFactory) {
		super(creature);
		factory = creatureFactory;
	}

	public void onUpdate() {
		if (spreadcount < 5 && Math.random() < 0.005)
			spread();
	}

	private void spread() {
		int x = creature.x + (int) (Math.random() * 11) - 5;
		int y = creature.y + (int) (Math.random() * 11) - 5;

		if (!creature.canEnter(x, y, creature.z))
			return;

		GameObject child = factory.newFungus(creature.z);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadcount++;
	}
}
