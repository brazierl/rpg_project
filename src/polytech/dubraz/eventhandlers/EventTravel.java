/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polytech.dubraz.eventhandlers;

import polytech.dubraz.gameresources.*;
import polytech.dubraz.main.Game;
import java.util.*;
import me.grea.antoine.utils.Log;
import polytech.dubraz.controls.HumanController;

/**
 *
 * @author Louis
 */
public class EventTravel {
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
            if(!Item.allTradableItem(p.getShips()).isEmpty())
            {
                displayTradableItems(p);
                ArrayList<Integer> list = Console.displayInts("Wich item(s) would you like to take ?");
                if(!list.equals(Console.NOINPUTLIST)){
                    for(int c : list){
                        Game.getMainShip().collect(Item.allTradableItem(p.getShips()).get(c),p.getShips());
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
            if(!Item.allTradableItem(p.getShips()).isEmpty())
            {
                displayTradableItems(p);
                ArrayList<Integer> list1 = Console.displayInts("Wich item(s) would you like to take ?");
                if(!list1.equals(Console.NOINPUTLIST)){
                    displayInventoryShip(Game.getMainShip());
                    ArrayList<Integer> list2 = Console.displayInts("Wich item(s) would you like to give (must be almost the same value (+/-20)) ?");
                    if(!list2.equals(Console.NOINPUTLIST)){
                        try{
                            ArrayList<Item> itemsGiven = new ArrayList<>();
                            ArrayList<Item> itemsTaken = new ArrayList<>();
                            for(int c1 : list1){
                               itemsTaken.add(Item.allTradableItem(p.getShips()).get(c1));
                            }
                            for(int c2 : list2){
                                itemsGiven.add(Game.getMainShip().getInventory().get(c2));
                            }
                            Game.getMainShip().trade(itemsGiven, itemsTaken,p.getShips());
                        }catch(Exception ex){
                            Log.e(ex.getMessage());
                        }
                    }
                }
            }
            else
                Console.display("No tradable objects.");
        }
        else
            Console.display("This place is not peacefull. You cannot trade.");
        this.travelTo(HumanController.askForPlaceToTravel(p));
    }
    
    private static void displayTradableItems(Place p){
        for(int i = 0; i<Item.allTradableItem(p.getShips()).size(); i++)
        {
            Console.display(i+". "+Item.allTradableItem(p.getShips()).get(i));
        }
    }
    
    private static void displayInventoryShip(Ship s){
        for(int j = 0; j<s.getInventory().size(); j++)
        {
            Console.display(j+". "+Game.getMainShip().getInventory().get(j));
        }
    }
   
}
