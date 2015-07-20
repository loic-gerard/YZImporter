/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yzimporter;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author lgerard
 */
public class GraphicMainInterface extends JFrame {

    private JTextArea logs;
    JButton synchroniser;
    JButton synchroniser_part;
    JButton importbillet;

    public GraphicMainInterface() {
        super();

        setSize(1000, 755);
        setTitle(YZImporter.appzName);

        WindowAdapter winCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        addWindowListener(winCloser);

        Container contenu = getContentPane();
        contenu.setLayout(new BorderLayout());

        //Logs d'activité
        logs = new JTextArea();
        logs.setVisible(true);
        logs.setText("");

        JScrollPane ScrollBar = new javax.swing.JScrollPane();
        ScrollBar.setViewportView(logs);
        ScrollBar.setVisible(true);
        ScrollBar.setBounds(20, 90, 500, 200);

        contenu.add(ScrollBar, BorderLayout.CENTER);
        logs.setText("");

        //Boutons
        JPanel zoneButtons = new JPanel();
        zoneButtons.setOpaque(false);

        synchroniser = new JButton(new ImageIcon("images/btn_synchro.png"));
        synchroniser.setSelectedIcon(new ImageIcon("images/btn_synchro.png"));
        synchroniser.setFocusPainted(false);
        synchroniser.setBorderPainted(false);
        synchroniser.setContentAreaFilled(false);
        synchroniser.setOpaque(false);
        ActionListener btn_validerListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                synchro();
            }
        };
        synchroniser.addActionListener(btn_validerListener);
        synchroniser.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zoneButtons.add(synchroniser);

        synchroniser_part = new JButton(new ImageIcon("images/btn_synchro2.png"));
        synchroniser_part.setSelectedIcon(new ImageIcon("images/btn_synchro2.png"));
        synchroniser_part.setFocusPainted(false);
        synchroniser_part.setBorderPainted(false);
        synchroniser_part.setContentAreaFilled(false);
        synchroniser_part.setOpaque(false);
        ActionListener btn_valider_partListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                synchroPart();
            }
        };
        synchroniser_part.addActionListener(btn_valider_partListener);
        synchroniser_part.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zoneButtons.add(synchroniser_part);

        importbillet = new JButton(new ImageIcon("images/btn_importext.png"));
        importbillet.setSelectedIcon(new ImageIcon("images/btn_importext.png"));
        importbillet.setFocusPainted(false);
        importbillet.setBorderPainted(false);
        importbillet.setContentAreaFilled(false);
        importbillet.setOpaque(false);
        ActionListener btn_importbilletListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                importExt();
            }
        };
        importbillet.addActionListener(btn_importbilletListener);
        importbillet.setCursor(new Cursor(Cursor.HAND_CURSOR));
        zoneButtons.add(importbillet);

        contenu.add(zoneButtons, BorderLayout.NORTH);

        setEnabled(true);
        setVisible(true);
        this.toFront();

        this.pack();
        this.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);
    }

    public void writeInLog(String log) {
        logs.setText(log + "\n" + logs.getText());
    }

    private void synchro() {
        new SynchroInterface(this, true);
    }

    private void synchroPart() {
        new SynchroInterface(this, false);
    }

    private void importExt() {
        new ImportExtInterface(this);
    }

}
