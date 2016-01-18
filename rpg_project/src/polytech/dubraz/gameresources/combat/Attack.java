package polytech.dubraz.gameresources.combat;

import java.util.HashSet;
import java.util.Set;
import polytech.dubraz.gameresources.Ship;
import polytech.dubraz.gameresources.Stats;

public class Attack implements Ability {
    private Ship ship;
    private HashSet<Effect> effects = new HashSet<>();

    public Attack(Ship ship) {
        this.ship = ship;
        effects.add(new Effect(Stats.HEALTH, -(ship.getStat(Stats.STRENGTH) + ship.getWornWeapon().getEffect(Stats.STRENGTH).getValue()), Effect.PERMANENT));
    }
    
    @Override
    public HashSet<Effect> getEffects() {        
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
