package GameResources.Combat;


import GameResources.Stats;

public class Effect {

    private Stats s;

    private int value;

    // -1 if permanent
    private int duration;

    public Stats getS() {
        return s;
    }

    public void setS(Stats s) {
        this.s = s;
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
        this.s = s;
        this.value = value;
        this.duration = duration;
    }

    public Effect() {
    }
   
    
}
