/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandlers;

import GameResources.Combat.*;
import GameResources.*;
import java.util.Map;
import main.Game;

/**
 *
 * @author Louis
 */
public class EventScenario extends Event{

    public EventScenario(){
        
    }
    public void firstEvent(){
        createNewShip();
    }
    public void randomEvent(){
        Console.Display("Random event");
    }

    public void lastEvent() {
        Console.Display("Game over.");
        Game.getMainShip().toString();
    }
    
    public void lootQuest(){
        Quest q = new Quest(Place.randomPlace(), Ship.randomListShips(), Item.randomListItems());
        Console.Display("Fight the opponent to loot some item.");
        EventFight f = new EventFight(Game.getMainShip(),q.getShipsToFight());
        if(!q.getPlace().getName().equals(Place.HYPERSPACE))
        {
            if(!q.getPlace().isPeaceful())
            {
                if(f.askForFight())
                    f.startFight();
                else
                {
                    f.runAway();
                    EventTravel et = new EventTravel();
                    et.travelTo(new Place(Place.HYPERSPACE, true, 0));
                }
            }
            else
            {
                if(askForTrade(q.getPlace()))
                {
                    EventTravel et = new EventTravel();
                    et.trade(q.getPlace());
                }
                else
                {
                    EventTravel et = new EventTravel();
                    et.travelTo(et.askForPlaceToTravel(q.getPlace()));
                }
            }
        }
        else{
            EventTravel et = new EventTravel();
            et.travelTo(et.askForPlaceToTravel(q.getPlace()));
        }
    }
    
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

    private boolean askForTrade(Place p) {
        if(p.getName().equals(Place.HYPERSPACE))
            Console.Display("You cannot trade in "+Place.HYPERSPACE+".");
        Console.Display("You are now on "+p.getName()+".");
        return Console.DisplayYN("Would you like to trade items with the ships around ?");        
    }

}
