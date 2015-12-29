package GameResources;

public class AssaultShip extends Ship {
    public AssaultShip(String name, int level , int maxWeight, int MaxHealth)
    {
        super(name,level,maxWeight,MaxHealth);
        autoCalculateStats();
    }

    public AssaultShip(String name) {
        this(name,DEFAULTLEVEL,DEFAULTMAXWEIGHT,DEFAULTMAXHEALTH);
    }
    
    public AssaultShip(String name, int level)
    {
        super(name, level);
        autoCalculateStats();
    }
    @Override
    protected void autoCalculateStats(){
        this.stats.put(Stats.HEALTH, getValueLevelStat(100));
        this.stats.put(Stats.DEFENSE, getValueLevelStat(80));
        this.stats.put(Stats.ENGINEERING, getValueLevelStat(70));
        this.stats.put(Stats.MANIABILITY, getValueLevelStat(120));
        this.stats.put(Stats.STRENGTH, getValueLevelStat(130));
    }
}
