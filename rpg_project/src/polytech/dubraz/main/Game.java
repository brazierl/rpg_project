package polytech.dubraz.main;


import polytech.dubraz.gameresources.Ship;
import polytech.dubraz.eventhandlers.EventScenario;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Louis
 */
public class Game {

    /**
     * @param args the command line arguments
     */
    private static Ship mainShip;
    private static Ship currentPlace;
    private static boolean isOver = false;
    
    public static Ship getCurrentPlace() {
        return currentPlace;
    }

    public static void setCurrentPlace(Ship currentPlace) {
        Game.currentPlace = currentPlace;
    }

    public static Ship getMainShip() {
        return mainShip;
    }

    public static void setMainShip(Ship mainShip) {
        Game.mainShip = mainShip;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        EventScenario es = new EventScenario();
        es.firstEvent();
        while(!isOver)
        {
            EventScenario e = new EventScenario();
            e.randomEvent();
        }
        es.lastEvent();
    }
    
}
