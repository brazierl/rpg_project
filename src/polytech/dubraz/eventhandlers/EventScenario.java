/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polytech.dubraz.eventhandlers;

import polytech.dubraz.gameresources.*;
import polytech.dubraz.controls.HumanController;
import polytech.dubraz.main.Game;

/**
 *
 * @author Louis
 */
public class EventScenario {
    public static final String RANDOMQUEST = "random";
    /*public static final String ?;
    public static final String ?;
    public static final String ?;*/
    
    public EventScenario(){
        createNewShip();
        Console.display(Game.getMainShip().toString());
        EventTravel et = new EventTravel();
        et.travelTo(new Place(Place.HYPERSPACE, true, Game.getMainShip().getLevel()));
    }
    
    public EventScenario(String type){
        switch(type){
            case RANDOMQUEST : 
                randomQuest();
                break;
        }
    }
    
    public EventScenario(String type, Place p){
        switch(type){
            case RANDOMQUEST : 
                randomQuest(p);
                break;
        }
    }
    
    private void randomQuest(){
        Quest q = new Quest(Game.getMainShip().getPlace(), Game.getMainShip().getPlace().getShips(), Item.randomListItems());
        executeQuest(q);
    }
    
    private void randomQuest(Place p){
        Quest q = new Quest(p, p.getShips(), Item.randomListItems());
        executeQuest(q);
    }
    
    private void createNewShip(){
        String name;
        Ship ship = new Ship();
        Console.display("  _____ ______   ____  ____    _____ __ __  ____  ____        ___   __ __    ___  _____ ______ \n" +
" / ___/|      | /    ||    \\  / ___/|  |  ||    ||    \\      /   \\ |  |  |  /  _]/ ___/|      |\n" +
"(   \\_ |      ||  o  ||  D  )(   \\_ |  |  | |  | |  o  )    |     ||  |  | /  [_(   \\_ |      |\n" +
" \\__  ||_|  |_||     ||    /  \\__  ||  _  | |  | |   _/     |  Q  ||  |  ||    _]\\__  ||_|  |_|\n" +
" /  \\ |  |  |  |  _  ||    \\  /  \\ ||  |  | |  | |  |       |     ||  :  ||   [_ /  \\ |  |  |  \n" +
" \\    |  |  |  |  |  ||  .  \\ \\    ||  |  | |  | |  |       |     ||     ||     |\\    |  |  |  \n" +
"  \\___|  |__|  |__|__||__|\\_|  \\___||__|__||____||__|        \\__,_| \\__,_||_____| \\___|  |__|  \n" +
"                                                                                               ");
        Console.display("You are now starting the most awesome console game you have ever played."+
                " \nIn a near future, in our universe, space travels are common. After years of hard work, you can finally afford your own ship."+
                "\nYou dream about amazing space fights, cool loots and conquer the galaxy."+
                "\nIn the galaxy, three types of ships are used, with there own specialities : protection, power and support.");
        int type = Console.displayInt("Which type of ship would you like to buy ? (1 : FrontShip, 2 : TechShip, 3 : AssaultShip)");
        switch(type){
            case 1: 
                Console.display("Enter your ship name : ");
                name = Console.read();
                ship = new FrontShip(name);
                break;
            case 2: 
                Console.display("Enter your ship name : ");
                name = Console.read();
                ship = new TechShip(name);
                break;
            case 3:
                Console.display("Enter your ship name : ");
                name = Console.read();
                ship = new AssaultShip(name);
                break;
        }
        Game.setMainShip(ship);
    }
    
    
    private void executeQuest(Quest q)
    {
        EventFight f = new EventFight(Game.getMainShip(),q.getShipsToFight());
        if(!Game.getMainShip().getPlace().getName().equals(Place.HYPERSPACE))
        {
            if(!q.getPlace().isPeaceful())
            {
                Console.display("Fight the opponent to loot some items.");
                if(HumanController.askForFight(f.getShips()))
                    f.startFight();
                else
                {
                    f.runAway();
                    EventTravel et = new EventTravel();
                    et.travelTo(new Place(Place.HYPERSPACE, true, 0));
                }
            }
            else
            {
                Console.display("Travelling...");
                if(HumanController.askForTrade(Game.getMainShip().getPlace()))
                {
                    EventTravel et = new EventTravel();
                    et.trade(q.getPlace());
                }
                else
                {
                    EventTravel et = new EventTravel();
                    et.travelTo(HumanController.askForPlaceToTravel(q.getPlace()));
                }
            }
        }
        else{
            boolean test = false;
            while(!test){
                HumanController.manageShip();
                test = Console.displayYN("Are you satisfied with this build ?");
            }
            EventTravel et = new EventTravel();
            et.travelTo(HumanController.askForPlaceToTravel(q.getPlace()));
        }
    }
   

}
