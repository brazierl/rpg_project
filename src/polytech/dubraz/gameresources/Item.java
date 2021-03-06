package polytech.dubraz.gameresources;

import java.io.*;
import polytech.dubraz.gameresources.combat.ArmorType;
import polytech.dubraz.gameresources.combat.UsableType;
import polytech.dubraz.gameresources.combat.WeaponType;
import polytech.dubraz.gameresources.combat.Effect;
import java.util.*;
import polytech.dubraz.main.Game;
import me.grea.antoine.utils.Dice;

public class Item implements Serializable{
    
    protected static final int BONUSCOEFF = 20;
    
    protected String name;

    protected int weight;

    protected int value;
    
    protected int level;

    protected HashSet<Effect> effects = new HashSet<>();

    public Item(String name, int weight, int value, HashSet<Effect> effects, int level) {
        this.value = 1000 * level;
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.effects = effects;
        this.level = level;
    }

    public Item(String name, int level) {
        this.value = 1000 * level;
        this.name = name;
        this.level = level;
    }

    public Item() {
        this.value = 100 * level;
    }
        
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public HashSet<Effect> getEffects() {
        return effects;
    }
    
    public Effect getEffect(Stats s)
    {
        for(Effect e : effects){
            if(e.getS().equals(s))
                return e;
        }
        return null;
    }
    
    public int getValueEffect() {
        return value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    
    public void addEffect(Effect e) {
        effects.add(e);
    }
    
    public static Item randomItem(){
        // reprendre avec les bons constructeurs
        int r = Dice.roll(0,2);
        Item i = new Item();
        int level = Game.getMainShip().getAverageLevel();
        switch(r){
            case 0: 
                i = new Usable(UsableType.randomType() , "", level, true);
                break;
            case 1: 
                i = new Weapon(WeaponType.randomType() , "", level, true);
                break;
            case 2:
                i = new Armor(ArmorType.randomType() , "", level, true);
                break;
        }
        return i;
    }
    
    public static ArrayList<Item> randomListItems(){
        ArrayList<Item> list;
        list = new ArrayList<>();
        int r = Dice.roll(1,4);
        for (int i = 0; i < r; i++) {
            list.add(randomItem());
        }
        return list;
    }
    
    public HashSet<Effect> getInvertEffects(){
        HashSet<Effect> effs = new HashSet<>();
        if(this.effects != null){
            for(Effect e : this.effects)
            {
                effs.add(e.getInvertEffect());
            }
        }
        return effs;
    }
    
    public static String[] usablesToArray(ArrayList<Usable> usables){
        String[] list = new String[usables.size()];
        int i=0;
        for(Usable u : usables){
            list[i] = u.toString();
            i++;
        }
        return list;
    }
    
    public static String[] itemsToArray(ArrayList<Item> items){
        String[] list = new String[items.size()];
        int i=0;
        for(Item item : items){
            list[i] = item.toString();
            i++;
        }
        return list;
    }

    @Override
    public String toString() {
        return name + " : -WGT: " + weight + ", VAL:" + value + ", LVL: " + level + "\nEFFECTS: \n" + effectsToString();
    }
    
    public String effectsToString(){
        String s = "";
        int i = 0;
        for(Effect e : effects){
            if(i==effects.size()-1)
                s += "  ["+ e.toString()+"]";
            else
                s += "  ["+ e.toString()+"]\n";
            i++;
        }
        return s;
    }
    
     public static ArrayList<Item> allTradableItem(ArrayList<Ship> ships){
        ArrayList<Item> list = new ArrayList<>();
        for(Ship s : ships){
            for(Item i : s.getInventory()){
                list.add(i);
            }
        }
        return list;
    }
}
