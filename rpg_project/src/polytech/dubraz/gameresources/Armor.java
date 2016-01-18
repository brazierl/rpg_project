package polytech.dubraz.gameresources;

import polytech.dubraz.gameresources.combat.ArmorType;
import polytech.dubraz.gameresources.combat.Effect;
import java.util.*;
import polytech.dubraz.gameresources.combat.UsableType;
import polytech.dubraz.main.Game;

public class Armor extends Item {
    private ArmorType type;

    public Armor(ArmorType type, String name, int weight, int value, HashSet<Effect> effects, int level) {
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
                this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(BONUSCOEFF*0*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(BONUSCOEFF*0.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(BONUSCOEFF*1.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(BONUSCOEFF*0*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(BONUSCOEFF*1.2*level), Effect.PERMANENT));
                break;
            case LIGHT : 
                this.name = "Light Armor";
                this.weight = 100;
                this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(BONUSCOEFF*0*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(BONUSCOEFF*1.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(BONUSCOEFF*0.5*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(BONUSCOEFF*0*level), Effect.PERMANENT));
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(BONUSCOEFF*1.5*level), Effect.PERMANENT));
                break;
        }
    }
    public static Item randomItem(){
        int level = (Game.getMainShip()!=null)?(Game.getMainShip().getAverageLevel()):1;
        return new Armor(ArmorType.randomType() , "", level, true);
    }
}
