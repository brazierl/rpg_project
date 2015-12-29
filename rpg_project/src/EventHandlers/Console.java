/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EventHandlers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author Louis
 */
public class Console {
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
            e.printStackTrace();
            return "error";
        }
    } 
}
