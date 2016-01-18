package polytech.dubraz.controls;


import java.util.ArrayList;
import me.grea.antoine.utils.*;
import polytech.dubraz.controls.*;
import polytech.dubraz.eventhandlers.Console;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.combat.*;
import polytech.dubraz.main.Game;

public class AIController extends Controller {

    public AIController(Ship currentShip, ArrayList<Ship> targets) {
        super(currentShip, targets);
    }
    
    @Override
    public Action act(){
        // tire aléatoirement une action 
        boolean test = true;
        Ship ship=null;
        while(test){
            int s = Dice.roll(-4, targets.size()-1);
            if(s<0)
                ship = Game.getMainShip();
            else
                ship = targets.get(s);
            test = ship.getMaxHealth()<=0;
        }        
        
        int abi;
        Usable u = null;
        if(ship.equals(Game.getMainShip()))
            abi = Dice.roll(0, 1);
        else{
            if(currentShip.getUsables().size()>0){
                abi = Dice.roll(2, 3);
                int usable = Dice.roll(0,currentShip.getUsables().size()-1);
                u = currentShip.getUsables().get(usable);
            }
            else
                abi = 2;
        }
        
        Ability ability = null;
        String abName = "";
        switch(abi){
            case 0 : ability = new Attack(currentShip);
                abName = "attack";
                Console.display(currentShip.getName()+" : attack on "+ship.getName());
                break;
            case 1 : ability = new Dodge(currentShip);
                abName = "dodge";
                Console.display(currentShip.getName()+" : dodge attacks this turn");
                break;
            case 2 : ability = new Repair(currentShip);
                abName = "repair";
                Console.display(currentShip.getName()+" : repair "+ship.getName());
                break;
            case 3 : currentShip.use(u);
                Console.display(currentShip.getName()+" : use a "+u.getName());
                Console.display(u.getEffects().toString());
                break;
        }
        return new Action(ability,ship);
    }
}
