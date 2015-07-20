/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.util.ArrayList;

public class Synchro extends Thread {
    private SynchroInterface interf;
    private boolean full;
    
    public Synchro(SynchroInterface i, boolean in_full){
	interf = i;
	full = in_full;
	start();
    }
    
    public void run(){
	interf.setTache("Connexion en cours...");
	interf.addLog("Connexion en cours...");
	
	String index = "-1";
	if(!full){
	    index = YZImporter.cnx.getMaxBilletNumero();
	}
	String data = UrlContent.getUrlContent(Config.getDistantWs()+"?uid"+Math.round(Math.random()*1000000)+"&index="+index+"&key=AB723876EABC7547");
	
	
	if(data.length() < 20){
	    interf.completeLog("Impossible d'accéder à l'Url : "+Config.getDistantWs());
	    interf.showError("Impossible d'accéder à l'Url : "+Config.getDistantWs(), "Erreur de connexion", true);
	}
	interf.setProgress(20);
	interf.completeLog("OK");
	
	
	if(full){
	    interf.addLog("Mise à plat de la base de données...");
	    YZImporter.cnx.setAllInactive("YZ");
	    interf.setProgress(30);
	    interf.completeLog("OK");
	}
	
	
	interf.setTache("Traitement des données...");
	interf.completeLog("--------------------------------------");
	interf.completeLog("Début du traitement des données");
	
	XmlReader xml = new XmlReader(data);
	if(xml.isError()){
	    interf.completeLog("Erreur de traitement : "+xml.getErrorMessage());
	    interf.showError("Erreur de traitement : "+xml.getErrorMessage(), "Erreur de traitement", true);
	}
	
	
	ArrayList al = xml.getNodes("billet");
	int s = al.size();
	
	int add = 0;
	int update = 0;
	int error = 0;
	
	
	for(int i = 0; i < s; i++){
	    XmlReader x = (XmlReader)al.get(i);
	    String pk = x.readSingleNode("pk");
	    String type = x.readSingleNode("typebillet");
	    String designation = x.readSingleNode("designation");
	    String dt_commande = x.readSingleNode("dt_commande");
	    String etat_commande = x.readSingleNode("etat_commande");
	    String client = x.readSingleNode("client");
	    if(client.equals("NULL")){ client = ""; }
	    String keycode = x.readSingleNode("keycode");
	    String ikeycode = x.readSingleNode("ikeycode");
	    String num_commande = x.readSingleNode("num_commande");
	    int valide = Integer.parseInt(x.readSingleNode("valide"));
	    String dates_validite = x.readSingleNode("dates_validite");
	    if(dates_validite.equals("NULL")){ dates_validite = ""; }
	    String commentaires = x.readSingleNode("commentaires");
	    if(commentaires.equals("NULL")){ commentaires = ""; }
	    interf.addLog("Lecture du billet "+pk+"...");
	    
	    int res = YZImporter.cnx.synchBillet(pk, type, designation, dt_commande, etat_commande, client, keycode, ikeycode, num_commande, valide, dates_validite, commentaires, "YZ");
	    if(res==1){
		interf.completeLog("AJOUTE");
		add++;
	    }else if(res==2){
		interf.completeLog("MIS A JOUR");
		update++;
	    }else if(res==3){
		interf.completeLog("ERREUR");
		error++;
	    }
	    
	    interf.setProgress(30+(70*i/s));
	    
	    
	}
	
	interf.completeLog("--------------------------------------");
	interf.completeLog("Fin du traitement des données");
	interf.completeLog("Ajoutés : "+add+" | Modifiés : "+update+" | Erreurs : "+error);
	
	interf.showEndMessage(add, update, error);
    }
}
