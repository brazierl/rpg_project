package polytech.dubraz.controls;

import java.util.ArrayList;
import me.grea.antoine.utils.Menu;
import polytech.dubraz.eventhandlers.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.combat.*;

public class Controller {
    private Ship currentShip;
    private ArrayList<Ship> targets;

    public Controller(Ship currentShip,ArrayList<Ship> targets) {
        this.currentShip = currentShip;
        this.targets = targets;
    }
        
    public Action act() {
        Action action;
        Console m1  = new Console("Choose your action", null, "Attack","Dodge","Repair") {
            @Override
            protected void on(int i) {
                Ability a = null;
                switch(i){
                    case 0 : a = new Attack();
                    case 1 : a = new Dodge();
                    case 2 : a = new Repair();
                }
                Ship target;
                Console m2 = new Console("Choose your target", null, targets.toArray(new String[targets.size()]) ) {
                    
                    @Override
                    protected void on(int t) {
                        action = new Action(a,targets.get(t));
                    }
                };
            }
        };
        return null;
    }
}
