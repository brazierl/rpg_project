package polytech.dubraz.gameresources;

public class FrontShip extends Ship {
    public FrontShip(String name, int level, int maxWeight, int MaxHealth)
    {
        super(name,level,maxWeight,MaxHealth);
        autoCalculateStats();
    }

    public FrontShip(String name) {
        this(name,DEFAULTLEVEL,DEFAULTMAXWEIGHT,DEFAULTMAXHEALTH);
        this.wornArmor = (Armor)Armor.randomItem();
        this.wornWeapon = (Weapon)Weapon.randomItem();
    }
    
     public FrontShip(String name, int level)
    {
        super(name, level);
        autoCalculateStats();
    }
    
    protected void autoCalculateStats(){
        this.stats.put(Stats.HEALTH, getValueLevelStat(130));
        this.stats.put(Stats.DEFENSE, getValueLevelStat(120));
        this.stats.put(Stats.ENGINEERING, getValueLevelStat(100));
        this.stats.put(Stats.MANIABILITY, getValueLevelStat(70));
        this.stats.put(Stats.STRENGTH, getValueLevelStat(80));
    }
}
