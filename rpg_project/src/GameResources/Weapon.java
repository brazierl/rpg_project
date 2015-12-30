package GameResources;

import GameResources.Combat.*;
import java.util.Set;

public class Weapon extends Item {    
    private WeaponType type;

    public Weapon(WeaponType type, String name, int weight, int value, Set<Effect> effects, int level) {
        super(name, weight, value, effects, level);
        this.type = type;
    }

    public Weapon(String name, int value) {
        super(name, value);
        this.type = type;
    }
    
}
