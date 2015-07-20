/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class DbConnexion {

    private String DBPath = "";
    private Connection connection = null;

    public DbConnexion(String dBPath) {
        DBPath = dBPath;
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + DBPath);
            Log.log("OK >> Connexion a " + DBPath + " avec succès");
        } catch (ClassNotFoundException notFoundException) {
            notFoundException.printStackTrace();
            Log.log("Erreur de connexion à " + DBPath + "(ClassNotFoundException)");
            new CrashError("Impossible de se connecter à la base de données", notFoundException, null);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            Log.log("Erreur de connecxion à " + DBPath + "SQLException");
            new CrashError("Impossible de se connecter à la base de données", sqlException, null);
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String getMaxBilletNumero(){
	ResultSet rs = query(""
	+ "SELECT MAX(CAST(in_numero AS INT)) AS numero FROM tb_billet WHERE tt_type != 'FRANCE BILLET' AND tt_type != 'TICKETNET'");
	
	try{
	    return rs.getString("numero");
	}catch(Exception e){
	    return "-1";
	}
    }
    
    public ResultSet query(String requet) {
	
       ResultSet resultat = null;
       try {
	   Statement statement = connection.createStatement();
           resultat = statement.executeQuery(requet);
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Erreur dans la requete : " + requet);
       }
       return resultat;
 
   }
    
   public void setAllInactive(String source){
       String query = "UPDATE tb_billet SET in_valide=0 WHERE tt_source='"+this.protect(source)+"'";
       queryWithoutResult(query);
   }
   
   public boolean queryWithoutResult(String requet) {
	
       try {
	   Statement statement = connection.createStatement();
           statement.executeUpdate(requet);
	   
	   return true;
       } catch (SQLException e) {
           e.printStackTrace();
           System.out.println("Erreur dans la requete : " + requet);
	   
	   return false;
       }

 
   }
   
   /*
     * Retourne :
     * 1 : billet ajouté
     * 2 : billet mis à jour
     * 3 : erreur de traitement
     */
    public int synchBillet(String pk, String type, String designation, String dt_commande, String etat_commande, String client, String keycode, String ikeycode, String num_commande, int valide, String dates_validite, String commentaires, String source){
	
	ResultSet existant = getBilletByNumero(pk);
	String pk_billet = "";
	try{
	     pk_billet = existant.getString("pk_billet");
	}catch(Exception e){
	}
	
	if(DbConnexion.getCount(existant)==1){
	    
	    
	    //Billet existant : à mettre à jour
	    try{
		
		String query = "UPDATE tb_billet "+
			"SET in_numero='"+pk+"',"+
			"tt_type='"+type+"',"+
			"tt_designation='"+this.protect(designation)+"',"+
			"dt_commande='"+dt_commande+"',"+
			"tt_codebarre='"+keycode+"',"+
			"in_numero_commande='"+num_commande+"',"+
			"tt_client='"+this.protect(client)+"',"+
			"in_valide="+valide+","+
			"tt_dates_validite='"+dates_validite+"',"+
			"tt_commentaires='"+this.protect(commentaires)+"', "+
                        "tt_source='"+this.protect(source)+"' "+
			"WHERE pk_billet="+pk_billet;
		boolean r = queryWithoutResult(query);
		
		
		if(r){
		    return 2;
		}else{
		    return 3;
		}
	    }catch(Exception e){
		e.printStackTrace();
		return 3;
	    }
	}else{
	    //Billet à créer
	    
	    String query = "INSERT INTO tb_billet "+
		    "( "+
		    "in_numero,"+
		    "tt_type,"+
		    "tt_designation,"+
		    "dt_commande,"+
		    "tt_codebarre,"+
		    "in_numero_commande,"+
		    "tt_client,"+
		    "in_valide,"+
		    "tt_dates_validite,"+
		    "tt_commentaires,"+
                    "tt_source"+
		    ") "+
		    "VALUES "+
		    "( "+
		    "'"+pk+"',"+
		    "'"+type+"',"+
		    "'"+this.protect(designation)+"',"+
		    "'"+dt_commande+"',"+
		    "'"+keycode+"',"+
		    "'"+num_commande+"',"+
		    "'"+this.protect(client)+"',"+
		    valide+","+
		    "'"+dates_validite+"',"+
		    "'"+this.protect(commentaires)+"',"+
                    "'"+this.protect(source)+"'"+
		    ")";
	    boolean r = queryWithoutResult(query);
	    
	    if(r){
		return 1;
	    }else{
		return 3;
	    }
	}

    }
    
    public ResultSet getBilletByNumero(String numero){
	ResultSet rs = query(""
	+ "SELECT * FROM tb_billet "
	+ "WHERE in_numero='"+numero+"'");

	return rs;
    }
    
    private static String protect(String string){
	return string.replaceAll("'", " ");
    }
    
    public static int getCount(ResultSet rs){
	int rowNum = 0;
	try{
	    while (rs.next()) {
		rowNum++;
	    }
	}catch(Exception ex){}
	return rowNum;
    }


}
