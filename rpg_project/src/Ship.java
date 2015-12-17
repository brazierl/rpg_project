
import java.util.*;

public class Ship {

    protected String name;

    protected int level;

    protected int maxWeight;

    protected int MaxHealth;

    protected Place p;
    
    protected HashMap<Stats,Integer> stats = new HashMap<Stats,Integer>();

    public Ship(String name, int level, int maxWeight, int MaxHealth) {
        this.name = name;
        this.level = level;
        this.maxWeight = maxWeight;
        this.MaxHealth = MaxHealth;
    }
    public Ship(String name, int maxWeight, int MaxHealth) {
        this(name,1,maxWeight,MaxHealth);
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getMaxHealth() {
        return MaxHealth;
    }

    public Place getP() {
        return p;
    }
    
    public int getHealth(){
        return this.stats.get(Stats.HEALTH);
    }

    public HashMap<Stats, Integer> getStats() {
        return stats;
    }
    
    public void applyEffect(Effect e) {
    }

    public void equipWeapon(Weapon w) {
    }

    public void equipArmor(Armor a) {
    }

    public int getInventoryWeight() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void dropItem(Item i) {
    }

    public void countMaxHealth() {
    }

    public void levelUp() {
    }

    public void sumStats() {
    }

    public void travel(Place p) {
    }

    public void addItem(Item I) {
    }

    public void attack(Place p) {
    }
}
