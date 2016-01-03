package GameResources;

import EventHandlers.Console;
import java.util.*;
import main.Game;
import me.grea.antoine.utils.*;

public class Place {
    private static final int TRADEFLEXIBILITY=20;
    
    private String name;
    private boolean isPeaceful;
    private int level;
    private ArrayList<Ship> ships;   
    
    public static final String HYPERSPACE = "Hyper Space";
    
    private static final ArrayList<String> NAMES = new ArrayList<String>() {{
        add("Mercure");
        add("Venus");
        add("Earth");
        add("Mars");
        add("Jupiter");
        add("Saturne");
        add("Uranus");
        add("Neptune");
        add("Pluton");
        add("Earth Moon");
    }};

    public Place() {
        
    }

    public Place(String name, boolean isPeaceful, int level) {
        this.name = name;
        this.isPeaceful = isPeaceful;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }
    
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isPeaceful() {
        return isPeaceful;
    }
    
    public static Place randomPlace()
    {
        Place p = new Place();
        p.name = getRandomName();
        p.isPeaceful = (Dice.roll(0,1)==1);
        p.level = Game.getMainShip().getLevel() + Dice.roll(-1, 1);
        return p;
    }
    
    public static Place randomPlace(String name)
    {
        Place p = new Place();
        p.name = getRandomName(name);
        p.isPeaceful = (Dice.roll(0,1)==1);
        p.level = Game.getMainShip().getLevel() + Dice.roll(-1, 1);
        return p;
    }
    
    public static ArrayList<Place> randomPlaces(Place cP)
    {
        ArrayList<Place> r = new ArrayList<>();
        if(cP.getName().equals(Place.HYPERSPACE))
        {
            Place p = new Place();
            p = randomPlace();
            r.add(p);
            int i = 0;
            for(String s : NAMES){
                if(s.equals(p.name))
                    break;
                i++;
            }
            r.add(randomPlace(NAMES.get(i-1)));
            r.add(randomPlace(NAMES.get(i+1)));
            return r;
        }
        else{
            Place p = new Place();
            p = randomPlace(cP.getName());
            r.add(p);
            int i = 0;
            for(String s : NAMES){
                if(s.equals(p.name))
                    break;
                i++;
            }
            r.add(randomPlace(NAMES.get(i-1)));
            r.add(randomPlace(NAMES.get(i+1)));
            return r;
        }
    }
    
    private static String getRandomName(){
        return NAMES.get(Dice.roll(NAMES.size()-1));
    }
    
    private static String getRandomName(String name){
        String n = NAMES.get(Dice.roll(NAMES.size()-1));
        while(n.equals(name))
        {
            n = NAMES.get(Dice.roll(NAMES.size()-1));
        }
        return n;
    }
    
    public void trade(ArrayList<Item> itemsGiven, ArrayList<Item> itemsTaken) {
        int val1 = 0;
        int val2 = 0;
        for(Item i : itemsTaken){
            val1 += i.getValue();
        }
        for(Item i : itemsGiven){ 
            val2 += i.getValue();
        }
        if(Math.abs(val2-val1)<=TRADEFLEXIBILITY)
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
                                        Game.getMainShip().getInventory().remove(iGiven);
                                        s.getInventory().add(iGiven);
                                        s.getInventory().remove(iTaken);
                                        Game.getMainShip().getInventory().add(iTaken);
                                    }
                               }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void collect(Item i) {
        for(Ship s : ships){
            for(Item it : s.getInventory()){
                if(it.equals(i))
                {
                    s.getInventory().remove(i);
                    Game.getMainShip().getInventory().add(i);
                }
            }
        }
    }
    
    public ArrayList<Item> allTradableItem(){
        ArrayList<Item> list = new ArrayList<>();
        for(Ship s : ships){
            for(Item i : s.getInventory()){
                list.add(i);
            }
        }
        return list;
    }
    
    public String shipsToString(){
        String s = "";
        int i = 0;
        for(Ship sh : ships){
            if(++i==ships.size())
                s += sh.toString();
            else
                s += sh.toString()+", ";
        }
        return s;
    }

    @Override
    public String toString() {
        return name + ", "+ (isPeaceful?"peaceful":"harmful") + ", lvl:" + level + ", ships:" + shipsToString();
    }
    
}
