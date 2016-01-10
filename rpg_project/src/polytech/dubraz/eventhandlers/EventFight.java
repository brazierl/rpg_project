package polytech.dubraz.eventhandlers;


import polytech.dubraz.gameresources.Ship;
import java.util.*;
import me.grea.antoine.utils.*;
import polytech.dubraz.main.Game;

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
        Console.display("You are starting a fight.");
        // retourne une liste ordonnée par mania des vaisseaux.
        while(mainShip.getHealth()>0 || areStillAlive()){
            //on appelle un tour
            ArrayList<Ship> list = ships;
            Turn t = new Turn(ships, mainShip);
            t.startTurn();
            mainShip = t.getMain();
            ships = t.getOponents();
            Console.display(mainShip.toString());
            Console.display(ships.toString());
        }   
        EventTravel et = new EventTravel();
        if(mainShip.getHealth()>0)
        {
            Console.display("You win !");
            if(Console.displayYN("Would you like to collect items ?"))
                et.collect(Game.getMainShip().getPlace());
        }
        else{
            Console.display("You loose...");
        }
        et.travelTo(et.askForPlaceToTravel(Game.getMainShip().getPlace()));
    }
    
    private boolean areStillAlive(){
        for(Ship s : ships){
            if(s.getHealth()>0)
                return true;
        }
        return false;
    } 

    public boolean askForFight() {
        Console.display("Foes :" + shipsToString());
        return Console.displayYN("Would you like to start the fight ?");
    }
    
    public String shipsToString(){
        String s = "";
        int i = 0;
        for(Ship sh : ships){
            if(++i==ships.size())
                s += sh.toString();
            else
                s += sh.toString()+", ";
        }
        return s;
    }
    
    public void runAway(){
        Console.display("You choose to run away from the fight.");
    }
}
