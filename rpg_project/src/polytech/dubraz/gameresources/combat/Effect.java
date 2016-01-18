package polytech.dubraz.gameresources.combat;


import polytech.dubraz.gameresources.Stats;

public class Effect {

    private Stats stat;

    private int value;
    
    public static final int PERMANENT = -1;
    // -1 if permanent
    private int duration;

    public Stats getS() {
        return stat;
    }

    public void setS(Stats s) {
        this.stat = s;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Effect(Stats s, int value, int duration) {
        this.stat = s;
        this.value = value;
        this.duration = duration;
    }

    public Effect() {
    }

    @Override
    public String toString() {
        return stat + " : +" + value + ", DURATION=" + ((duration==PERMANENT)?("permanent"):(duration + " turn(s)"));
    }
   
    public Effect getInvertEffect(){
        return new Effect(stat, -value, duration);
    }
}
