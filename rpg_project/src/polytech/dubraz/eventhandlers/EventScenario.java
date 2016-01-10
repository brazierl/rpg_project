/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polytech.dubraz.eventhandlers;

import polytech.dubraz.gameresources.FrontShip;
import polytech.dubraz.gameresources.Quest;
import polytech.dubraz.gameresources.Ship;
import polytech.dubraz.gameresources.Item;
import polytech.dubraz.gameresources.TechShip;
import polytech.dubraz.gameresources.Place;
import polytech.dubraz.gameresources.AssaultShip;
import java.util.Map;
import polytech.dubraz.main.Game;

/**
 *
 * @author Louis
 */
public class EventScenario extends Event{
    public static final String RANDOMQUEST = "random";
    public static final String DEFENDPLANET = "planet";
    /*public static final String ?;
    public static final String ?;
    public static final String ?;*/
    
    public EventScenario(){
        createNewShip();
        Console.display(Game.getMainShip().toString());
        EventTravel et = new EventTravel();
        et.travelTo(new Place(Place.HYPERSPACE, true, Game.getMainShip().getLevel()));
    }
    
    public EventScenario(String type){
        switch(type){
            case RANDOMQUEST : 
                randomQuest();
                break;
            case DEFENDPLANET:
                
                break;
        }
    }
    
    public EventScenario(String type, Place p){
        switch(type){
            case RANDOMQUEST : 
                randomQuest(p);
                break;
            case DEFENDPLANET:
                
                break;
        }
    }

    private void lastEvent() {
        Console.display("Game over.");
        Game.getMainShip().toString();
    }
    
    private void randomQuest(){
        Quest q = new Quest(Place.randomPlace(), Ship.randomListShips(), Item.randomListItems());
        executeQuest(q);
    }
    
    private void randomQuest(Place p){
        Quest q = new Quest(p, Ship.randomListShips(), Item.randomListItems());
        executeQuest(q);
    }
    
    private void executeQuest(Quest q)
    {
        EventFight f = new EventFight(Game.getMainShip(),q.getShipsToFight());
        if(!Game.getMainShip().getPlace().getName().equals(Place.HYPERSPACE))
        {
            if(!q.getPlace().isPeaceful())
            {
                Console.display("Fight the opponent to loot some items.");
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
                Console.display("Travelling...");
                if(askForTrade(Game.getMainShip().getPlace()))
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
    
     private void createNewShip(){
        int type = -1;
        String name;
        Ship ship = new Ship();
            type = Console.displayInt("Select your ship type (1 : FrontShip, 2 : TechShip, 3 : AssaultShip)");
        switch(type){
            case 1: 
                Console.display("Enter your ship name : ");
                name = Console.read();
                ship = new FrontShip(name);
                break;
            case 2: 
                Console.display("Enter your ship name : ");
                name = Console.read();
                ship = new TechShip(name);
                break;
            case 3:
                Console.display("Enter your ship name : ");
                name = Console.read();
                ship = new AssaultShip(name);
                break;
            default : type = -1;
        }
        Game.setMainShip(ship);
    }

    private boolean askForTrade(Place p) {
        if(p.getName().equals(Place.HYPERSPACE))
            Console.display("You cannot trade in "+Place.HYPERSPACE+".");
        Console.display("You are now on "+p.getName()+".");
        return Console.displayYN("Would you like to trade items with the ships around ?");        
    }

}
