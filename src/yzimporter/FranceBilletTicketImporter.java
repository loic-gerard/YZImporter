/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

/**
 *
 * @author lgerard
 */
public class FranceBilletTicketImporter {
    
    public FranceBilletTicketImporter(ImportExtInterface importInterface, String[] linestr, String type, String dates_validite){
	importInterface.addLog("Billet numéro "+linestr[15]+" : ");
	
	int res = YZImporter.cnx.synchBillet(linestr[15], type, "France Billet ("+type+")", linestr[2], "expediee", linestr[11]+" "+linestr[10], linestr[15], linestr[15], linestr[15], 1, dates_validite, "", "FRANCEBILLET");
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
