package it.unibo.grubclash.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import it.unibo.grubclash.model.GrubPanel;

import java.io.File;
import java.io.IOException;

/*
    Metti maschera, dentro mapinitialization il nero diventa terreno e il bianco diventa cielo
*/

public class MapBuilder extends Canvas {

    static GrubPanel grubPanel = new GrubPanel();

    final static int ROWS = 20;
    final static int COLS = 20;
    
    public static Color switchBackground(JPanel[][] mapBase, int i, int j, Color color) {
        Color btnColor = (color == Color.WHITE ? Color.BLACK : Color.WHITE);
        mapBase[i][j].setBackground(btnColor);
        return btnColor;
    }

    public static JButton createButton(int i, int j) {
        final int BUTTON_WIDTH = 80;
        final int BUTTON_HEIGHT = 40;

        JButton invisibleBtn = new JButton();
        if (i == 0 && j == COLS - 1) {
            invisibleBtn.setText("Finish");
            invisibleBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        } else { // Gestisce tutti i bottoni che non sono quello di chiusura dell'editing della mappa
            //invisibleBtn.setText(String.valueOf(i) + "-" + String.valueOf(j));
            invisibleBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            invisibleBtn.setBorder(null);
            invisibleBtn.setBorderPainted(false);
            invisibleBtn.setContentAreaFilled(false);
            invisibleBtn.setOpaque(false);
        }
        return invisibleBtn; // Fa strano chiamare "invisibleBtn" l'unico bottone che fa da finish, ma non ho idee migliori
    }

    public static void mapInitialization(JButton[][] btnMatrix, JFrame map, JPanel[][] mapBase) throws IOException {
        map.setResizable(false);
        // Devo far in modo di cambiare il nero con piattaforma e bianco con cielo
        // BufferedImage piattaforma = ImageIO.read(new File("src\\main\\resources\\gameplay\\patform.png"));

        for(int i = 0; i < ROWS; i++ ) {
            for(int j = 0; j < COLS; j++) {
                btnMatrix[i][j].setVisible(false);
                if(mapBase[i][j].getBackground() == Color.BLACK) {
                    panelBackground(mapBase, i, j);
                } else {
                    mapBase[i][j].setBackground(Color.CYAN);
                }
                
                mapBase[i][j].repaint();
            }
        }
        grubPanel.startGameThread();
    }


    public static void panelBackground(JPanel[][] mapBase, int i, int j) throws IOException {
        BufferedImage platformImg = ImageIO.read(new File("src\\main\\resources\\gameplay\\platform.png"));
        JLabel picLabel = new JLabel(new ImageIcon(platformImg));
        mapBase[i][j].add(picLabel);
    }

    public static void p1Map() {
        JFrame map = new JFrame();
        FrameManager.setTitle(map);
        FrameManager.setIcon(map);
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.setSize(900, 900);
        map.setResizable(true); // Nella fase di setup è resizable
        // Imposto il layout
        map.setLayout(new GridLayout(ROWS, COLS));
        // Creo una griglia di panels
        JPanel[][] mapBase = new JPanel[ROWS][COLS];

        // I bottoni servono per cambiare colore
        JButton[][] btnMatrix = new JButton[ROWS][COLS];

        // Mi concentro a creare la griglia di gioco e i bottoni della griglia
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                mapBase[i][j] = new JPanel();
                mapBase[i][j].setBackground(Color.WHITE);
                mapBase[i][j].setLayout(new FlowLayout());

                // Aggiungo gli action listener al click
                btnMatrix[i][j] = createButton(i, j);

                if(i == 0 && j == COLS - 1) {
                    JButton btnFinish = btnMatrix[i][j];
                    btnFinish.addActionListener(f -> { // Chiama il metodo per la rimozione dei bottoni
                        try {
                            mapInitialization(btnMatrix, map, mapBase);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } // Così la map è pronta, la svuota dai bottoni durante la fase della creazione
                    });
                } else {
                    final int finalI = i;
                    final int finalJ = j;

                    // 'previousColorState' deve essere final e non puo' essere modificato dentro i metodi richiamati agli eventi
                    // Soluzione proposta dall'IDE
                    // Oggetto utilizzato per salvare lo stato del colore precedente al ricoloramento in grigio
                    final Color[] previousColorState = { btnMatrix[finalI][finalJ].getBackground() };

                    btnMatrix[i][j].addActionListener(e -> previousColorState[0] = switchBackground(mapBase, finalI, finalJ, previousColorState[0]));

                    btnMatrix[i][j].addMouseListener(new MouseAdapter() {
                        // Quando l'utente passa sopra una cella cambia colore e diventa grigio
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            previousColorState[0] = mapBase[finalI][finalJ].getBackground();
                            Color menuColor = Color.decode("#EF7B10");
                            mapBase[finalI][finalJ].setBackground(menuColor);
                        }

                        // Reimposta il colore della cella
                        @Override
                        public void mouseExited(MouseEvent e) {
                            mapBase[finalI][finalJ].setBackground(previousColorState[0]);
                        }
                    });
                }
                
                mapBase[i][j].add(btnMatrix[i][j], BorderLayout.CENTER);
                if(i == 19) {
                    mapBase[i][j].setBackground(Color.BLACK);
                }
                mapBase[i][j].setVisible(true);
                
                map.add(mapBase[i][j]);
            }
        }

        // Questi metodi qua sotto servono per centrare il frame in mezzo allo schermo
        map.pack();
        map.setLocationRelativeTo(null);

        map.setVisible(true);

        // Messaggio di aiuto a schermo
        FrameManager.showMessageBox("Messaggio", "Crea o rimuovi spazi con collisione interagendo con i blocchi per creare la tua mappa!", JOptionPane.INFORMATION_MESSAGE);
    }
}