package polytech.dubraz.eventhandlers;

import java.util.*;
import java.util.Map.Entry;
import polytech.dubraz.controls.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.combat.*;
import polytech.dubraz.main.*;

public class Turn {
    private ArrayList<Ship> ships;
    private HashMap<Ship,Effect> dodgeToCancel = new HashMap<>();
    
    private ArrayList<Ship> oponents;
    private Ship main;
    
    public Turn(ArrayList<Ship> oponents, Ship main){
        this.oponents = oponents;
        this.main = main;
        this.ships = oponents;
        ships.add(main);
    }

    public ArrayList<Ship> getOponents() {
        return oponents;
    }

    public Ship getMain() {
        return main;
    }
    
    private ArrayList<Ship> getSortedShips()
    {
        // bubble sort
        int longueur=ships.size();
        boolean permut;
        ArrayList<Ship> list = ships;
        do
        {
            // hypothesis : array is sorted
            permut=false;
            for (int i=0 ; i<longueur-1 ; i++)
            {
                // verify order
                if (list.get(i).getStat(Stats.MANIABILITY) > list.get(i+1).getStat(Stats.MANIABILITY))
                {
                    // permutation if not
                    list = exchange(list,i,i+1);
                    permut=true;
                }
            }
        }
        while(permut);
        return list;
    }
    
    private ArrayList<Ship> exchange(ArrayList<Ship> list, int pos1, int pos2){
        Ship s1 = list.get(pos1);
        Ship s2 = list.get(pos2);
        list.set(pos1, s2);
        list.set(pos2, s1);        
        return list;
    }
    
    public void startTurn(){
        ArrayList<Ship> list = getSortedShips();
        for (Ship s : list){
            if(s.getHealth()>0)
            {
                Controller c;
                if(s.equals(Game.getMainShip()))
                    c = new HumanController(s,list);
                else
                    c = new AIController(s,list);
                Action a = c.act();
                Ship target = a.getTarget();
                a.applyEffects();
                if(a.getA() instanceof Dodge)
                    dodgeToCancel.put(a.getTarget(), a.getA().getEffect(Stats.DEFENSE));
            }
            s.effectTurnsDecrement();
        }
        for(Entry<Ship,Effect> es : dodgeToCancel.entrySet()){
            Effect effect = es.getValue();
            effect.setValue(-effect.getValue());
            es.getKey().applyEffect(effect);
        }
    }
}
