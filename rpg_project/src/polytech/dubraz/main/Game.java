package polytech.dubraz.main;


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

    /**
     * @param args the command line arguments
     */
    private static Ship mainShip = null;

    public static Ship getMainShip() {
        return mainShip;
    }

    public static void setMainShip(Ship mainShip) {
        Game.mainShip = mainShip;
    }
    public static void main(String[] args) {
        // TODO code application logic here
        new EventScenario();
        new EventScenario(EventScenario.RANDOMQUEST); 
        
    }
    
}
