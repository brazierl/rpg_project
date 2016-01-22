package polytech.dubraz.gameresources;

import java.io.Serializable;
import java.util.*;
import polytech.dubraz.main.Game;
import me.grea.antoine.utils.*;

public class Place implements Serializable{
    public static final int TRADEFLEXIBILITY=20;
    
    private String name;
    private boolean isPeaceful;
    private int level;
    private ArrayList<Ship> ships = new ArrayList<>();   
    
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
        ArrayList<Ship> ships = Ship.randomListShips();
        p.name = getRandomNameForList();
        p.isPeaceful = (Dice.roll(0,1)==1);
        p.ships = ships;
        p.level = p.calculateLevel();
        return p;
    }
    
    public static Place randomPlace(String name)
    {
        Place p = new Place();
        ArrayList<Ship> ships = Ship.randomListShips();
        p.name = name;
        p.isPeaceful = (Dice.roll(0,1)==1);
        p.ships = ships;
        p.level = p.calculateLevel();
        return p;
    }
    
    public static ArrayList<Place> randomPlaces(Place cP)
    {
        ArrayList<Place> r = new ArrayList<>();
        if(cP.getName().equals(Place.HYPERSPACE))
        {
            Place p;
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
            Place p;
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
            r.add(new Place(Place.HYPERSPACE,true, 0));
            return r;
        }
    }
    
    private static String getRandomNameForList(){
        return NAMES.get(Dice.roll(1,NAMES.size()-2));
    }
    
    private static String getRandomNameForList(String name){
        String n = NAMES.get(Dice.roll(1,NAMES.size()-2));
        while(n.equals(name))
        {
            n = NAMES.get(Dice.roll(NAMES.size()-1));
        }
        return n;
    }    
    
    @Override
    public String toString() {
        return name + ", "+ (isPeaceful?"peaceful":"harmful") + ", LVL:" + level + 
                " \nSHIPS:\n" + (Ship.shipsToStringPlace(ships).equals("")?"No ship":Ship.shipsToStringPlace(ships));
    }
    
    private int calculateLevel(){
        int sumLvl = 0;
        for(Ship s:ships){
            sumLvl += s.getLevel();
        }
        return sumLvl;
    }
    
}
