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
        Console m1  = new Console("Choose your action", null, "Attack","Dodge","Repair") {
            @Override
            protected void on(int i) {
                switch(i){
                    case 0 : retour = new Attack(currentShip);
                    case 1 : retour = new Dodge(currentShip);
                    case 2 : retour = new Repair(currentShip);
                }
            }
        };
        Console m2 = new Console("Choose your target", null, targets.toArray(new String[targets.size()]) ) { 
            private Ship ship;
            @Override
            protected void on(int t) {
                retour = targets.get(t);
            }
        };
        m1.display();
        m2.display();
        action = new Action((Ability)m1.getRetour(),(Ship)m2.getRetour());
        return action;
    }
}
