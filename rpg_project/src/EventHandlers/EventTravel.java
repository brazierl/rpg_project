/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandlers;

import GameResources.*;
import java.util.*;
import main.*;
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
        Game.getMainShip().setP(p);
        Console.Display("You are now travelling to "+p.getName()+".");
    }
    public void collect(Place p)
    {
        if(!p.isPeaceful())
        {
            if(!p.allTradableItem().isEmpty())
            {
                for(int i = 0; i<p.allTradableItem().size(); i++)
                {
                    Console.Display(i+". "+p.allTradableItem().get(i));
                }
                ArrayList<Integer> list = Console.DisplayInts("Wich item(s) would you like to take ?");
                if(!list.equals(Console.NOINPUTLIST)){
                    for(int c : list){
                        p.collect(p.allTradableItem().get(c));
                    }
                }
            }
            else
                Console.Display("No tradable objects.");
        }
        else
            Console.Display("This place is peacefull. You cannot collect.");
    }
    public void trade(Place p)
    {
        if(p.isPeaceful())
        {
            if(!p.allTradableItem().isEmpty())
            {
                for(int i = 0; i<p.allTradableItem().size(); i++)
                {
                    Console.Display(i+". "+p.allTradableItem().get(i));
                }
                ArrayList<Integer> list1 = Console.DisplayInts("Wich item(s) would you like to take ?");
                if(!list1.equals(Console.NOINPUTLIST)){
                    for(int i = 0; i<Game.getMainShip().getInventory().size(); i++)
                    {
                        Console.Display(i+". "+Game.getMainShip().getInventory().get(i));
                    }
                    ArrayList<Integer> list2 = Console.DisplayInts("Wich item(s) would you like to give (must be almost the same value (+/-20) ?");
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
                Console.Display("No tradable objects.");
        }
        else
            Console.Display("This place is not peacefull. You cannot trade.");
    }

    public Place askForPlaceToTravel(Place p) {
        try{
            Console.Display("You should travel to another place.");
            Console.Display("Here are your possible destination : ");
            ArrayList<Place> dests = Place.randomPlaces(p);
            for (int i = 0; i < dests.size(); i++) {
                Console.Display(i+". "+dests.get(i).toString());
            }
            return dests.get(Console.DisplayInt("Where do you wnat to travel ?"));
        }catch(Exception ex){
            Log.e(ex.getMessage());
            Console.Display("Not available destination.");
            return askForPlaceToTravel(p);
        }
    }
}
