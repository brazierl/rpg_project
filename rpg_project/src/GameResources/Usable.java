package GameResources;

import GameResources.Combat.*;
import java.util.*;

public class Usable extends Item {
    private UsableType type;
    
    public UsableType getType() {
        return type;
    }

    public void setType(UsableType type) {
        this.type = type;
    }

    public Usable(UsableType type, String name, int weight, int value, Set<Effect> effects, int level) {
        super(name, weight, value, effects, level);
        this.type = type;
    }

    public Usable(UsableType type, String name, int value) {
        super(name, value);
        this.type = type;
    }
    
    
}
