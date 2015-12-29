package GameResources;

import java.util.ArrayList;
import main.Game;
import me.grea.antoine.utils.Dice;

public class Item {

    private String name;

    private int weight;

    private int value;

    public int getValueEffect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addEffect() {
    }
    
    public static Item randomItem(){
        // reprendre avec les bons constructeurs
        int r = Dice.roll(0,2);
        Item i = new Item();
        switch(r){
            case 0: 
                i = new Usable(/*Game.getMainShip().getAverageLevel()*/);
                break;
            case 1: 
                i = new Weapon(/*Game.getMainShip().getAverageLevel()*/);
                break;
            case 2:
                i = new Armor(/*Game.getMainShip().getAverageLevel()*/);
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
