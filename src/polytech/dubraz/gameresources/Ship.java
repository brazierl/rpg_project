package polytech.dubraz.gameresources;

import java.io.*;
import polytech.dubraz.gameresources.combat.Effect;
import polytech.dubraz.eventhandlers.Console;
import java.util.*;
import java.util.Map.*;
import polytech.dubraz.main.Game;
import me.grea.antoine.utils.Dice;
import me.grea.antoine.utils.Log;

public class Ship implements Serializable{

    protected String name;

    protected int level;
    
    protected int experience;

    protected int maxWeight;

    protected int maxHealth;
    
    protected ArrayList<Item> inventory = new ArrayList<>();

    protected Place place;
    protected static final int DEFAULTLEVEL = 1;
    protected static final int DEFAULTMAXWEIGHT = 1000;
    protected static final int DEFAULTMAXHEALTH = 100;
    
    public static int defaultXpLvl = 1000;
    
    protected static final double LEVELSTATCOEFF = 0.1; 
    
    protected HashMap<Stats,Integer> stats = new HashMap<>();

    protected static int numShip = 1;
    
    protected Armor wornArmor;
    protected Weapon wornWeapon;
    
    
    // ----------------- Constructors -----------------
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
        this(name, level, 0, 0);
        countMaxHealth();
        countMaxWeight();
    }

    public Ship() {
    }

    
    // ----------------- Get/Set -----------------
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

    public int getXp() {
        return experience;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }
    
    public Armor getWornArmor() {
        return wornArmor;
    }

    public Weapon getWornWeapon() {
        return wornWeapon;
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
    
    
    // ----------------- Managing Items -----------------
    public void equipItem(Item i) throws IllegalArgumentException{    
        if(i instanceof Armor){
            if(wornArmor == null)
            {
                wornArmor = (Armor)i;
                this.applyEffects(i.getEffects());
            }
            else
            {
                if(Console.displayYN("Equip this armor instead of "+ wornArmor.getName()+ " ?"))
                {
                    wornArmor = (Armor)i;
                    this.applyEffects(i.getEffects());
                }
            }
        }
        else if(i instanceof Weapon){
            if(wornWeapon == null)
            {
                wornWeapon = (Weapon)i;
                this.applyEffects(i.getEffects());
            }
            else{
                if(Console.displayYN("Equip this weapon instead of "+ wornArmor.getName()+ " ?"))
                {
                    wornWeapon = (Weapon)i; 
                    this.applyEffects(i.getEffects());
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("You cannot equip this Item.");
        }
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

    public void equipWeapon(Weapon w) {
        equipItem(w);
    }
    public void equipArmor(Armor a) {
        equipItem(a);
    }
    public void unequipArmor(){
        this.applyEffects(wornArmor.getInvertEffects());
        wornArmor = null;
    }
    public void unequipWeapon(){
        this.applyEffects(wornWeapon.getInvertEffects());
        wornWeapon = null;
    }
    
    public String inventoryToString(){
        String s = "";
        if(!inventory.isEmpty()){
            for(int i = 0; i<inventory.size(); i++)
            {
                if(i==inventory.size()-1)
                    s += i+". "+inventory.get(i);
                else
                    s += i+". "+inventory.get(i)+",\n";
            }
        }
        return s;
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
    
    public ArrayList<Usable> getUsables(){
        ArrayList<Usable> usables = new ArrayList<>();
        for(Item i : inventory){
            if(i instanceof Usable)
            {
                usables.add((Usable)i);
            }
        }
        return usables;
    }
    public void use(Usable u){
        u.setIsCurrentlyUsed(true);
        applyEffects(u.effects);
        dropItem(u);
    }
    
    public boolean trade(ArrayList<Item> itemsGiven, ArrayList<Item> itemsTaken, ArrayList<Ship> ships) {
        int val1 = 0;
        int val2 = 0;
        for(Item i : itemsTaken){
            val1 += i.getValue();
        }
        for(Item i : itemsGiven){ 
            val2 += i.getValue();
        }
        if(Math.abs(val2-val1)<=Place.TRADEFLEXIBILITY)
        {
            for(Ship s : ships){
                for(Item it1 : s.getInventory()){
                    for(Item iTaken : itemsTaken){
                        if(it1.equals(iTaken))
                        {
                            for(Item it2 : Game.getMainShip().getInventory()){
                               for(Item iGiven : itemsGiven){ 
                                    if(it2.equals(iGiven))
                                    {
                                        Game.getMainShip().dropItem(iGiven);
                                        s.getInventory().add(iGiven);
                                        s.dropItem(iTaken);
                                        Game.getMainShip().getInventory().add(iTaken);
                                        return true;
                                    }
                               }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    
    public void dropItem(Item i) {
        if(i!=null){
            for(int j=0; j< inventory.size(); j++ )
            {
                if(inventory.get(j).equals(i)){
                    inventory.remove(j);
                    break;
                }
            }   
        }
    }
    
    public void collect(Item i, ArrayList<Ship> ships) {
        for(Ship s : ships){
            for(Item it : s.getInventory()){
                if(it.equals(i))
                {
                    s.getInventory().remove(i);
                    this.getInventory().add(i);
                }
            }
        }
    }
    
    // ----------------- Managing Effects -----------------
   
    public void applyEffect(Effect e) {
        for(Entry<Stats, Integer> s : stats.entrySet()) {
            if(s.getKey() == e.getS()){
                if(s.getValue()+e.getValue()<0)
                    s.setValue(0);
                else
                    s.setValue(s.getValue()+e.getValue());
            }
        }
        countMaxHealth();
        countMaxWeight();
    }
    public void applyEffects(HashSet<Effect> effects) {
        for(Effect e : effects){
            applyEffect(e);
        }
    }
    
    public void endOfFight(){
        autoCalculateStats();
        applyEffects(wornArmor.getEffects());
        applyEffects(wornWeapon.getEffects());
        for(Usable u : getUsables()){
            if(u.isCurrentlyUsed())
                inventory.remove(u);
        }
    }
    
    public void effectTurnsDecrement(){
        for(Usable u : getUsables()){
            u.decrementTurn();
        }
    }
    
    // ----------------- Managing Stats -----------------
    
    public void countMaxHealth() {
        maxHealth = getStat(Stats.HEALTH);
    }
    public void countMaxWeight() {
        maxWeight = DEFAULTMAXWEIGHT + DEFAULTMAXWEIGHT/100 * getStat(Stats.STRENGTH);
    }
    public void levelUp() {
        level++;
        experience = 0;
        defaultXpLvl *= 1.2;
        stats.replace(Stats.HEALTH, (int)Math.round(stats.get(Stats.HEALTH)*1.2));
        autoCalculateStats();
        applyEffects(wornArmor.getEffects());
        applyEffects(wornWeapon.getEffects());        
    }
    public void addXp(int xpToAdd){
        experience += xpToAdd;
        if(xpToAdd>defaultXpLvl){
            levelUp();
            experience = xpToAdd%defaultXpLvl;
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
    
    protected int getValueLevelStat(int statVal)
    {
        return (int) Math.round(statVal * (1 + LEVELSTATCOEFF * (level - 1)));
    }
    public int getAverageLevel(){
        int l = this.level + Dice.roll(-1, 1);
        return l==0?1:l;
    }
    protected void autoCalculateStats(){
        this.stats.put(Stats.HEALTH, getValueLevelStat(100));
        this.stats.put(Stats.DEFENSE, getValueLevelStat(100));
        this.stats.put(Stats.ENGINEERING, getValueLevelStat(100));
        this.stats.put(Stats.MANIABILITY, getValueLevelStat(100));
        this.stats.put(Stats.STRENGTH, getValueLevelStat(100));
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
    
    // ----------------- Random Ship -----------------
    
    public static Ship randomShip(){
        int r = Dice.roll(0,2);
        Ship s = new Ship();
        switch(r){
            case 0: 
                s = new FrontShip("FrontShip"+numShip, Game.getMainShip().getAverageLevel());
                break;
            case 1: 
                s = new TechShip("TechShip"+numShip, Game.getMainShip().getAverageLevel());
                break;
            case 2:
                s = new AssaultShip("AssaultShip"+numShip, Game.getMainShip().getAverageLevel());
                break;
        }
        s.equipArmor((Armor)Armor.randomItem());
        s.equipWeapon((Weapon)Weapon.randomItem());
        s.inventory = Item.randomListItems();
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
    
    protected final void equipFirstRandWeapon(){
        Armor armor = (Armor)Armor.randomItem();
        Weapon weapon = (Weapon)Weapon.randomItem();
        inventory.add(armor);
        inventory.add(weapon);
        equipArmor(armor);
        equipWeapon(weapon);
    }
    
     // ----------------- toString Methods -----------------
    
    @Override
    public String toString() {
        return name + " : LVL:" + level + ", XP:" + experience + "/"+defaultXpLvl+", WGT:"+ getInventoryWeight() + "/" + maxWeight + 
                ", \nINVENTORY:\n" + ("".equals(inventoryToString())?"Empty":inventoryToString()) + 
                ", \nSTATS:\n" + statsToString() + 
                ", \nWORN EQUIPEMENT:\n    " + wornWeapon.getName()+ " LVL:" + wornWeapon.getLevel() + "\n    " + wornArmor.getName() + " LVL:" + wornWeapon.getLevel();
    }
    
    public String statsToString(){
        String s = "";
        int i = 0;
        for(Entry<Stats, Integer> e : stats.entrySet())
        {
            if(stats.entrySet().size()==i+1)
                s += "  "+e.getKey()+" : "+e.getValue();
            else
                s += "  "+e.getKey()+" : "+e.getValue()+"\n";
            i++;
        }
        return s;
    }
    
    public static String[] shipsToArray(ArrayList<Ship> ships){
        String[] list = new String[ships.size()];
        int i=0;
        for(Ship s : ships){
            list[i] = s.toString();
            i++;
        }
        return list;
    }
    
    public static String[] shipsSimpleToArray(ArrayList<Ship> ships){
        String[] list = new String[ships.size()];
        int i=0;
        for(Ship s : ships){
            if(s.getHealth()<=0)
                list[i] = "(DESTROYED)" + s.getName() + " LVL:" + s.getLevel() + "\n";
            else
                list[i] = s.getName() + " LVL:" + s.getLevel() + "\n";
            i++;
        }
        return list;
    }
    
     public static String shipsToString(ArrayList<Ship> ships){
        String s = "";
        int i = 0;
        for(Ship sh : ships){
            if(++i==ships.size())
                s += sh.toString();
            else
                s += sh.toString()+",\n";
        }
        return s;
    }
    
     public static String shipsToStringPlace(ArrayList<Ship> ships){
        String s = "";
        int i = 0;
        if(!ships.isEmpty()){
            for(Ship sh : ships){
                if(++i==ships.size())
                    s += "  "+sh.getName()+" : lvl "+sh.getLevel();
                else
                    s += "  "+sh.getName()+" : lvl "+sh.getLevel()+"\n";
            }
        }
        return s;
    }
    
    public static String shipsToStringFight(ArrayList<Ship> ships){
        String s = "";
        int i = 0;
        for(Ship sh : ships){
            if(i==ships.size()){
                s += sh.getName() + "\n    " 
                        + sh.getStats().toString() + "\n";
            }
            else{
                 s += sh.getName() + "\n    "
                        + sh.getStats().toString() + "\n";
            }
            i++;
        }
        return s;
    }
    
     // ----------------- Serialization -----------------
    
    public boolean serializeShip(){
        try
        {
           FileOutputStream fileOut = new FileOutputStream("ship.ser");
           ObjectOutputStream out = new ObjectOutputStream(fileOut);
           out.writeObject(this);
           out.close();
           fileOut.close();
           return true;
        }catch(IOException i)
        {
            Log.e(i.getMessage());
            return false;
        }
    }
    
    public static Ship deserializeShip() {
        try
        {
           FileInputStream fileIn = new FileInputStream("ship.ser");
           ObjectInputStream in = new ObjectInputStream(fileIn);
           Ship s = (Ship) in.readObject();
           in.close();
           fileIn.close();
           return s;
        }catch(IOException | ClassNotFoundException i)
        {
           Log.e(i.getMessage());
        }
        return null;
    }
}
