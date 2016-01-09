package polytech.dubraz.gameresources.combat;

import java.util.*;
import polytech.dubraz.gameresources.*;

public interface Ability {
    static final int STATLIFECOEFF = 10;
    public HashSet<Effect> getEffects();
    public Effect getEffect(Stats s);
}
