public class TechShip extends Ship {
    public TechShip(String name, int level, int maxWeight, int MaxHealth)
    {
        super(name,level,maxWeight,MaxHealth);
        this.stats.put(Stats.HEALTH, 80);
        this.stats.put(Stats.DEFENSE, 90);
        this.stats.put(Stats.ENGINEERING, 130);
        this.stats.put(Stats.MANIABILITY, 120);
        this.stats.put(Stats.STRENGTH, 80);
    }
}
