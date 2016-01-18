package polytech.dubraz.controls;


import java.util.ArrayList;
import polytech.dubraz.eventhandlers.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.combat.*;

public class HumanController extends Controller {

    public HumanController(Ship currentShip, ArrayList<Ship> targets) {
        super(currentShip, targets);
    }
    
    public Action act(){
        Action action = null;
        Console m1  = new Console("Possible actions", "Choice", "Attack","Dodge","Repair") {
            @Override
            protected void on(int i) {
                switch(i){
                    case 0 : valueToReturn = new Attack(currentShip);
                    break;
                    case 1 : valueToReturn = new Dodge(currentShip);
                    break;
                    case 2 : valueToReturn = new Repair(currentShip);
                    break;
                    case 3 : Usable u = selectUsable();
                        currentShip.use(u);
                        Console.display("You use a "+u.getName());
                        Console.display(u.getEffects().toString());
                    break;
                }
            }
        };
        Console m2 = new Console("Choose your target", "Choice", Ship.shipsSimpleToArray(targets)) { 
            private Ship ship;
            @Override
            protected void on(int t) {
                valueToReturn = targets.get(t);
            }
        };
        m1.display();
        m2.display();
        action = new Action((Ability)m1.getValueToReturn(),(Ship)m2.getValueToReturn());
        return action;
    }
    
    private Usable selectUsable(){
        Console c = new Console("Select a usable item", "Choice", Item.usablesToArray(currentShip.getUsables())) {
            @Override
            protected void on(int i) {
                valueToReturn = currentShip.getUsables().get(i);
            }
        };
        return (Usable)c.getValueToReturn();
    }
    
    
}
