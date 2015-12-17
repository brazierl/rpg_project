
import me.grea.antoine.utils.Log;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author p1509019
 */
public class EventFight extends Event{
    private Ship s1;
    private Ship s2;
    public EventFight(Ship s1, Ship s2){
        this.s1=s1;
        this.s2=s2;
    }
    public void execute(){
        Log.i("Vous entrer en combat.");
        while(s1.getHealth()!=0 || s2.getHealth()!=0){
            
        }
                  
      } 
}
