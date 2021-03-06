package polytech.dubraz.gameresources;

import polytech.dubraz.gameresources.combat.UsableType;
import polytech.dubraz.gameresources.combat.Effect;
import java.util.*;
import me.grea.antoine.utils.Dice;
import polytech.dubraz.gameresources.combat.WeaponType;
import polytech.dubraz.main.Game;

public class Usable extends Item {
    private UsableType type;
    private boolean isCurrentlyUsed;
    
    public UsableType getType() {
        return type;
    }

    public void setType(UsableType type) {
        this.type = type;
    }
    
    public boolean isCurrentlyUsed() {
        return isCurrentlyUsed;
    }

    public void setIsCurrentlyUsed(boolean isCurrentlyUsed) {
        this.isCurrentlyUsed = isCurrentlyUsed;
    }

    public Usable(UsableType type, String name, int weight, int value, HashSet<Effect> effects, int level) {
        super(name, weight, value, effects, level);
        this.type = type;
        isCurrentlyUsed = false;
    }

    public Usable(UsableType type, String name, int level, boolean isRandom) {
        super(name, level);
        this.type = type;
        isCurrentlyUsed = false;
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
                break;
            case DAMAGE : 
                this.name = "Damage bot";
                this.weight = 1;
                this.value = 20;
                this.effects.add(new Effect(Stats.HEALTH, (int)Math.round(0.8*level), duration));
                break;
            case BONUS :
                int r2 = Dice.roll(1,3);
                String n = generateBonus(r2, duration);
                this.value = 20*(r2-1);
                this.name = n+"bot";
                this.weight = 1*(r2-1);
                break;
        }
    }
    
    private String generateBonus(int nb, int duration){
        String n = "";
        int i = 0;
        while(i<nb)
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
        return n;
    }
             
             
    public static Item randomItem(){
        int level = (Game.getMainShip()!=null)?(Game.getMainShip().getAverageLevel()):1;
        return new Usable(UsableType.randomType() , "", level, true);
    }
    
    public void decrementTurn(){
        if(isCurrentlyUsed){
            for(Effect e : effects){
                int d = e.getDuration();
                if(e.getDuration()>1)
                    e.setDuration(d--);
                else if(e.getDuration()==1)
                    effects.remove(e);
            }
        }
    }
    
    
}
