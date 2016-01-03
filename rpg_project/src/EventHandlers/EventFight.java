package EventHandlers;


import GameResources.Ship;
import java.util.*;
import me.grea.antoine.utils.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author p1509019
 */
public class EventFight extends Event{
    private Ship mainShip;
    private ArrayList<Ship> ships;

    public EventFight(Ship mainShip, ArrayList<Ship> ships) {
        this.mainShip = mainShip;
        this.ships = ships;
    }
    
    public void startFight(){
        Console.Display("Vous entrer en combat.");
        while(mainShip.getHealth()!=0 || areStillAlive()){
            
        }          
    }
    
    private boolean areStillAlive(){
        for(Ship s : ships){
            if(s.getHealth()>0)
                return true;
        }
        return false;
    } 
}
