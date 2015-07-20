/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import org.ini4j.Wini;
import java.io.File;

public class Config {
    
    private static String config_distantws = "";
    
    public static void init(){
	try{
	    Wini ini = new Wini(new File("config.ini"));
	    config_distantws = ini.get("config", "distantws");
	    
	}catch(Exception e){
	    new CrashError("Erreur de lecture du fichier de configuration", e, null);
	}
    }
    
    public static String getDistantWs(){
        return config_distantws;
    }
    
}
