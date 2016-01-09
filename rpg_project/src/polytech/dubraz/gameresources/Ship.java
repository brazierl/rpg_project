package polytech.dubraz.gameresources;


import polytech.dubraz.gameresources.combat.Effect;
import polytech.dubraz.eventhandlers.Console;
import java.util.*;
import java.util.Map.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import polytech.dubraz.main.Game;
import me.grea.antoine.utils.Dice;
import me.grea.antoine.utils.Log;

public class Ship {

    protected String name;

    protected int level;
    
    protected int experience;

    protected int maxWeight;

    protected int maxHealth;
    
    protected ArrayList<Item> inventory = new ArrayList<Item>();

    protected Place place;
    protected static final int DEFAULTLEVEL = 1;
    protected static final int DEFAULTMAXWEIGHT = 1000;
    protected static final int DEFAULTMAXHEALTH = 100;
    
    protected static int defaultXpLvl = 1000;
    
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
        autoCalculateStats();
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

    public void setPlace(Place p) {
        this.place = p;
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
        Console.display("Inventory : ("+getInventoryWeight()+"/"+maxWeight+")");
        for(int i = 0; i<inventory.size(); i++)
        {
            Console.display(i+". "+inventory.get(i));
        }
    }
    
    public String inventoryToString(){
        String s = "";
        for(int i = 0; i<inventory.size(); i++)
        {
            if(i==inventory.size()-1)
                s += i+". "+inventory.get(i);
            else
                s += i+". "+inventory.get(i)+", \n";
        }
        return s;
    }
    
    public void equipItem(Item i){    
        if(i instanceof Armor)
            if(wornArmor == null)
                wornArmor = (Armor)i;
            else
                if(Console.displayYN("Equip this armor instead of "+ wornArmor.getName()+ " ?"))
                    wornArmor = (Armor)i;
                else{}
        else if(i instanceof Weapon)
            if(wornWeapon == null)
                wornWeapon = (Weapon)i;
            else
                if(Console.displayYN("Equip this weapon instead of "+ wornArmor.getName()+ " ?"))
                    wornWeapon = (Weapon)i; 
    }
    
    public void equipItemFromInventory(int index){
        try{
            Item i = inventory.get(index);
            equipItem(i);
        }
        catch(Exception e)
        { 
            Console.display("This item is not in the list.");
        }
    }

    public Armor getWornArmor() {
        return wornArmor;
    }

    public Weapon getWornWeapon() {
        return wornWeapon;
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
    public Place getPlace() {
        return place;
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
    public void applyEffects(HashSet<Effect> effects) {
        for(Effect e : effects){
            applyEffect(e);
        }
    }
    public void dropItem(int i) {
        inventory.remove(inventory.get(i));
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
            Console.display("Inventory full. You cannot collect this item.");
        }
        else
        {
            if(!inventory.contains(i))
            {
                inventory.add(i);
                Console.display(i.getName() + "was added to you inventory.");
            }
            else 
            {
                Console.display("An similar item is already in your inventory.");
                if(Console.displayYN("Pick it up anyway ?"))
                {
                    inventory.add(i);
                    Console.display(i.getName() + "was added to you inventory.");
                }
                    
            }
        }
    }
    public int getInventoryWeight(){
        int w = 0; 
        if(inventory != null)
        {
            for(Item i : inventory)
            {
                w += i.getWeight();
            }
        }
        return w;            
    }
    public void attack(Place p) {
    }
    protected int getValueLevelStat(int statVal)
    {
        return (int) Math.round(statVal * (1 + LEVELSTATCOEFF * level - 1));
    }
    public String generateName(){
        return "Ship no."+numShip;
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
                s = new FrontShip(s.generateName(), Game.getMainShip().getAverageLevel());
                break;
            case 1: 
                s = new TechShip(s.generateName(), Game.getMainShip().getAverageLevel());
                break;
            case 2:
                s = new AssaultShip(s.generateName(), Game.getMainShip().getAverageLevel());
                break;
        }
        s.wornArmor = (Armor)Armor.randomItem();
        s.wornWeapon = (Weapon)Weapon.randomItem();
        s.inventory = Item.randomListItems();
        return s;
    }
    //TODO: Refaire en fonction du niveau de Place
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
        try {
            for(Entry<Stats, Integer> s : stats.entrySet()) {
                if(s.getKey() == stat)
                    return s.getValue();
            }
            throw new Exception("No stat value found.");
        } catch (Exception ex) {
            Log.e(ex.getMessage());
        }
        return 0;
    }
    
    public String statsToString(){
        String s = "";
        int i = 0;
        for(Entry<Stats, Integer> e : stats.entrySet())
        {
            if(stats.entrySet().size()==++i)
                s += e.getKey()+" : "+e.getValue();
            else
                s += e.getKey()+" : "+e.getValue()+", ";
        }
        return s;
    }
    
    @Override
    public String toString() {
        return name + " : lvl:" + level + ", xp:" + experience + "/"+defaultXpLvl+", wgt:"+ getInventoryWeight() + "/" + maxWeight + ", Health:" + maxHealth + ", \ninventory:" + inventoryToString() + ", \nstats=" + statsToString() + ", \nWorn Equipement: " + wornWeapon + "/" + wornArmor;
    }
    
}
