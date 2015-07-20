/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class UrlContent {
    public static String getUrlContent(String adresse) {

	URL url;
	String allContent = "";

	try {
	    // get URL content
	    url = new URL(adresse);
	    URLConnection conn = url.openConnection();

	    // open the stream and put it into BufferedReader
	    BufferedReader br = new BufferedReader(
		    new InputStreamReader(conn.getInputStream()));

	    String inputLine;

	    
	    while ((inputLine = br.readLine()) != null) {
		allContent += inputLine;
	    }

	    br.close();


	} catch (MalformedURLException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
	
	return allContent;

    }
}