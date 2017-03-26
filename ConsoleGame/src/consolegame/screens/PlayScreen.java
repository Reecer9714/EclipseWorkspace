package consolegame.screens;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import asciiPanel.AsciiPanel;
import consolegame.game.*;
import consolegame.game.creatures.Creature;
import consolegame.game.creatures.CreatureFactory;
import consolegame.game.items.Item;
import consolegame.game.items.ItemFactory;

public class PlayScreen implements Screen{
    private World world;
    private Screen subscreen;
    private FieldOfView fov;
    private List<String> messages;
    private int screenWidth;
    private int screenHeight;

    private ItemFactory itemFactory;
    private CreatureFactory creatureFactory;
    private static Creature player;

    public PlayScreen(){
	screenWidth = 80;
	screenHeight = 21;
	messages = new ArrayList<String>();
	createWorld();

	itemFactory = new ItemFactory(world);

	fov = new FieldOfView(world);
	creatureFactory = new CreatureFactory(world, itemFactory, fov);

	createItems(itemFactory);
	createCreatures(creatureFactory);

    }

    private void createWorld(){
	long seed = 1;
	world = new WorldFactory(seed, 90, 31, 8).makeCaves().build();
//	world = new WorldFactory(seed, 90, 31, 8).makeDungeon(10, 5, 10).build();
    }

    private void createPlayer(CreatureFactory cf){
	setPlayer(cf.newPlayer(messages));
    }
    
    private void createCreatures(CreatureFactory cf){
	createPlayer(cf);
//	cf.newPet(getPlayer());

	for(int z = 0; z < world.depth(); z++){
	    for(int i = 0; i < 8; i++){
		cf.newFungus(z);
	    }
	    for(int i = 0; i < 15; i++){
		cf.newBat(z);
	    }
	    for(int i = 0; i < z + 3; i++){
		cf.newZombie(z, getPlayer());
	    }
	    if(z > world.depth() / 2) for(int i = 0; i < z - world.depth() / 2 + 1; i++)
		cf.newGoblin(z, getPlayer());
	}
    }

    private void createItems(ItemFactory itf){
	for(int z = 0; z < world.depth(); z++){
	    for(int i = 0; i < world.width() * world.height() / 50; i++)
		itf.newRock(z);
	    for(int i = 0; i < 3; i++){
		itf.newRandomPotion(z);
	    }
	}
	itf.newTorch(0);
	itf.newDagger(1);
	itf.newLightArmor(2);
	itf.newBow(3);
	itf.newMediumArmor(4);
	itf.newStaff(5);
	itf.newHeavyArmor(6);
	itf.newSword(7);

	itf.newVictoryItem(world.depth() - 1);
    }

    public int getScrollX(){
	return Math.max(0, Math.min(getPlayer().x - screenWidth / 2, world.width() - screenWidth));
    }

    public int getScrollY(){
	return Math.max(0, Math.min(getPlayer().y - screenHeight / 2, world.height() - screenHeight));
    }

    private void displayTiles(AsciiPanel terminal, int left, int top){
	fov.update(getPlayer().x, getPlayer().y, getPlayer().z, getPlayer().stats.visionValue);

	for(int x = 0; x < screenWidth; x++){
	    for(int y = 0; y < screenHeight; y++){
		int wx = x + left;
		int wy = y + top;

		if(getPlayer().canSee(wx, wy, getPlayer().z))
		    terminal.write(world.glyph(wx, wy, getPlayer().z), x, y, world.color(wx, wy, getPlayer().z));
		else terminal.write(fov.tile(wx, wy, getPlayer().z).glyph(), x, y, Color.darkGray);
	    }
	}
    }

    private void displayMessages(AsciiPanel terminal, List<String> messages){
	int top = 23 - messages.size();
	for(int i = 0; i < messages.size(); i++){
	    terminal.writeCenter(messages.get(i), top + i);
	}
	messages.clear();
	// copy into history
    }

    @Override
    public void displayOutput(AsciiPanel console){
	int left = getScrollX();
	int top = getScrollY();

	displayTiles(console, left, top);
	displayMessages(console, messages);
	console.write(getPlayer().glyph(), getPlayer().x - left, getPlayer().y - top, getPlayer().color());

	String stats = String.format(" %3d/%3d hp  %d/%d mana  %8s", getPlayer().stats.hp, getPlayer().stats.maxHp, getPlayer().stats.mana, getPlayer().stats.maxMana,
		getPlayer().hunger());

	console.write(stats, 1, 23);

	if(subscreen != null) subscreen.displayOutput(console);

    }

