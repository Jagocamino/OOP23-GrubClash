package it.unibo.grubclash.view;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import it.unibo.grubclash.model.GrubPanel;

import java.io.File;
import java.io.IOException;

/*
    metti maschera, dentro mapinitialization il nero diventa terreno e il bianco diventa cielo
*/

public class TestMappa extends Canvas {

    static GrubPanel grubPanel = new GrubPanel();

    final static int ROWS = 19;
    final static int COLS = 19;
    
    public static void switchbackground(JPanel[][] basemappa, int i, int j) {
        if (basemappa[i][j].getBackground() == Color.WHITE ) {
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

    public static void mapinitialization(JButton[][] tantibottoni, JFrame mappa, JPanel[][] basemappa) throws IOException {
        mappa.setResizable(false);
        //devo far in modo di cambiare il nero con piattaforma e bianco con cielo
        //BufferedImage piattaforma = ImageIO.read(new File("src\\main\\resources\\gameplay\\patform.png"));

        for(int i=0; i < ROWS+1; i++ ) {
            for(int j=0; j < COLS+1; j++) {
                tantibottoni[i][j].setVisible(false);
                if (basemappa[i][j].getBackground()==Color.BLACK) {
                    panelbackground(basemappa, i, j);
                }else{
                    basemappa[i][j].setBackground(Color.CYAN);
                }
                
                basemappa[i][j].repaint();
            }
        }
        grubPanel.startGameThread();
    }


    public static void panelbackground(JPanel[][] basemappa, int i, int j) throws IOException {
        BufferedImage myPicture = ImageIO.read(new File("src\\main\\resources\\gameplay\\platform.png"));
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        basemappa[i][j].add(picLabel);
    }


    public static void p1mappa(JFrame frameprima) {

        JFrame mappa = new JFrame();
        mappa.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mappa.setSize(900,900);
        mappa.setResizable(true); //nella fase di setup è resizable
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
                            try {
                                mapinitialization(tantibottoni,mappa,basemappa);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } //così la mappa è pronta, la svuota dai bottoni durante la fase della creazione
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
        //questi metodi qua sotto servono per centrare il frame in mezzo allo schermo
        mappa.pack();
        mappa.setLocationRelativeTo(null);

        mappa.setVisible(true);
        frameprima.dispose();
    }
}