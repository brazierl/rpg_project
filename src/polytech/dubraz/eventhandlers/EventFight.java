package polytech.dubraz.eventhandlers;


import polytech.dubraz.gameresources.Ship;
import java.util.*;
import me.grea.antoine.utils.*;
import polytech.dubraz.controls.HumanController;
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
public class EventFight{
    private Ship mainShip;
    private ArrayList<Ship> ships;
    public EventFight(Ship mainShip, ArrayList<Ship> ships) {
        this.mainShip = mainShip;
        this.ships = ships;
    }

    public Ship getMainShip() {
        return mainShip;
    }

    public ArrayList<Ship> getShips() {
        return ships;
    }

    public void startFight(){
        Console.display("You are starting a fight.");
        while(mainShip.getHealth()>0 && areStillAlive()){
            // Create a turn while no team is defeated
            ArrayList<Ship> list = ships;
            Turn t = new Turn(ships, mainShip);
            t.startTurn();
            mainShip = t.getMain();
            t.getOponents().remove(mainShip);
            ships = t.getOponents();
            Console.display(mainShip.toString());
            Console.display(Ship.shipsToStringFight(ships));
            Turn.incrementNoTrn();
        }   
        Turn.reinitNoTrn();
        EventTravel et = new EventTravel();
        if(mainShip.getHealth()>0)
        {
            Console.display("You win !");
            int xp = 0;
            for(int i=0; i<ships.size(); i++){
                xp += ships.get(i).getXp();
            }
            xp *= 200;
            Game.getMainShip().addXp(xp);
            if(Console.displayYN("Would you like to collect items ?"))
                et.collect(Game.getMainShip().getPlace());
        }
        else{
            Console.display("You loose...");
        }
        mainShip.endOfFight();
        et.travelTo(HumanController.askForPlaceToTravel(Game.getMainShip().getPlace()));
    }
    
    private boolean areStillAlive(){
        for(Ship s : ships){
            if(s.getHealth()>0)
                return true;
        }
        return false;
    } 
    
    public void runAway(){
        Console.display("You choose to run away from the fight.");
    }
}
