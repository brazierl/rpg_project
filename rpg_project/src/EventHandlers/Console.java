/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import me.grea.antoine.utils.Log;

/**
 *
 * @author Louis
 */
public class Console {
    public static final ArrayList<Integer> NOINPUTLIST = new ArrayList<Integer>(){{
            add(-1);
    }};
    public static final Integer NOINPUTINT = -1;
    
    public static void Display(String s){
        System.out.println(s);
    }
    public static String Read(){
        try{
	    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	    String s = bufferRead.readLine();
	      
	    return s;
	}
	catch(IOException e)
	{
            Log.e(e.getMessage());
            Display("Wrong input.");
            return "error";
        }
    } 

    public static boolean DisplayYN(String question) {
        Console.Display(question+" (Y/N)");
        while(true)
        {
            String answer = Read();
            if("Y".equals(answer) || "y".equals(answer) || "YES".equals(answer) || "Yes".equals(answer) || "yes".equals(answer))
                return true;
            else if("N".equals(answer) || "n".equals(answer) || "NO".equals(answer) || "No".equals(answer) || "no".equals(answer))
                return false;
        } 
    }
    
    public static ArrayList<Integer> DisplayInts(String question) {
        Console.Display(question+" (1,3,7...)");
        try {
            ArrayList<Integer> list = new ArrayList<>();
            String answer = Read().trim();
            if(answer == null)
                return NOINPUTLIST;
            for(String s : answer.split(","))
            {
                list.add(Integer.parseInt(s));
            }
            return list;
        } catch (NumberFormatException numberFormatException) {
            Log.e(numberFormatException.getMessage());
            Display("Wrong input.");
            return DisplayInts(question);
        }
    }
    public static Integer DisplayInt(String question) {
        Console.Display(question+" (an Integer)");
        try {
            String answer = Read().trim();
            if(answer == null)
                return NOINPUTINT;
            return Integer.parseInt(answer);
        } catch (NumberFormatException numberFormatException) {
            Log.e(numberFormatException.getMessage());
            Display("Wrong input.");
            return DisplayInt(question);
        }
    }
}
