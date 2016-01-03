/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GameResources.Combat;

import me.grea.antoine.utils.*;

/**
 *
 * @author Louis
 */
public enum UsableType {
    REPAIR, DAMAGE, BONUS;
    public static UsableType randomType(){
        int r = Dice.roll(0,2);
        switch(r){
            case 0 : return REPAIR;
            case 1 : return DAMAGE;
            case 2 : return BONUS;
        }
        return null;
    }
}
