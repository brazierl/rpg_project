package GameResources.Combat;


import main.Game;
import EventHandlers.*;
import GameResources.*;
import java.util.Map;

public class Action {
    public void createNewShip(){
        String type = "-1";
        String name;
        Ship ship = new Ship();
        while("-1".equals(type))
        {
            Console.Display("Select your ship type (1 : FrontShip, 2 : TechShip, 3 : AssaultShip)");
            type = Console.Read().trim();
            switch(type){
                case "1": 
                    Console.Display("Enter your ship name : ");
                    name = Console.Read();
                    ship = new FrontShip(name);
                    break;
                case "2": 
                    Console.Display("Enter your ship name : ");
                    name = Console.Read();
                    ship = new TechShip(name);
                    break;
                case "3":
                    Console.Display("Enter your ship name : ");
                    name = Console.Read();
                    ship = new AssaultShip(name);
                    break;
                default : type = "-1";
            }
        }
        Game.setMainShip(ship);
    }

    public void getWholeStats() {
        Ship s = Game.getMainShip();
        Console.Display("Your ship " + s.getName() + " is level " + s.getLevel() + ".");
        Console.Display("Your stats are :");
        for(Map.Entry<Stats, Integer> e : s.getStats().entrySet())
        {
            Console.Display("- " + e.getKey()+ " : " + e.getValue());
        }
    }
    
}
