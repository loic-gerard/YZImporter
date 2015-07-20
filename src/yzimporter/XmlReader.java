/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.io.*;
import org.w3c.dom.Document;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 
import java.util.ArrayList;
import org.xml.sax.*;

public class XmlReader {
    Document doc;
    Element element;
    boolean error = false;
    String error_details = "";
    
    public XmlReader(File file){
	
	try {
	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    doc = docBuilder.parse (file);
	    
	    // normalize text representation
	    doc.getDocumentElement ().normalize ();
	    
	}catch(Exception e){
	    error = true;
	    error_details = e.getMessage();
	    e.printStackTrace();
	}
    }
    
    public XmlReader(String xmlContent){
	
	try {
	    DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	    doc = docBuilder.parse (new InputSource(new StringReader(xmlContent)));
	    
	    // normalize text representation
	    doc.getDocumentElement ().normalize ();
	    
	}catch(Exception e){
	    error = true;
	    error_details = e.getMessage();
	    e.printStackTrace();
	}
    }
    
    public boolean isError(){
	return error;
    }
    
    public String getErrorMessage(){
	if(!error){
	    return null;
	}else{
	    return error_details;
	}
    }
    
    public XmlReader(Element e){
	element = e;
    }
    
    
    public String readSingleNode(String nodeName){
	 NodeList listOfNodes = getNodeList(nodeName);
	 Node node = listOfNodes.item(0);
	 if(node.getNodeType() == Node.ELEMENT_NODE){
	    NodeList textL = node.getChildNodes();
	    String v = ((Node)textL.item(0)).getNodeValue().trim();
	    return v;
	 }
	 return null;
    }
    
    private NodeList getNodeList(String nodeName){
	NodeList listOfNodes;
	if(element == null){
	    listOfNodes = doc.getElementsByTagName(nodeName);
	}else{
	    listOfNodes = element.getElementsByTagName(nodeName);
	}
	
	return listOfNodes;
    }
    
    public ArrayList getNodes(String nodeName){
	NodeList listOfNodes = getNodeList(nodeName);
	ArrayList a = new ArrayList();

	
	for(int s=0; s<listOfNodes.getLength() ; s++){
	    Node firstNode = listOfNodes.item(s);
	    if(firstNode.getNodeType() == Node.ELEMENT_NODE){
		Element firstElement = (Element)firstNode;
		a.add(new XmlReader(firstElement));
	    }
	}
	
	return a;
    }
}
