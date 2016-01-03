/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandlers;

import GameResources.Combat.*;
import GameResources.*;

/**
 *
 * @author Louis
 */
public class EventScenario extends Event{

    public EventScenario(){
        
    }
    public void firstEvent(){
        Action a = new Action();
        a.createNewShip();
    }
    public void randomEvent(){
        Console.Display("Random event");
    }

    public void lastEvent() {
        Console.Display("Game over.");
        Action a = new Action();
        a.getWholeStats();
    }
    
    public void lootQuest(){
        Place p = new Place(); 
        p.randomPlace();
        // reprendre randomListItems 
        Quest q = new Quest(p, Ship.randomListShips(), Item.randomListItems());
        // traitement de la quête
        
    }
}
