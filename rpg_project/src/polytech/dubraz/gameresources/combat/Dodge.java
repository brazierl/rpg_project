package polytech.dubraz.gameresources.combat;

import java.util.*;
import polytech.dubraz.gameresources.Ship;
import polytech.dubraz.gameresources.Stats;

public class Dodge implements Ability {
private Ship ship;
    private HashSet<Effect> effects = new HashSet<>();

    public Dodge(Ship ship) {
        this.ship = ship;
    }
    
    @Override
    public HashSet<Effect> getEffects() {
        effects.add(new Effect(Stats.DEFENSE, ship.getStat(Stats.MANIABILITY)/10 + ship.getWornWeapon().getEffect(Stats.MANIABILITY).getValue(), 1));
        return effects;
    }

    @Override
    public Effect getEffect(Stats s) {
        for(Effect e : effects)
        {
            if(e.getS().equals(s))
                return e;
        }
        return null;
    }
}
