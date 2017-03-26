package consolegame.game.creatures;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import consolegame.game.*;
import consolegame.game.items.Inventory;
import consolegame.game.items.Item;

public class Creature extends GameObject{
    private World world;

    public CreatureStats stats;

    private Inventory inventory;
    public Inventory inventory(){
	return inventory;
    }

    private Item weapon;
    public Item weapon(){
	return weapon;
    }

    private Item armor;
    public Item armor(){
	return armor;
    }

    private List<Effect> effects;

    public List<Effect> effects(){
	return effects;
    }

    public Creature(World world, String name, char glyph, Color color, int maxHp, int attack, int defense, int vision,
	    int invSize){
	super(name, glyph, color);
	this.world = world;
	stats = new CreatureStats(world, this, maxHp, vision, attack, defense);
	effects = new ArrayList<Effect>();
	inventory = new Inventory(invSize);
	
    }

    public void update(){
	if(hunger() == "Full" && Math.random() < 0.01) regenerateHealth();
	stats.modifyFood(-1);
	regenerateMana();
	ai.onUpdate();
	updateEffects();
    }

    private void updateEffects(){
	List<Effect> done = new ArrayList<Effect>();

	for(Effect effect : effects){
	    effect.update(this);
	    if(effect.isDone()){
		effect.end(this);
		done.add(effect);
	    }
	}

	effects.removeAll(done);
    }

    public void notify(String message, Object... params){
	ai.onNotify(String.format(message, params));
    }

    private CreatureAi ai;
    public CreatureAi ai(){return ai;}
    public void setCreatureAi(CreatureAi ai){
	this.ai = ai;
    }

    private void regenerateHealth(){
	stats.regenHpCooldown -= stats.regenHpPer1000;
	if(stats.regenHpCooldown < 0){
	    stats.modifyHp(1);
	    stats.modifyFood(-1);
	    stats.regenHpCooldown += 1000;
	}
    }

    private void regenerateMana(){
	stats.regenManaCooldown -= stats.regenManaPer1000;
	if(stats.regenManaCooldown < 0){
	    if(stats.mana < stats.maxMana){
		stats.modifyMana(1);
		stats.modifyFood(-1);
	    }
	    stats.regenManaCooldown += 1000;
	}
    }

    // actions
    public void doAction(String message, Object... params){
	int r = 9;
	for(int ox = -r; ox < r + 1; ox++){
	    for(int oy = -r; oy < r + 1; oy++){
		if(ox * ox + oy * oy > r * r) continue;

		Creature other = world.creature(x + ox, y + oy, z);

		if(other == null) continue;

		if(other == this)
		    other.notify("You " + message + ".", params);
		else if(other.canSee(x, y, z))
		    other.notify(String.format("The '%s' %s.", name, makeSecondPerson(message)), params);
	    }
	}
    }

    public void moveBy(int mx, int my, int mz){
	if(mx == 0 && my == 0 && mz == 0) return;

	Tile tile = world.tile(x + mx, y + my, z + mz);

	if(mz == -1){
	    if(tile == Tile.STAIRS_DOWN){
		doAction("walk up the stairs to level %d", z + mz + 1);
	    }else{
		doAction("try to go up but are stopped by the cave ceiling");
		return;
	    }
	}else if(mz == 1){
	    if(tile == Tile.STAIRS_UP){
		doAction("walk down the stairs to level %d", z + mz + 1);
	    }else{
		doAction("try to go down but are stopped by the cave floor");
		return;
	    }
	}

	Creature other = world.creature(x + mx, y + my, z + mz);

	if(other == null)
	    ai.onEnter(x + mx, y + my, z + mz, tile);
	else meleeAttack(other);
    }

    public void rest(){
	stats.modifyHp(1);
	stats.modifyFood(-5);
    }

    private void commonAttack(Creature other, int attack, String action, Object... params){
	stats.modifyFood(-2);

	int amount = Math.max(0, attack - other.stats.defenseValue);

	amount = (int)(Math.random() * amount) + 1;

	Object[] params2 = new Object[params.length + 1];
	for(int i = 0; i < params.length; i++){
	    params2[i] = params[i];
	}
	params2[params2.length - 1] = amount;

	doAction(action, params2);

	other.stats.modifyHp(-amount);

	if(other.stats.hp < 1) gainXp(other);
    }

    public void meleeAttack(Creature other){
	commonAttack(other, stats.attackValue, "attack the %s for %d damage", other.name);
    }

    public void rangedWeaponAttack(Creature other){
	commonAttack(other, stats.attackValue / 2 + weapon.stats.rangedAttackValue, "fire a %s at the %s for %d damage",
		weapon.name(), other.name);
    }

    private void throwAttack(Item item, Creature other){
	commonAttack(other, stats.attackValue / 2 + item.stats.thrownAttackValue, "throw a %s at the %s for %d damage",
		item.name(), other.name);
	other.addEffect(item.quaffEffect());
    }

    public void throwItem(Item item, int wx, int wy, int wz){
	Point end = new Point(x, y, 0);

	for(Point p : new Line(x, y, wx, wy)){
	    if(!realTile(p.x, p.y, z).isGround()) break;
	    end = p;
	}

	wx = end.x;
	wy = end.y;

	Creature c = creature(wx, wy, wz);

	if(c != null)
	    throwAttack(item, c);
	else doAction("throw a %s", item.name());

	putAt(item, wx, wy, wz);
    }

