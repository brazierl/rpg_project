package GameResources;


import EventHandlers.Console;
import GameResources.Combat.*;
import java.util.*;
import main.Game;
import me.grea.antoine.utils.Dice;

public class Ship {

    protected String name;

    protected int level;
    
    protected int experience;

    protected int maxWeight;

    protected int maxHealth;
    
    protected Set<Item> inventory;

    protected Place p;
    protected static final int DEFAULTLEVEL = 1;
    protected static final int DEFAULTMAXWEIGHT = 1000;
    protected static final int DEFAULTMAXHEALTH = 100;
    
    protected static final double LEVELSTATCOEFF = 0.1; 
    
    protected HashMap<Stats,Integer> stats = new HashMap<>();

    protected static int numShip = 1;
    
    public Ship(String name, int level, int maxWeight, int MaxHealth) {
        this.name = name;
        this.level = level;
        this.maxWeight = maxWeight;
        this.maxHealth = MaxHealth;
        this.experience = 0;
        numShip++;
    }
    public Ship(String name, int maxWeight, int MaxHealth) {
        this(name,DEFAULTLEVEL,maxWeight,MaxHealth);
    }
    
    public Ship(String name){
        this(name,DEFAULTLEVEL,DEFAULTMAXWEIGHT,DEFAULTMAXHEALTH);
    }
    
    public Ship(String name, int level)
    {
        //recalculer maxhealth et maxweight
        this(name, level, 0, 0);
        countMaxHealth();
        countMaxWeight();
    }

    public Ship() {
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
        return maxHealth;
    }

    public Set<Item> getInventory() {
        return inventory;
    }

    public void setInventory(Set<Item> inventory) {
        this.inventory = inventory;
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

    public void dropItem(Item i) {
    }

    public void countMaxHealth() {
    }
    
    public void countMaxWeight() {
    }

    public void levelUp() {
        level++;
        experience = 0;
    }

    public void sumStats() {
    }

    public void travel(Place p) {
    }

    public void addItem(Item i) {
        if(getInventoryWeight()+i.getWeight() > maxWeight)
        {
            Console.Display("Inventory full. You cannot collect this item.");
        }
        else
        {
            inventory.add(i);
            Console.Display(i.getName() + "was added to you inventory.");
        }
    }
    
    public int getInventoryWeight(){
        int w = 0; 
        for(Item i : inventory)
        {
            w += i.getWeight();
        }
        return w;            
    }
    
    public void attack(Place p) {
    }
    
    protected int getValueLevelStat(int statVal)
    {
        return (int) Math.round(statVal * (1 + LEVELSTATCOEFF * level - 1));
    }
    
    public static String generateName(){
        return Ship.class.getName()+numShip;
    }
    
    public int getAverageLevel(){
        return this.level + Dice.roll(-1, 1);
    }
    protected void autoCalculateStats(){
        this.stats.put(Stats.HEALTH, getValueLevelStat(100));
        this.stats.put(Stats.DEFENSE, getValueLevelStat(100));
        this.stats.put(Stats.ENGINEERING, getValueLevelStat(100));
        this.stats.put(Stats.MANIABILITY, getValueLevelStat(100));
        this.stats.put(Stats.STRENGTH, getValueLevelStat(100));
    }
    
    public static Ship randomShip(){
        int r = Dice.roll(0,2);
        Ship s = new Ship();
        switch(r){
            case 0: 
                s = new FrontShip(Ship.generateName(), Game.getMainShip().getAverageLevel());
                break;
            case 1: 
                s = new TechShip(Ship.generateName(), Game.getMainShip().getAverageLevel());
                break;
            case 2:
                s = new AssaultShip(Ship.generateName(), Game.getMainShip().getAverageLevel());
                break;
        }
        return s;
    }
    
    public static ArrayList<Ship> randomListShips(){
        ArrayList<Ship> list = new ArrayList<Ship>();
        int r = Dice.roll(1,4);
        for (int i = 0; i < r; i++) {
            list.add(randomShip());
        }
        return list;
    }
}
