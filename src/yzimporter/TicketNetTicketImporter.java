/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

/**
 *
 * @author lgerard
 */
public class TicketNetTicketImporter {

    public TicketNetTicketImporter(ImportExtInterface importInterface, String[] linestr, String type, String dates_validite){

	importInterface.addLog("Billet numéro "+linestr[1]+" : ");
	
	int res = 0;
	if(linestr.length > 15){
	    res = YZImporter.cnx.synchBillet(linestr[1], type, "ticketNet ("+type+")", "", "expediee", linestr[16]+" "+linestr[15], linestr[1], linestr[1], linestr[0], 1, dates_validite, "", "TICKETNET");
	}else{
	    res = YZImporter.cnx.synchBillet(linestr[1], type, "ticketNet ("+type+")", "", "expediee", "NC", linestr[1], linestr[1], linestr[0], 1, dates_validite, "", "TICKETNET");
	}
	if(res==1){
	    importInterface.completeLog("AJOUT");
	    importInterface.added++;
	}else if(res==2){
	    importInterface.completeLog("MIS A JOUR");
	    importInterface.updated++;
	}else if(res==3){
	    importInterface.error++;
	    importInterface.completeLog("ERREUR !");
	    importInterface.showError("Erreur d'import d'un billet", "Erreur d'import", false);
	}
    }
}
