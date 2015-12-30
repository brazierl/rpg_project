package GameResources;

import GameResources.Combat.*;
import java.util.*;
import main.Game;
import me.grea.antoine.utils.Dice;

public class Item {

    protected String name;

    protected int weight;

    protected int value;
    
    protected int level;

    protected Set<Effect> effects;

    public Item(String name, int weight, int value, Set<Effect> effects, int level) {
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.effects = effects;
        this.level = level;
    }

    public Item(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public Item() {
    }
        
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public Set<Effect> getEffects() {
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
        switch(r){
            case 0: 
                // générer nom et effet
                i = new Usable(/*Game.getMainShip().getAverageLevel()*/);
                // i.randomEffect()
                break;
            case 1: 
                i = new Weapon(/*Game.getMainShip().getAverageLevel()*/);
                // i.randomEffect()
                break;
            case 2:
                i = new Armor(/*Game.getMainShip().getAverageLevel()*/);
                // i.randomEffect()
                break;
        }
        return i;
    }
    
    public static ArrayList<Item> randomListItems(){
        ArrayList<Item> list = new ArrayList<Item>();
        int r = Dice.roll(1,4);
        for (int i = 0; i < r; i++) {
            list.add(randomItem());
        }
        return list;
    }
}
