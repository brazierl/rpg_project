public class AssaultShip extends Ship {
    public AssaultShip(String name, int maxWeight, int MaxHealth)
    {
        super(name,maxWeight,MaxHealth);
        this.stats.put(Stats.HEALTH, 100);
        this.stats.put(Stats.DEFENSE, 80);
        this.stats.put(Stats.ENGINEERING, 70);
        this.stats.put(Stats.MANIABILITY, 120);
        this.stats.put(Stats.STRENGTH, 130);
    }
}
