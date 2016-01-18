package polytech.dubraz.controls;

import java.util.ArrayList;
import me.grea.antoine.utils.*;
import polytech.dubraz.eventhandlers.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.combat.*;

public abstract class Controller {
    protected Ship currentShip;
    protected ArrayList<Ship> targets;
    
    public Controller(Ship currentShip,ArrayList<Ship> targets) {
        this.currentShip = currentShip;
        this.targets = targets;
    }
        
    public Action act(){
        return null;
    }
}
