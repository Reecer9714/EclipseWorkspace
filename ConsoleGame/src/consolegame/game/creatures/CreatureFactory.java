package consolegame.game.creatures;

import java.util.List;

import asciiPanel.AsciiPanel;
import consolegame.game.*;
import consolegame.game.items.ItemFactory;

public class CreatureFactory {
	private World world;
	private FieldOfView fov;
	private ItemFactory itf;

    public CreatureFactory(World world, ItemFactory itf, FieldOfView fov){
        this.world = world;
        this.itf = itf;
        this.fov = fov;
    }
    
    public Creature newPlayer(List<String> messages){
        Creature player = new Creature(world, "You", '@', AsciiPanel.brightWhite, 20, 5, 0, 4, 20);
        world.addAtEmptyLocation(player, 0);
        new PlayerAi(player, messages, fov);
        return player;
    }
    
    public GameObject newPet(Creature owner){
	Creature pet;
	switch((int)(World.random.nextDouble() * 2)-1){
	    
	    case 0: pet = newCat(owner); break;
	    default: pet = newDog(owner);
	}
	pet.name("Pet " + pet.name());
	return pet;
    }
    
    public GameObject newFungus(int depth){
        Creature fungus = new Creature(world, "Fungus", 'f', AsciiPanel.green, 5, 0, 0, 2, 1);
        new FungusAi(fungus, this);
        world.addAtEmptyLocation(fungus, depth);
        return fungus;
    }
    
    public Creature newDog(Creature owner){
    	Creature dog = new Creature(world, "Dog", 'd', AsciiPanel.brightBlack, 10, 1, 0, 5, 3);
    	new FriendlyAi(dog, owner);
    	world.addAtEmptySpace(dog, owner.x, owner.y, owner.z);
    	return dog;
    }
    
    public Creature newCat(Creature owner){
    	Creature cat = new Creature(world, "Cat", 'c', AsciiPanel.brightBlack, 10, 1, 0, 5, 3);
    	new FriendlyAi(cat, owner);
    	world.addAtEmptySpace(cat, owner.x, owner.y, owner.z);
    	return cat;
    }
    
    public GameObject newBat(int depth){
    	Creature bat = new Creature(world, "Bat", 'b', AsciiPanel.yellow, 10, 1, 0, 5, 3);
    	world.addAtEmptyLocation(bat, depth);
    	new BatAi(bat);
    	return bat;
    }
    
    public GameObject newZombie(int depth, Creature player){
    	Creature zombie = new Creature(world, "Zombie", 'z', AsciiPanel.green, 15, 3, 1, 4, 5);
    	world.addAtEmptyLocation(zombie, depth);
    	new ZombieAi(zombie, player);
    	return zombie;
    }
    
    public GameObject newGoblin(int depth, Creature player){
        Creature goblin = new Creature(world, "goblin", 'g', AsciiPanel.brightGreen, 20, 5, 5, 5, 5);
        goblin.equip(itf.newRandomWeapon(depth));
        goblin.equip(itf.newRandomArmor(depth));
        world.addAtEmptyLocation(goblin, depth);
        new GoblinAi(goblin, player);
        return goblin;
    }
    
    //quest giver
    //shop keeper
    
    
}