    public void gainXp(Creature other){
	int amount = other.stats.maxHp + other.stats.attackValue + other.stats.defenseValue - stats.level * 2;

	if(amount > 0) stats.modifyXp(amount);
    }

    public void dig(int wx, int wy, int wz){
	stats.modifyFood(-10);
	world.dig(wx, wy, wz);
	doAction("dig");
    }

    public void pickup(){
	Item item = world.item(x, y, z);

	if(inventory.isFull() || item == null){
	    doAction("grab at the ground");
	}else{
	    doAction("pickup a %s", item.name());
	    world.remove(x, y, z);
	    inventory.add(item);
	}
    }

    public void drop(Item item){
	if(world.addAtEmptySpace(item, x, y, z)){
	    doAction("drop a " + item.name());
	    getRidOf(item);
	}else{
	    notify("There is no room to drop %s", item.name());
	}
    }

    public void quaff(Item item){
	doAction("quaff a " + item.name());
	consume(item);
    }

    public void eat(Item item){
	doAction("eat a " + item.name());
	consume(item);
    }

    private void consume(Item item){
	if(item.stats.foodValue < 0) notify("Gross!");

	addEffect(item.quaffEffect());

	stats.modifyFood(item.stats.foodValue);
	getRidOf(item);
    }

    private void addEffect(Effect effect){
	if(effect == null) return;

	effect.start(this);
	effects.add(effect);
    }

    public void unequip(Item item){
	if(item == null) return;

	stats.visionValue -= item.stats.visionValue;

	if(item == armor){
	    doAction("remove a " + item.name());
	    armor = null;
	}else if(item == weapon){
	    doAction("put away a " + item.name());
	    weapon = null;
	}
    }

    public void equip(Item item){
	if(!inventory.contains(item)){
	    if(inventory.isFull()){
		notify("Can't equip %s since you're holding too much stuff.", item.name());
		return;
	    }else{
		world.remove(item);
		inventory.add(item);
	    }
	}

	if(item.stats.attackValue == 0 && item.stats.rangedAttackValue == 0 && item.stats.defenseValue == 0
		&& item.stats.visionValue == 0)
	    return;

	stats.visionValue += item.stats.visionValue;

	if(item.stats.attackValue + item.stats.rangedAttackValue >= item.stats.defenseValue){
	    unequip(weapon);
	    doAction("wield a " + item.name());
	    weapon = item;
	}else{
	    unequip(armor);
	    doAction("put on a " + item.name());
	    armor = item;
	}
    }

    public void summon(Creature other){
	world.add(other);
    }

    private int detectCreatures;

    public void modifyDetectCreatures(int amount){
	detectCreatures += amount;
    }

    public void castSpell(Spell spell, int x2, int y2){
	Creature other = creature(x2, y2, z);

	if(spell.manaCost() > stats.mana){
	    doAction("point and mumble but nothing happens");
	    return;
	}else if(other == null){
	    doAction("point and mumble at nothing");
	    return;
	}

	other.addEffect(spell.effect());
	stats.modifyMana(-spell.manaCost());
    }

    private void getRidOf(Item item){
	inventory.remove(item);
	unequip(item);
    }

    private void putAt(Item item, int wx, int wy, int wz){
	inventory.remove(item);
	unequip(item);
	world.addAtEmptySpace(item, wx, wy, wz);
    }

    // Booleans
    public boolean canSee(int wx, int wy, int wz){
	return (detectCreatures > 0 && world.creature(wx, wy, wz) != null || ai.canSee(wx, wy, wz));
    }

    public boolean canEnter(int wx, int wy, int wz){
	return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
    }

    public boolean isPlayer(){
	return glyph == '@';
    }

    public String hunger(){
	if(stats.food < stats.maxFood * 0.1)
	    return "Starving";
	else if(stats.food < stats.maxFood * 0.2)
	    return "Hungry";
	else if(stats.food > stats.maxFood * 0.9)
	    return "Stuffed";
	else if(stats.food > stats.maxFood * 0.8)
	    return "Full";
	else return "";
    }

    void leaveCorpse(){
	Item corpse = new Item(name + " corpse", '%', color.darker());
	corpse.stats.modifyFoodValue(stats.maxHp);
	world.addAtEmptySpace(corpse, x, y, z);
	for(Item item : inventory.getItems()){
	    if(item != null) drop(item);
	}
    }

    // util
    private String makeSecondPerson(String text){
	String[] words = text.split(" ");
	words[0] = words[0] + "s";

	StringBuilder builder = new StringBuilder();
	for(String word : words){
	    builder.append(" ");
	    builder.append(word);
	}

	return builder.toString().trim();
    }

    public Creature creature(int wx, int wy, int wz){
	if(canSee(wx, wy, wz))
	    return world.creature(wx, wy, wz);
	else return null;
    }

    public int distanceTo(Creature other){
	Line l = new Line(x, y, other.x, other.y);
	return l.length();
    }

    public Item item(int wx, int wy, int wz){
	if(canSee(wx, wy, wz))
	    return world.item(wx, wy, wz);
	else return null;
    }

    public Tile realTile(int wx, int wy, int wz){
	return world.tile(wx, wy, wz);
    }

    public Tile tile(int wx, int wy, int wz){
	if(canSee(wx, wy, wz))
	    return world.tile(wx, wy, wz);
	else return ai.rememberedTile(wx, wy, wz);
    }

}
