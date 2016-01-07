package polytech.dubraz.gameresources.combat;


import polytech.dubraz.main.Game;
import java.util.Map;
import polytech.dubraz.gameresources.Ship;

public class Action {
    private Ability a;
    private Ship target;
    public Action(Ability a, Ship target) {
        this.a=a;
        this.target = target;
    }
   
    
}
