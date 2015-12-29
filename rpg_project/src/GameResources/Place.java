package GameResources;

import java.util.*;
import main.Game;
import me.grea.antoine.utils.*;

public class Place {

    private String name;

    private boolean isPeaceful;

    private int level;

    private static final List<String> NAMES = new ArrayList<String>() {{
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
    
    public Item trade(Item i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Place() {
        
    }
    
    public void randomPlace()
    {
        this.name = getRandomName();
        this.isPeaceful = (Dice.roll(0,1)==1);
        this.level = Game.getMainShip().getLevel() + Dice.roll(-1, 1);
    }
    
    private static String getRandomName(){
        return NAMES.get(Dice.roll(NAMES.size()-1));
    }
}
