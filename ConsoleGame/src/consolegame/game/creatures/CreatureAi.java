package consolegame.game.creatures;

import java.util.List;

import consolegame.game.*;
import consolegame.game.items.Item;

public class CreatureAi {
	protected Creature creature;

	public CreatureAi(Creature creature) {
		this.creature = creature;
		this.creature.setCreatureAi(this);
	}

	public void wander() {
		int mx = (int) (Math.random() * 3) - 1;
		int my = (int) (Math.random() * 3) - 1;

		GameObject other = creature.creature(creature.x + mx, creature.y + my, creature.z);

		if (other != null && other.glyph() == creature.glyph())
			return;
		else
			creature.moveBy(mx, my, 0);
	}

	public void hunt(GameObject target) {
		List<Point> points = new Path(creature, target.x, target.y).points();

		int mx = points.get(0).x - creature.x;
		int my = points.get(0).y - creature.y;

		creature.moveBy(mx, my, 0);
	}

	public boolean canSee(int wx, int wy, int wz) {
		if (creature.z != wz)
			return false;

		if ((creature.x - wx) * (creature.x - wx) + (creature.y - wy) * (creature.y - wy) > creature.stats.visionValue
				* creature.stats.visionValue)
			return false;

		for (Point p : new Line(creature.x, creature.y, wx, wy)) {
			if (creature.realTile(p.x, p.y, wz).isGround() || p.x == wx && p.y == wy)
				continue;

			return false;
		}

		return true;
	}

	public void onEnter(int x, int y, int z, Tile tile) {
		if (tile.isGround()) {
			creature.x = x;
			creature.y = y;
			creature.z = z;
		} else {
			creature.doAction("bump into a wall");
		}

	}

	public boolean canRangedWeaponAttack(GameObject other) {
		return creature.weapon() != null && creature.weapon().stats.rangedAttackValue > 0
				&& creature.canSee(other.x, other.y, other.z);
	}

	public boolean canThrowAt(GameObject other) {
		return creature.canSee(other.x, other.y, other.z) && getWeaponToThrow() != null;
	}

	protected Item getWeaponToThrow() {
		Item toThrow = null;

		for (Item item : creature.inventory().getItems()) {
			if (item == null || creature.weapon() == item || creature.armor() == item)
				continue;

			if (toThrow == null || item.stats.thrownAttackValue > toThrow.stats.attackValue)
				toThrow = item;
		}

		return toThrow;
	}

	public boolean canPickup() {
		return creature.item(creature.x, creature.y, creature.z) != null && !creature.inventory().isFull();
	}

	protected boolean canUseBetterEquipment() {
		int currentWeaponRating = creature.weapon() == null ? 0
				: creature.weapon().stats.attackValue + creature.weapon().stats.rangedAttackValue;
		int currentArmorRating = creature.armor() == null ? 0 : creature.armor().stats.defenseValue;

		for (Item item : creature.inventory().getItems()) {
			if (item == null)
				continue;

			boolean isArmor = item.stats.attackValue + item.stats.rangedAttackValue < item.stats.defenseValue;

			if (item.stats.attackValue + item.stats.rangedAttackValue > currentWeaponRating
					|| isArmor && item.stats.defenseValue > currentArmorRating)
				return true;
		}

		return false;
	}

	protected void useBetterEquipment() {
		int currentWeaponRating = creature.weapon() == null ? 0
				: creature.weapon().stats.attackValue + creature.weapon().stats.rangedAttackValue;
		int currentArmorRating = creature.armor() == null ? 0 : creature.armor().stats.defenseValue;

		for (Item item : creature.inventory().getItems()) {
			if (item == null)
				continue;

			boolean isArmor = item.stats.attackValue + item.stats.rangedAttackValue < item.stats.defenseValue;

			if (item.stats.attackValue + item.stats.rangedAttackValue > currentWeaponRating
					|| isArmor && item.stats.defenseValue > currentArmorRating) {
				creature.equip(item);
			}
		}
	}

	public void onUpdate() {
	}

	public void onNotify(String message) {
	}

	public void onGainLevel() {
		new LevelUpController().autoLevelUp(creature);
	}

	public Tile rememberedTile(int wx, int wy, int wz) {
		return Tile.UNKNOWN;
	}

}
