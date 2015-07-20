/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.util.Date;

public class Log {
    public static void log(String message){
	Date date = new Date();
	message = date.toString()+" : "+message;
	
	System.out.println(message);
	
	if(YZImporter.interf != null){
	    YZImporter.interf.writeInLog(message);
	}
    }
}
