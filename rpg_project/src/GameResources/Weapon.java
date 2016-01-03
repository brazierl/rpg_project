package GameResources;

import GameResources.Combat.*;
import java.util.Set;

public class Weapon extends Item {    
    private WeaponType type;

    public Weapon(WeaponType type, String name, int weight, int value, Set<Effect> effects, int level) {
        super(name, weight, value, effects, level);
        this.type = type;
    }

    public Weapon(WeaponType type, String name, int level, boolean isRandom) {
        super(name, level);
        this.type = type;
        if(isRandom)
        {
            generateAttributes(level, type);
        }
    }
    private void generateAttributes(int level, WeaponType type)
    {
        switch(type){
            case HEAVY : 
                this.name = "Heavy Canon";
                this.weight = 100;
                this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(1.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(0.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(1*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(0.8*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(1.2*level), Effect.PERMANENT));
            case LIGHT : 
                this.name = "Light Canon";
                this.weight = 50;
                this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(1.2*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(1.2*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(0.8*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(1*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(0.8*level), Effect.PERMANENT));
            case REPAIRING : 
                this.name = "Support Canon";
                this.weight = 80;
                this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(0.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(0.8*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(1.2*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(1.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(1*level), Effect.PERMANENT));
        }
    }
}
