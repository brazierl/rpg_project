package polytech.dubraz.gameresources;

public class TechShip extends Ship {
    public TechShip(String name, int level, int maxWeight, int MaxHealth)
    {
        super(name,level,maxWeight,MaxHealth);
        autoCalculateStats();
    }

    public TechShip(String name) {
        this(name,DEFAULTLEVEL,DEFAULTMAXWEIGHT,DEFAULTMAXHEALTH);
    }
    
    public TechShip(String name, int level)
    {
        super(name, level);
        autoCalculateStats();
    }
    
    @Override
    protected void autoCalculateStats(){
        this.stats.put(Stats.HEALTH, getValueLevelStat(80));
        this.stats.put(Stats.DEFENSE, getValueLevelStat(90));
        this.stats.put(Stats.ENGINEERING, getValueLevelStat(130));
        this.stats.put(Stats.MANIABILITY, getValueLevelStat(120));
        this.stats.put(Stats.STRENGTH, getValueLevelStat(80));
    }
}
