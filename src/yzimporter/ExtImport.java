/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.io.File;

/**
 *
 * @author lgerard
 */
public class ExtImport extends Thread {
    private ImportExtInterface interf;
    
    public ExtImport(ImportExtInterface i){
	interf = i;
	start();
    }
    
    public void run(){
        interf.addLog("Mise à plat de la base de données...");
	YZImporter.cnx.setAllInactive("FRANCEBILLET");
        YZImporter.cnx.setAllInactive("TICKETNET");
        
	File dir = new File("import/");
	String [] listefichiers = dir.list();
	for(int i=0;i<listefichiers.length;i++){ 
	    String[] dec = listefichiers[i].split("[.]");
	    System.out.println(dec[dec.length-1]);
	    if(dec[dec.length-1].equals("txt") || dec[dec.length-1].equals("csv")){
		interf.setTache("import de : "+listefichiers[i]);
		new CsvReader("import/"+listefichiers[i], interf);
		float d = (float) (i+1)/listefichiers.length;
		int p = Math.round(d*100);
		interf.setProgress(p);
	    }
	}
	interf.showEndMessage();
    }
}
