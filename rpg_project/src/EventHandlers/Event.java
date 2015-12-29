package EventHandlers;

import GameResources.Combat.*;
import GameResources.*;
import java.util.*;
import main.Game;
import me.grea.antoine.utils.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author p1509019
 */
public class Event {
    private String description;
    
    public Event(String description) {
        this.description = description;
    }
    public Event(){
        
    }
    
    public String getDescription() {
        return description;
    }
    public void display(){
        Console.Display(description);
    }
    public void action(){
        Action a = new Action();
    }
    public void execute(){
        display();
        action();
    } 
    
}
