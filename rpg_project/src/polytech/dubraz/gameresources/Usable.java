package polytech.dubraz.gameresources;

import polytech.dubraz.gameresources.combat.UsableType;
import polytech.dubraz.gameresources.combat.Effect;
import java.util.*;
import me.grea.antoine.utils.Dice;
import polytech.dubraz.gameresources.combat.WeaponType;
import polytech.dubraz.main.Game;

public class Usable extends Item {
    private UsableType type;
    
    public UsableType getType() {
        return type;
    }

    public void setType(UsableType type) {
        this.type = type;
    }

    public Usable(UsableType type, String name, int weight, int value, HashSet<Effect> effects, int level) {
        super(name, weight, value, effects, level);
        this.type = type;
    }

    public Usable(UsableType type, String name, int level, boolean isRandom) {
        super(name, level);
        this.type = type;
        if(isRandom)
        {
            generateAttributes(level, type);
        }
    }
    private void generateAttributes(int level, UsableType type)
    {
        int duration = Dice.roll(1,3);
        switch(type){
            case REPAIR : 
                this.name = "Repairing bot";
                this.weight = 1;
                this.value = 20;
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(1.2*level), Effect.PERMANENT));
            case DAMAGE : 
                this.name = "Damage bot";
                this.weight = 1;
                this.value = 20;
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(0.8*level), duration));
            case BONUS :
                int r2 = Dice.roll(1,3);
                int i = 0;
                String n = "";
                while(i<r2)
                {
                    int r1 = Dice.roll(0,4);
                    switch(r1)
                    {
                        case 0 :
                            n+= "Manibility";
                            this.effects.add(new Effect(Stats.MANIABILITY, (int)Math.round(BONUSCOEFF*1*level), duration));
                            break;
                        case 1 :
                            n+= "Defense";
                            this.effects.add(new Effect(Stats.DEFENSE, (int)Math.round(BONUSCOEFF*1*level), duration));
                            break;
                        case 2 :
                            n+= "Engineering";
                            this.effects.add(new Effect(Stats.ENGINEERING, (int)Math.round(BONUSCOEFF*1*level), duration));
                            break;
                        case 3 :
                            n+= "Strength";
                            this.effects.add(new Effect(Stats.STRENGTH, (int)Math.round(BONUSCOEFF*1*level), duration));
                            break;
                        case 4 :
                            n+= "Healing";
                            this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(BONUSCOEFF*1*level), duration));
                            break;
                    }
                    i++;
                    n+=" ";
                }
                this.value = 20*i;
                this.name = n+"bot";
                this.weight = 1*i;
        }
    }
    public static Item randomItem(){
        int level = (Game.getMainShip()!=null)?(Game.getMainShip().getAverageLevel()):1;
        return new Usable(UsableType.randomType() , "", level, true);
    }
}
