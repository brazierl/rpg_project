package polytech.dubraz.gameresources.combat;

import java.util.*;
import polytech.dubraz.gameresources.Ship;
import polytech.dubraz.gameresources.Stats;

public class Repair implements Ability {
private Ship ship;
    private HashSet<Effect> effects = new HashSet<>();

    public Repair(Ship ship) {
        this.ship = ship;
    }
    
    @Override
    public HashSet<Effect> getEffects() {
        effects.add(new Effect(Stats.HEALTH, +(ship.getStat(Stats.ENGINEERING) + ship.getWornWeapon().getEffect(Stats.ENGINEERING).getValue()), Effect.PERMANENT));
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
