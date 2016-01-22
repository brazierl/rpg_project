/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polytech.dubraz.gameresources;

import java.util.ArrayList;

/**
 *
 * @author Louis
 */
public class Quest {
    private Place place;
    private ArrayList<Ship> shipsToFight;
    private ArrayList<Item> reward;
   
    public Quest(Place place, ArrayList<Ship> shipsToFight, ArrayList<Item> reward) {
        this.place = place;
        this.shipsToFight = shipsToFight;
        this.reward = reward;
        this.place.setShips(shipsToFight);
    }
    
    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public ArrayList<Ship> getShipsToFight() {
        return shipsToFight;
    }

    public void setShipsToFight(ArrayList<Ship> shipsToFight) {
        this.shipsToFight = shipsToFight;
    }

    public ArrayList<Item> getReward() {
        return reward;
    }

    public void setReward(ArrayList<Item> reward) {
        this.reward = reward;
    }
}
