package polytech.dubraz.main;


import polytech.dubraz.eventhandlers.Console;
import polytech.dubraz.gameresources.*;
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
    
    private static Ship mainShip = null;

    public static Ship getMainShip() {
        return mainShip;
    }

    public static void setMainShip(Ship mainShip) {
        Game.mainShip = mainShip;
    }
    public static void main(String[] args) {
        Ship s = Ship.deserializeShip();
        if(s==null)
            new EventScenario();
        else{
            mainShip = s;
            Console.display("Your ship has been restored.");
            new EventScenario(EventScenario.RANDOMQUEST,mainShip.getPlace());
        }
    }
    
}
