package polytech.dubraz.gameresources.combat;

import polytech.dubraz.main.Game;
import java.util.*;
import polytech.dubraz.gameresources.Ship;
import polytech.dubraz.gameresources.Stats;

public class Action {
    private Ability a;
    private Ship target;
    public Action(Ability a, Ship target) {
        this.a=a;
        this.target = target;
    }

    public Ability getA() {
        return a;
    }

    public Ship getTarget() {
        return target;
    }
    
    public void applyEffects(){
        if(a != null)
        {
            HashSet<Effect> effects = a.getEffects();
            for(Effect e : effects){
                if(e.getS().equals(Stats.HEALTH) && e.getValue()+target.getHealth()>target.getMaxHealth())
                    e.setValue((target.getMaxHealth()-target.getHealth())*(1-target.getStat(Stats.DEFENSE)/50));
            }
            target.applyEffects(effects);
        } 
    }
}
