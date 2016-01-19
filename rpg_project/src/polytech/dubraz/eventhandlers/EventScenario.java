/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package polytech.dubraz.eventhandlers;

import polytech.dubraz.gameresources.FrontShip;
import polytech.dubraz.gameresources.Quest;
import polytech.dubraz.gameresources.Ship;
import polytech.dubraz.gameresources.Item;
import polytech.dubraz.gameresources.TechShip;
import polytech.dubraz.gameresources.Place;
import polytech.dubraz.gameresources.AssaultShip;
import java.util.Map;
import me.grea.antoine.utils.Log;
import polytech.dubraz.main.Game;

/**
 *
 * @author Louis
 */
public class EventScenario extends Event{
    public static final String RANDOMQUEST = "random";
    public static final String DEFENDPLANET = "planet";
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
            case DEFENDPLANET:
                
                break;
        }
    }
    
    public EventScenario(String type, Place p){
        switch(type){
            case RANDOMQUEST : 
                randomQuest(p);
                break;
            case DEFENDPLANET:
                
                break;
        }
    }

    private void lastEvent() {
        Console.display("Game over.");
        Game.getMainShip().toString();
    }
    
    private void randomQuest(){
        Quest q = new Quest(Game.getMainShip().getPlace(), Game.getMainShip().getPlace().getShips(), Item.randomListItems());
        executeQuest(q);
    }
    
    private void randomQuest(Place p){
        Quest q = new Quest(p, p.getShips(), Item.randomListItems());
        executeQuest(q);
    }
    
    private void executeQuest(Quest q)
    {
        EventFight f = new EventFight(Game.getMainShip(),q.getShipsToFight());
        if(!Game.getMainShip().getPlace().getName().equals(Place.HYPERSPACE))
        {
            if(!q.getPlace().isPeaceful())
            {
                Console.display("Fight the opponent to loot some items.");
                if(f.askForFight())
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
                if(askForTrade(Game.getMainShip().getPlace()))
                {
                    EventTravel et = new EventTravel();
                    et.trade(q.getPlace());
                }
                else
                {
                    EventTravel et = new EventTravel();
                    et.travelTo(et.askForPlaceToTravel(q.getPlace()));
                }
            }
        }
        else{
            boolean test = false;
            while(!test){
                manageShip();
                test = Console.displayYN("Are you satisfied with this build ?");
            }
            EventTravel et = new EventTravel();
            et.travelTo(et.askForPlaceToTravel(q.getPlace()));
        }
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

    private boolean askForTrade(Place p) {
        if(p.getName().equals(Place.HYPERSPACE))
            Console.display("You cannot trade in "+Place.HYPERSPACE+".");
        Console.display("You are now on "+p.getName()+".");
        return Console.displayYN("Would you like to trade items with the ships around ?");        
    }
    
    private void manageShip(){
        Console c = new Console("Manage your ship", "Choice", "Inventory", "Travel") {
            @Override
            protected void on(int i) {
                switch(i){
                    case 0 : manageInventory();
                    break;
                    default : 
                    break;
                }
            }
        };
        c.display();
    }
    
    private void manageInventory(){
        Console c = new Console("Inventory", "Choice", "See", "Drop", "Equip", "Unequip Armor", "Unequip Weapon", "Leave") {
            @Override
            protected void on(int i) {
                switch(i){
                    case 0: displayInventory();
                        break;
                    case 1: Game.getMainShip().dropItem(displayInventory());
                        break;
                    case 2: Game.getMainShip().equipItem(displayInventory());
                        break;
                    case 3: Game.getMainShip().unequipArmor();
                        break;
                    case 4: Game.getMainShip().unequipWeapon();
                        break;
                    default:
                        break;
                }
            }
        };
        c.display();
    }
    
    private Item displayInventory(){
        Console c = new Console("Choose an item", "Choice", Item.itemsToArray(Game.getMainShip().getInventory())) {
            @Override
            protected void on(int i) {
                try{
                    valueToReturn = Game.getMainShip().getInventory().get(i);
                }catch(Exception e){
                    Log.e(e.getMessage());
                    valueToReturn = null;
                }
            }
        };
        c.display();
        return (Item)c.valueToReturn;
    }

}
