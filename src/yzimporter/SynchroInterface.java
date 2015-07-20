/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.*;



public class SynchroInterface extends JFrame implements Runnable{
    private JProgressBar progressglobal;
    private JLabel operation;
    private JTextArea details;
    private Synchro synchro;
    private JScrollPane scrollPane;
    private String log = "";
    private boolean full;
    
    public SynchroInterface(GraphicMainInterface parent, boolean in_full) {
	full = in_full;
	//super(parent, "Synchronisation en cours...", true);
	buildWindow();

    }
    
    public void run(){
    }

    private void buildWindow() {
	setSize(552,350);
	setTitle("Synchronisation en cours...");

	WindowAdapter winCloser = new WindowAdapter() {
	    public void windowClosing(WindowEvent e) {
	    }
	};
	addWindowListener(winCloser);

	//Construction du contenu
	Container contenu = getContentPane();
	Color bgcolor = new Color(66, 66, 66);
	contenu.setBackground(null);
	contenu.setLayout(null);

	//Progression globale
	progressglobal = new JProgressBar(0, 100);
	progressglobal.setBackground(new Color(155, 155, 155));
	progressglobal.setBorderPainted(false);
	progressglobal.setBorder(null);
	progressglobal.setForeground(new Color(255, 174, 0));
	progressglobal.setValue(0);
	contenu.add(progressglobal);
	progressglobal.setBounds(20, 58, 500, 11);
	

	//Opération en cours
	operation = new JLabel("En attente...");
	operation.setBounds(20, 20, 500, 18);
	operation.setForeground(new Color(0, 0, 0));
	operation.setFont(new Font("Arial", Font.PLAIN, 12));
	contenu.add(operation);
	
	details = new JTextArea(5, 20);
	scrollPane = new JScrollPane(details, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	details.setEditable(false);
	
	//contenu.add(scrollPane);
	
	
	details = new JTextArea();
        //details.setBounds(5, 65, 330, 235);
        details.setVisible(true);
        details.setText("");
        
        
        JScrollPane ScrollBar = new javax.swing.JScrollPane();
        ScrollBar.setViewportView(details);
        ScrollBar.setVisible(true);

	//pack();
	
	ScrollBar.setBounds(20, 90, 500, 200);
	
	this.add(ScrollBar);
	details.setText("");

	//Termine la création
	setResizable(false);
	center();

	//launch();

	setVisible(true);
	this.toFront();
	
	startSynch();
    }
    
    private void startSynch(){
	synchro = new Synchro(this, full);
	
	
    }

    /**
     * Positionner la fenêtre au centre
     */
    private void center() {
	//positionnement au milieu de l'ecran
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	Dimension frameSize = getSize();
	if (frameSize.height > screenSize.height) {
	    frameSize.height = screenSize.height;
	}
	if (frameSize.width > screenSize.width) {
	    frameSize.width = screenSize.width;
	}
	setLocation((screenSize.width / 2) - (frameSize.width / 2), (screenSize.height / 2) - (frameSize.height / 2));
    }
    
    public void setTache(String details){
	operation.setText(details);
    }
    
    public void addLog(String info){
	log += info;

    }
    
    public void completeLog(String info){
	log += info+"\n";
	details.setText(log+details.getText());
	log = "";

    }
    
    public void setProgress(int p){
	progressglobal.setValue(p);
    }
    
    public void showError(String erreur, String title, boolean exit){
	JOptionPane.showMessageDialog(this,
	erreur,
	title,
	JOptionPane.ERROR_MESSAGE);
	if(exit){
	    System.exit(0);
	}
    }
    
    public void showEndMessage(int add, int update, int error){
	JOptionPane.showMessageDialog(this,
	"La base de données locale a été synchronisée avec succès avec les serveurs Ikodo. \n \nBillets ajoutés : "+add+"\nBillets mise à jour : "+update+"\nErreurs de traitement : "+error,
	"Fin de la synchronisation",
	JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
}
