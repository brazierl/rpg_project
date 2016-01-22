/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polytech.dubraz.gameresources.combat;

import me.grea.antoine.utils.*;

/**
 *
 * @author Louis
 */
public enum ArmorType {
    HEAVY, LIGHT;
    public  static ArmorType randomType(){
        int r = Dice.roll(0,1);
        switch(r){
            case 0 : return HEAVY;
            case 1 : return LIGHT;
        }
        return null;
    }
}
