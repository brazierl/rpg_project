public class FrontShip extends Ship {
    public FrontShip(String name, int maxWeight, int MaxHealth)
    {
        super(name,maxWeight,MaxHealth);
        this.stats.put(Stats.HEALTH, 130);
        this.stats.put(Stats.DEFENSE, 120);
        this.stats.put(Stats.ENGINEERING, 100);
        this.stats.put(Stats.MANIABILITY, 70);
        this.stats.put(Stats.STRENGTH, 80);
    }
}
