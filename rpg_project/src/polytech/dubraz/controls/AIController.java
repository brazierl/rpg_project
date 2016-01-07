package polytech.dubraz.controls;


import java.util.ArrayList;
import me.grea.antoine.utils.*;
import polytech.dubraz.controls.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.combat.*;
import polytech.dubraz.main.Game;

public class AIController extends Controller {

    public AIController(Ship currentShip, ArrayList<Ship> targets) {
        super(currentShip, targets);
    }
    
    public Action act(){
        // tire aléatoirement une action 
        int s = Dice.roll(0, targets.size()-1);
        Ship ship = targets.get(s);
        int abi;
        if(ship.equals(Game.getMainShip()))
            abi = Dice.roll(0, 1);
        else
            abi = 2;
        Ability ability = null;
        switch(abi){
            case 0 : ability = new Attack();
            case 1 : ability = new Dodge();
            case 2 : ability = new Repair();
        }
        return new Action(ability,ship);
    }
}
