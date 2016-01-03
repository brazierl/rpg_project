package GameResources;

import GameResources.Combat.*;
import java.util.Set;

public class Armor extends Item {
    private ArmorType type;

    public Armor(ArmorType type, String name, int weight, int value, Set<Effect> effects, int level) {
        super(name, weight, value, effects, level);
        this.type = type;
    }

    public Armor(ArmorType type, String name, int level, boolean isRandom) {
        super(name, level);
        this.type = type;
        if(isRandom)
        {
            generateAttributes(level, type);
        }
    }
    private void generateAttributes(int level, ArmorType type)
    {
        switch(type){
            case HEAVY : 
                this.name = "Heavy Armor";
                this.weight = 200;
                this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(BONUSCOEFF*0*level), -1));
                this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(BONUSCOEFF*0.5*level), -1));
                this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(BONUSCOEFF*1.5*level), -1));
                this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(BONUSCOEFF*0*level), -1));
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(BONUSCOEFF*1.2*level), -1));
            case LIGHT : 
                this.name = "Light Armor";
                this.weight = 100;
                this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(BONUSCOEFF*0*level), -1));
                this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(BONUSCOEFF*1.5*level), -1));
                this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(BONUSCOEFF*0.5*level), -1));
                this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(BONUSCOEFF*0*level), -1));
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(BONUSCOEFF*1.5*level), -1));
        }
    }
}
