package GameResources;

import GameResources.Combat.*;
import java.util.Set;

public class Armor extends Item {
    private ArmorType type;

    public Armor(ArmorType type, String name, int weight, int value, Set<Effect> effects, int level) {
        super(name, weight, value, effects, level);
        this.type = type;
    }

    public Armor(ArmorType type, String name, int value) {
        super(name, value);
        this.type = type;
    }
    
    
}
