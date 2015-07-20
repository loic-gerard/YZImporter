/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author lgerard
 */
public class CsvReader {
    String csvFile;
    ImportExtInterface importInterface;

    public CsvReader(String filename, ImportExtInterface in_importInterface) {
	csvFile = filename;
	importInterface = in_importInterface;
	read();
    }

    private void read() {
	importInterface.completeLog("------------------------------------------");
	importInterface.completeLog("Debut de traitement du fichier : "+csvFile);
	
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = null;

	String billetType = null;
	String codebillet = "";
	String datesvalidite = "";
	
	boolean readed = false;
	
	try {

	    br = new BufferedReader(new FileReader(csvFile));
	    while ((line = br.readLine()) != null) {


		// use comma as separator
		if(cvsSplitBy == null){
		    String[] tp1 = line.split(";");
		    if(tp1.length > 1){
			cvsSplitBy = ";";
		    }
		    String[] tp2 = line.split("\\|");
		    if(tp2.length > 1){
			cvsSplitBy = "\\|";
		    }
		}
		
		String[] linestr = line.split(cvsSplitBy);

		if(billetType == "francebillet" && codebillet != "" && datesvalidite != ""){
		    if(linestr.length > 1){
			new FranceBilletTicketImporter(importInterface, linestr, codebillet, datesvalidite);
		    }
		}
		if(billetType == "ticketnet" && codebillet != "" && datesvalidite != ""){
		    if(linestr.length > 1){
			new TicketNetTicketImporter(importInterface, linestr, codebillet, datesvalidite);
		    }
		}
		

		//Découverte des données (TICKET NET)
		if(linestr[0].equals("341063") || linestr[0].equals("341070")){
		    billetType = "ticketnet";
		    
		    if(linestr[1].equals("2061197")){
			codebillet = "weekend";
			datesvalidite = "31/07/2015,01/08/2015";
			readed = true;
			importInterface.completeLog("Résultat de la détection | type:TICKETNET code:"+codebillet+" datesvalidite:"+datesvalidite);
		    }
		    if(linestr[1].equals("2061184")){
			 codebillet = "samedi";
			 datesvalidite = "01/08/2015";
			 readed = true;
			    
			 importInterface.completeLog("Résultat de la détection | type:TICKETNET code:"+codebillet+" datesvalidite:"+datesvalidite);
		    }
		    if(linestr[1].equals("2061182")){
			codebillet = "vendredi";
			datesvalidite = "31/07/2015";
			readed = true;
			    
			importInterface.completeLog("Résultat de la détection | type:TICKETNET code:"+codebillet+" datesvalidite:"+datesvalidite);
		    }
		}
		
		//Découverte des données (FRANCE BILLET)
		if(linestr[0].equals("Tri")){
		    billetType = "francebillet";
		}
		if(linestr[0].equals("Type de tarif")){
		    if(linestr[1].equals("Forfait 1 jour")){
			codebillet = "1J";
		    }
		    if(linestr[1].equals("Forfait 2 jours")){
			codebillet = "2J";
		    }
		}
		if(linestr[0].equals("Seance")){
		    if(codebillet.equals("1J")){
			if(linestr[1].equals("2015-07-31 18:00:00")){
			    codebillet = "vendredi";
			    datesvalidite = "31/07/2015";
			    readed = true;
			    
			    importInterface.completeLog("Résultat de la détection | type:FRANCE BILLET code:"+codebillet+" datesvalidite:"+datesvalidite);
			}
			if(linestr[1].equals("2015-08-01 18:00:00")){
			    codebillet = "samedi";
			    datesvalidite = "01/08/2015";
			    readed = true;
			    
			    importInterface.completeLog("Résultat de la détection | type:FRANCE BILLET code:"+codebillet+" datesvalidite:"+datesvalidite);
			}
		    }
		    if(codebillet.equals("2J")){
			if(linestr.length == 1){
			    codebillet = "weekend";
			    datesvalidite = "31/07/2015,01/08/2015";
			    readed = true;
			    
			    importInterface.completeLog("Résultat de la détection | type:FRANCE BILLET code:"+codebillet+" datesvalidite:"+datesvalidite);
			}
		    }
		}
		

	    }

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	} finally {
	    if (br != null) {
		try {
		    br.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	    }
	}
	
	if(!readed){
	    importInterface.completeLog("Erreur : fichier non reconnu");
	    importInterface.showError("Erreur fichier non reconnu : "+csvFile,"Erreur de traitement",false);
	    importInterface.fileserror++;
	}else{
	    importInterface.filestreated++;
	}
	
	

	importInterface.completeLog("Fin de traitement du fichier");
    }
}
