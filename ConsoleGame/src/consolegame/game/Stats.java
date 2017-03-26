package consolegame.game;

public class Stats{

    public int attackValue;
    public int defenseValue;
    public int visionValue;

    public void modifyAttackValue(int amount){
        attackValue += amount;
    }

    public void modifyDefenseValue(int amount){
        defenseValue += amount;
    }

    public void modifyVisionValue(int amount){
        visionValue += amount;
    }

}