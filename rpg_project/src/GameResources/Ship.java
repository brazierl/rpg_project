package GameResources;


import EventHandlers.Console;
import GameResources.Combat.*;
import java.util.*;
import java.util.Map.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.Game;
import me.grea.antoine.utils.Dice;

public class Ship {

    protected String name;

    protected int level;
    
    protected int experience;

    protected int maxWeight;

    protected int maxHealth;
    
    protected ArrayList<Item> inventory;

    protected Place p;
    protected static final int DEFAULTLEVEL = 1;
    protected static final int DEFAULTMAXWEIGHT = 1000;
    protected static final int DEFAULTMAXHEALTH = 100;
    
    protected static final double LEVELSTATCOEFF = 0.1; 
    
    protected HashMap<Stats,Integer> stats = new HashMap<>();

    protected static int numShip = 1;
    
    protected Armor wornArmor;
    protected Weapon wornWeapon;
    
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

    public void setP(Place p) {
        this.p = p;
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

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

    public void displayInventory(){
        Console.Display("Inventory : ("+getInventoryWeight()+"/"+maxWeight+")");
        for(int i = 0; i<inventory.size(); i++)
        {
            Console.Display(i+". "+inventory.get(i));
        }
    }
    
    public void equipItem(Item i){    
        if(i instanceof Armor)
            if(wornArmor == null)
                wornArmor = (Armor)i;
            else
                if(Console.DisplayYN("Equip this armor instead of "+ wornArmor.getName()+ " ?"))
                    wornArmor = (Armor)i;
                else{}
        else if(i instanceof Weapon)
            if(wornWeapon == null)
                wornWeapon = (Weapon)i;
            else
                if(Console.DisplayYN("Equip this weapon instead of "+ wornArmor.getName()+ " ?"))
                    wornWeapon = (Weapon)i; 
    }
    
    public void equipItemFromInventory(int index){
        try{
            Item i = inventory.get(index);
            equipItem(i);
        }
        catch(Exception e)
        { 
            Console.Display("This item is not in the list.");
        }
    }
    public void equipWeapon(Weapon w) {
        equipItem(w);
    }
    public void equipArmor(Armor a) {
        equipItem(a);
    }
    public void unequipArmor(){
        wornArmor = null;
    }
    public void unequipWeapon(){
        wornWeapon = null;
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
        for(Entry<Stats, Integer> s : stats.entrySet()) {
            if(s.getKey() == e.getS())
                s.setValue(s.getValue()+e.getValue());
        }
    }
    public void dropItem(int i) {
    }
    public void countMaxHealth() {
        maxHealth = DEFAULTMAXHEALTH + DEFAULTMAXHEALTH/10 * level;
    }
    public void countMaxWeight() {
        maxWeight = DEFAULTMAXWEIGHT + DEFAULTMAXWEIGHT/100 * getStat(Stats.STRENGTH);
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
            if(!inventory.contains(i))
            {
                inventory.add(i);
                Console.Display(i.getName() + "was added to you inventory.");
            }
            else 
            {
                Console.Display("An similar item is already in your inventory.");
                if(Console.DisplayYN("Pick it up anyway ?"))
                {
                    inventory.add(i);
                    Console.Display(i.getName() + "was added to you inventory.");
                }
                    
            }
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
        ArrayList<Ship> list = new ArrayList<>();
        int r = Dice.roll(1,4);
        for (int i = 0; i < r; i++) {
            list.add(randomShip());
        }
        return list;
    }
    public int getStat(Stats stat)
    {
        for(Entry<Stats, Integer> s : stats.entrySet()) {
            if(s.getKey() == stat)
                return s.getValue();
        }
        try {
            throw new Exception("No stat value found.");
        } catch (Exception ex) {
            Console.Display(ex.getMessage());
        }
        return 0;
    }
    
}
