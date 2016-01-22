package polytech.dubraz.controls;


import java.util.ArrayList;
import me.grea.antoine.utils.Log;
import polytech.dubraz.eventhandlers.*;
import polytech.dubraz.gameresources.*;
import polytech.dubraz.gameresources.combat.*;
import polytech.dubraz.main.Game;

public class HumanController extends Controller {

    public HumanController(Ship currentShip, ArrayList<Ship> targets) {
        super(currentShip, targets);
    }
    
    public Action act(){
        Action action = null;
        Console m1  = new Console("Possible actions", "Choice", "Attack","Dodge","Repair") {
            @Override
            protected void on(int i) {
                switch(i){
                    case 0 : valueToReturn = new Attack(currentShip);
                    break;
                    case 1 : valueToReturn = new Dodge(currentShip);
                    break;
                    case 2 : valueToReturn = new Repair(currentShip);
                    break;
                    case 3 : Usable u = selectUsable();
                        currentShip.use(u);
                        Console.display("You use a "+u.getName());
                        Console.display(u.getEffects().toString());
                    break;
                }
            }
        };
        Console m2 = new Console("Choose your target", "Choice", Ship.shipsSimpleToArray(targets)) { 
            private Ship ship;
            @Override
            protected void on(int t) {
                valueToReturn = targets.get(t);
            }
        };
        m1.display();
        m2.display();
        action = new Action((Ability)m1.getValueToReturn(),(Ship)m2.getValueToReturn());
        return action;
    }
    
    private Usable selectUsable(){
        Console c = new Console("Select a usable item", "Choice", Item.usablesToArray(currentShip.getUsables())) {
            @Override
            protected void on(int i) {
                valueToReturn = currentShip.getUsables().get(i);
            }
        };
        return (Usable)c.getValueToReturn();
    }
    
    public static boolean askForFight(ArrayList<Ship> ships) {
       Console.display("Foes :\n" + Ship.shipsToString(ships));
       return Console.displayYN("Would you like to start the fight ?");
   }
   
    public static boolean askForTrade(Place p) {
        if(p.getName().equals(Place.HYPERSPACE))
            Console.display("You cannot trade in "+Place.HYPERSPACE+".");
        Console.display("You are now on "+p.getName()+".");
        return Console.displayYN("Would you like to trade items with the ships around ?");        
    }
    
    public static void manageShip(){
        Console c = new Console("Manage your ship", "Choice", "Inventory", "Save", "Travel") {
            @Override
            protected void on(int i) {
                switch(i){
                    case 0 : displayInventory();
                    break;
                    case 1 : 
                        if(Game.getMainShip().serializeShip())
                            Console.display("---> State saved.");
                        else
                            Console.display("/!\\ Error while saving.");
                    break;
                    default : 
                    break;
                }
            }
        };
        c.display();
    }
    
    public static void manageInventory(Item item){
        Console c = new Console("Inventory", "Choice", "Back", "Drop", "Equip", "Unequip Armor", "Unequip Weapon", "Leave") {
            @Override
            protected void on(int i) {
                switch(i){
                    case 0: displayInventory();
                        break;
                    case 1: Game.getMainShip().dropItem(item);
                        break;
                    case 2: Game.getMainShip().equipItem(item);
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
    
    public static void displayInventory(){
        Console c = new Console("Choose an item", "Choice",Item.itemsToArray(Game.getMainShip().getInventory())) {
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
        manageInventory((Item)c.getValueToReturn());
    }
    
    public static Place askForPlaceToTravel(Place p) {
        while(true){
            try{
                Console.display("Here are your possible destinations : ");
                ArrayList<Place> dests = Place.randomPlaces(p);
                for (int i = 0; i < dests.size(); i++) {
                    Console.display(i+". "+dests.get(i).toString());
                }
                return dests.get(Console.displayInt("Where do you want to travel ?"));
            }catch(Exception ex){
                Log.e(ex.getMessage());
                Console.display("Unavailable destination.");
            }
        }
    }
}
