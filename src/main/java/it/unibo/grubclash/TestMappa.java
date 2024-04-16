package it.unibo.grubclash;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
    -gestire rescale (i bottoni invisibili devono cambiare insieme alla visuale)
 */

public class TestMappa extends Canvas{

    final static int ROWS = 19;
    final static int COLS = 19;
    
    public static void switchbackground(JPanel[][] basemappa, int i, int j) {
        if (basemappa[i][j].getBackground()== Color.WHITE ) {
            basemappa[i][j].setBackground(Color.BLACK);
        } else {
            basemappa[i][j].setBackground(Color.WHITE);
        }
    }

    public static JButton creabottone(int i, int j) {
        if (i==0 && j==COLS) {
            JButton bottoneinvisibile = new JButton("Finish");
            bottoneinvisibile.setBounds(25,25,25,25);
            return bottoneinvisibile; //fa strano chiamare "bottoneinvisibile" l'unico bottone che fa da finish, ma non ho idee migliori
        } else { //gestisce tutti i bottoni che non sono quello di chiusura dell'editing della mappa
            JButton bottoneinvisibile = new JButton(String.valueOf(i) + "-" + String.valueOf(j));
            bottoneinvisibile.setBounds(20,20,20,20);
            bottoneinvisibile.setBorder(null);
            bottoneinvisibile.setBorderPainted(false);
            bottoneinvisibile.setContentAreaFilled(false);
            bottoneinvisibile.setOpaque(false);
            return bottoneinvisibile;
        }        
    }

    public static void mapinitialization(JButton[][] tantibottoni) {
        for(int i=0; i < ROWS+1; i++ ) {
            for(int j=0; j < COLS+1; j++) {
                tantibottoni[i][j].setVisible(false);
            }

        }
    }


    public static void p1mappa(JFrame frameprima) {

        JFrame mappa = new JFrame();
        mappa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mappa.setSize(900,900);
        //imposto il layout
        mappa.setLayout(new GridLayout(20,20));
        //creo una griglia di panels
        JPanel[][] basemappa = new JPanel[20][20];

        //i bottoni servono per cambiare colore
        JButton[][] tantibottoni = new JButton[20][20];

        //mi concentro a creare la griglia di gioco e i bottoni della griglia
        for(int i=0; i < ROWS+1; i++){
            for(int j=0; j < COLS+1; j++){

                
                basemappa[i][j] = new JPanel();
                basemappa[i][j].setBackground(Color.WHITE);
                basemappa[i][j].setLayout(new FlowLayout());


                //aggiungo gli action listener al click
                tantibottoni[i][j] = creabottone(i, j);

                final int iCopy = i;//altrimenti si lamenta che Local variable i defined in an enclosing scope must be final or effectively final
                final int jCopy = j;
                if(i==0 && j==COLS) {
                    JButton bottonefinish = tantibottoni[i][j];
                    bottonefinish.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent f) { //chiama il metodo per la rimozione dei bottoni
                            mapinitialization(tantibottoni); //così la mappa è pronta
                        }
                    });
                } else {
                    tantibottoni[i][j].addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                            switchbackground(basemappa, iCopy, jCopy);
                        }
                    });
                }
                
                basemappa[i][j].add(tantibottoni[i][j], BorderLayout.CENTER);
                if(i==19){
                    basemappa[i][j].setBackground(Color.BLACK);
                }
                basemappa[i][j].setVisible(true);
                
                mappa.add(basemappa[i][j]);
            }
        }
        mappa.setVisible(true);
        frameprima.dispose();



    }
}