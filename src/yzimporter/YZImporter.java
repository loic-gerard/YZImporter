/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

/**
 *
 * @author lgerard
 */
public class YZImporter {
    public static GraphicMainInterface interf;
    public static DbConnexion cnx;
    public static String appzName = "Yzeure And Rock - Importer";
    public static String version = "Version 0.0.1";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new YZImporter();
    }
    
    public YZImporter(){
        Log.log(appzName);
	Log.log(version);
	Log.log("----------------------------------------");
	
	//Création de l'interface
	interf = new GraphicMainInterface();
        
        //Chargement du fichier de configuration (config.ini)
	Config.init();
        
        //Connexion à la DBB
        cnx = new DbConnexion("../YZDatabase/database.s3db");
        cnx.connect();
        
        //Log ready
	Log.log("Configuration et interface prêtes à l'utilisation");
    }
    
}
