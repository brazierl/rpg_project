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
    
    protected String[] shipsToArray(ArrayList<Ship> ships){
        String[] list = new String[ships.size()];
        int i=0;
        for(Ship s : ships){
            list[i] = s.toString();
            i++;
        }
        return list;
    }
}
