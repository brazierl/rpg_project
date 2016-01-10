package polytech.dubraz.gameresources;

import polytech.dubraz.gameresources.combat.ArmorType;
import polytech.dubraz.gameresources.combat.UsableType;
import polytech.dubraz.gameresources.combat.WeaponType;
import polytech.dubraz.gameresources.combat.Effect;
import java.util.*;
import polytech.dubraz.main.Game;
import me.grea.antoine.utils.Dice;

public class Item {
    
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

    @Override
    public String toString() {
        return name + " : -wgt: " + weight + ", val:" + value + ", lvl: " + level + ", \neffects: " + effects.toString()+"\n";
    }
    
    
}