    @Override
    public Screen respondToUserInput(KeyEvent e){
	int level = getPlayer().stats.level;

	if(subscreen != null){
	    subscreen = subscreen.respondToUserInput(e);
	}else{
	    boolean action = true;

	    switch(e.getKeyCode()){
		case KeyEvent.VK_NUMPAD4:
		case KeyEvent.VK_LEFT:
		    getPlayer().moveBy(-1, 0, 0);
		break;

		case KeyEvent.VK_NUMPAD6:
		case KeyEvent.VK_RIGHT:
		    getPlayer().moveBy(1, 0, 0);
		break;

		case KeyEvent.VK_NUMPAD8:
		case KeyEvent.VK_UP:
		    getPlayer().moveBy(0, -1, 0);
		break;

		case KeyEvent.VK_NUMPAD2:
		case KeyEvent.VK_DOWN:
		    getPlayer().moveBy(0, 1, 0);
		break;

		case KeyEvent.VK_NUMPAD7:
		    getPlayer().moveBy(-1, -1, 0);
		break;
		case KeyEvent.VK_NUMPAD9:
		    getPlayer().moveBy(1, -1, 0);
		break;
		case KeyEvent.VK_NUMPAD1:
		    getPlayer().moveBy(-1, 1, 0);
		break;
		case KeyEvent.VK_NUMPAD3:
		    getPlayer().moveBy(1, 1, 0);
		break;

		case KeyEvent.VK_NUMPAD5:
		    getPlayer().doAction("wait for a bit");
		break; // wait

		case KeyEvent.VK_D:
		    subscreen = new DropScreen(getPlayer());
		break;
		case KeyEvent.VK_E:
		    subscreen = new EatScreen(getPlayer());
		break;
		case KeyEvent.VK_W:
		    subscreen = new EquipScreen(getPlayer());
		break;
		case KeyEvent.VK_Q:
		    subscreen = new DrinkScreen(getPlayer());
		break;
		case KeyEvent.VK_X:
		    subscreen = new ExamineScreen(getPlayer());
		break;
		case KeyEvent.VK_R:
		    subscreen = new ReadScreen(getPlayer(), getPlayer().x - getScrollX(), getPlayer().y - getScrollY());
		break;
		case KeyEvent.VK_SEMICOLON:
		    subscreen = new LookScreen(getPlayer(), "Looking", getPlayer().x - getScrollX(), getPlayer().y - getScrollY());
		    action = false;
		break;
		case KeyEvent.VK_T:
		    subscreen = new ThrowScreen(getPlayer(), getPlayer().x - getScrollX(), getPlayer().y - getScrollY());
		break;
		case KeyEvent.VK_F:
		    if(getPlayer().weapon() == null || getPlayer().weapon().stats.rangedAttackValue == 0)
			getPlayer().notify("You don't have a ranged weapon equiped.");
		    else subscreen = new FireWeaponScreen(getPlayer(), getPlayer().x - getScrollX(), getPlayer().y - getScrollY());
		break;

		default:
		    action = false;
	    }

	    switch(e.getKeyCode()){
		case KeyEvent.VK_F1:
		    getPlayer().stats.modifyMaxMana(100);
		    getPlayer().stats.modifyMana(100);
		    getPlayer().stats.modifyMaxHp(100);
		    getPlayer().stats.modifyHp(100);
		    getPlayer().stats.modifyVisionValue(100);
		break;
		case KeyEvent.VK_F2:
		    world.addAtEmptySpace(itemFactory.newHolyMagesSpellbook(getPlayer().z), getPlayer().x, getPlayer().y, getPlayer().z);
		break;
	    }

	    boolean action2 = true;

	    switch(e.getKeyChar()){
		case 'g':
		case ',':
		    getPlayer().pickup();
		break;
		case '?':
		    subscreen = new HelpScreen();
		break;
		case '<':
		    if(userIsTryingToExit())
			return userExits();
		    else getPlayer().moveBy(0, 0, -1);
		break;
		case '>':
		    getPlayer().moveBy(0, 0, 1);
		break;

		default:
		    action2 = false;
	    }

	    if(!action && !action2){ return this; }
	}

	if(subscreen == null) world.update();

	if(getPlayer().stats.level > level) subscreen = new LevelUpScreen(getPlayer(), getPlayer().stats.level - level);

	if(getPlayer().stats.hp < 1) return new LoseScreen();

	return this;
    }

    private boolean userIsTryingToExit(){
	return getPlayer().z == 0 && world.tile(getPlayer().x, getPlayer().y, getPlayer().z) == Tile.STAIRS_UP;
    }

    private Screen userExits(){
	for(Item item : getPlayer().inventory().getItems()){
	    if(item != null && item.name().equals("Trophy")) return new WinScreen();
	}
	return new LoseScreen();
    }

    public static Creature getPlayer(){
	return player;
    }

    public static void setPlayer(Creature p){
	player = p;
    }

}
