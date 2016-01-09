package polytech.dubraz.eventhandlers;

import java.util.*;
import polytech.dubraz.controls.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.main.*;

public class Turn {
    // Permet de gérer les actions des vaisseaux
    private ArrayList<Ship> ships;
    
    private ArrayList<Ship> oponents;
    private Ship main;
    
    public Turn(ArrayList<Ship> oponents, Ship main){
        this.oponents = oponents;
        this.main = main;
        this.ships = oponents;
        ships.add(main);
    }

    public ArrayList<Ship> getOponents() {
        return oponents;
    }

    public Ship getMain() {
        return main;
    }
    
    /*public ArrayList<Ship> sortList(){
        ; A FAIRE SI ON A LE TEMPS 
    }
    */
    
    public void startTurn(){
        for (Ship s : ships){
            if(s.getHealth()>0)
            {
                Controller c;
                if(s.equals(Game.getMainShip()))
                    c = new HumanController(s,ships);
                else
                    c = new AIController(s,ships);
            }
        }
    }
}
