/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polytech.dubraz.eventhandlers;

import polytech.dubraz.gameresources.Item;
import polytech.dubraz.gameresources.Place;
import polytech.dubraz.main.Game;
import java.util.*;
import me.grea.antoine.utils.Log;

/**
 *
 * @author Louis
 */
public class EventTravel extends Event {
    private Set<Place> destinations;

    public EventTravel(){
        
    }

    public Set<Place> getDestinations() {
        return destinations;
    }

    public void setDestinations(Set<Place> destinations) {
        this.destinations = destinations;
    }
    
    public void travelTo(Place p){
        Game.getMainShip().setPlace(p);
        Console.display("You are now travelling to "+p.getName()+".");
        EventScenario es = new EventScenario(EventScenario.RANDOMQUEST,p);
    }
    public void collect(Place p)
    {
        if(!p.isPeaceful())
        {
            if(!p.allTradableItem().isEmpty())
            {
                for(int i = 0; i<p.allTradableItem().size(); i++)
                {
                    Console.display(i+". "+p.allTradableItem().get(i));
                }
                ArrayList<Integer> list = Console.displayInts("Wich item(s) would you like to take ?");
                if(!list.equals(Console.NOINPUTLIST)){
                    for(int c : list){
                        p.collect(p.allTradableItem().get(c));
                    }
                }
            }
            else
                Console.display("No tradable objects.");
        }
        else
            Console.display("This place is peacefull. You cannot collect.");
    }
    public void trade(Place p)
    {
        if(p.isPeaceful())
        {
            if(!p.allTradableItem().isEmpty())
            {
                for(int i = 0; i<p.allTradableItem().size(); i++)
                {
                    Console.display(i+". "+p.allTradableItem().get(i));
                }
                ArrayList<Integer> list1 = Console.displayInts("Wich item(s) would you like to take ?");
                if(!list1.equals(Console.NOINPUTLIST)){
                    for(int i = 0; i<Game.getMainShip().getInventory().size(); i++)
                    {
                        Console.display(i+". "+Game.getMainShip().getInventory().get(i));
                    }
                    ArrayList<Integer> list2 = Console.displayInts("Wich item(s) would you like to give (must be almost the same value (+/-20) ?");
                    if(!list2.equals(Console.NOINPUTLIST)){
                        ArrayList<Item> itemsGiven = new ArrayList<>();
                        ArrayList<Item> itemsTaken = new ArrayList<>();
                        for(int c1 : list1){
                           itemsTaken.add(p.allTradableItem().get(c1));
                        }
                        for(int c2 : list2){
                            itemsGiven.add(p.allTradableItem().get(c2));
                        }
                        p.trade(itemsGiven, itemsTaken);
                    }
                }
            }
            else
                Console.display("No tradable objects.");
        }
        else
            Console.display("This place is not peacefull. You cannot trade.");
    }

    public Place askForPlaceToTravel(Place p) {
        try{
            Console.display("You should travel to another place.");
            Console.display("Here are your possible destination : ");
            ArrayList<Place> dests = Place.randomPlaces(p);
            
            for (int i = 0; i < dests.size(); i++) {
                Console.display(i+". "+dests.get(i).toString());
            }
            return dests.get(Console.displayInt("Where do you want to travel ?"));
        }catch(Exception ex){
            Log.e(ex.getMessage());
            Console.display("Unavailable destination.");
            return askForPlaceToTravel(p);
        }
    }
}
