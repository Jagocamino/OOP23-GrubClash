package it.unibo.grubclash.view;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import it.unibo.grubclash.model.GrubPanel;

import java.io.File;
import java.io.IOException;

/*
    devo trovare il modo di cambiare il comportamento del bottone in finish (rimuovere actionlistener senza passare parametri), 
    devo mettere messaggio a schermo ogni volta che avviene un finish nella fase di placement, 
    ogni volta che lo premo il playercount aumenta, cambia colore (colore specifico = player specifico) e implemento stesso funzionamento dei bottoni terra (toggle)
*/

public class MapBuilder extends Canvas {

    static GrubPanel grubPanel = new GrubPanel();

    protected static int currentPlayer;
    protected static int numPlayers;
    protected static Color playerColor[];

    private static boolean characterPlacementPhase; //diventa true quando viene toccato il primo "finish"
    private static boolean colorSpawnpoint; //bool che serve per tenere traccia se lo spawnpoint è stato messo o no, si usa nel metodo switchBackground

    final static int ROWS = 20;
    final static int COLS = 20;


    //questi tre metodi servono al programma a capire se siamo nella fase del piazzamento dei personaggi, che si trova dopo la fase della creazione dei blocchi di terra della mappa
    public static void initCharacterPlacementPhase () {
        characterPlacementPhase = false;
    }
    public static void updateCharacterPlacementPhase () {
        characterPlacementPhase = true;
    } 
    public static boolean getCharacterPlacementPhase () {
        return characterPlacementPhase;
    }

    public static void initColorSpawnpoint () {
        colorSpawnpoint = false;
    }

    public static boolean getColorSpawnpoint () {
        return colorSpawnpoint;
    }

    public static void switchColorSpawnpoint () {
        if (colorSpawnpoint == false) {
            colorSpawnpoint = true;
        }else{
            colorSpawnpoint = false;
        }
    }
    
    public static Color switchBackground(JPanel[][] mapBase, int i, int j, Color color, JButton[][] btnMatrix, JFrame map) {
        if (getCharacterPlacementPhase() == false) { //per capire se siamo nella fase dei personaggi
            Color btnColor = (color == Color.WHITE ? Color.BLACK : Color.WHITE);
            mapBase[i][j].setBackground(btnColor);
            return btnColor;
        }else{ //cioè se siamo nella fase di placement dei personaggi
            Color btnColor;
             //permetto il cambio del colore solo se o lo sfondo è bianco, o è dello stesso colore del palyer che voglio cambiare
            if (mapBase[i][j].getBackground() == Color.WHITE && getColorSpawnpoint() == false) {
                btnColor = getPlayerColor(getCurrentPlayer());
                switchColorSpawnpoint();
                mapBase[i][j].setBackground(btnColor);
                return btnColor;
            }else{
                if (mapBase[i][j].getBackground() == getPlayerColor(getCurrentPlayer()) && getColorSpawnpoint() == true) {
                    btnColor = Color.WHITE;
                    switchColorSpawnpoint();
                    mapBase[i][j].setBackground(btnColor);
                    return btnColor;
                }
            }
            return mapBase[i][j].getBackground();
        }
    }

    public static void setNumPlayers ( int numPlayers ) {
        MapBuilder.numPlayers = numPlayers;
        initPlayerColor();
    }

    public static int getNumPlayers () {
        return numPlayers;
    }

    public static int getCurrentPlayer () {
        return MapBuilder.currentPlayer;
    }

    public static void updateCurrentPlayer () {
        if ( getCurrentPlayer() < getNumPlayers() ) {
            MapBuilder.currentPlayer++;
        }else{
            initializeCurrentPlayer();
        }
    }

    public static void initializeCurrentPlayer () {
        MapBuilder.currentPlayer = 0;
    }

    public static void initPlayerColor () {
        Color[] playerColor = new Color[getNumPlayers()]; //colori che identificano i giocatori
        for (int f = 0; f < getNumPlayers(); f++) {
            switch (f) {
                case 0:
                    playerColor[f] = Color.PINK;
                    break;
                case 1:
                    playerColor[f] = Color.YELLOW;
                    break;
                case 2:
                    playerColor[f] = Color.LIGHT_GRAY;
                    break;
                case 3:
                    playerColor[f] = Color.RED;
                    break;
                case 4:
                    playerColor[f] = Color.ORANGE;
                    break;
                case 5:
                    playerColor[f] = Color.BLUE;
                    break;
            }
        }
        MapBuilder.playerColor = playerColor;
    }

    public static Color getPlayerColor (int currentPlayer) { //verrà passato tramite getCurrentPlayer();
        return playerColor[currentPlayer];
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

    public static void mapInitialization(JButton[][] btnMatrix, JFrame map, JPanel[][] mapBase) throws IOException {//TODO mapinitializazion deve mettere il modello in base al player
        
        map.setResizable(false); //blocco il resize, aiuta le hitbox
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

    public static void p2Map(JFrame map, JPanel[][] mapBase, JButton[][] btnMatrix, int btnFinishI, int btnFinishJ) {
        JButton btnFinish = btnMatrix[btnFinishI][btnFinishJ];
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                if(btnMatrix[i][j] == btnFinish) { //if(è nel bottone finish, ovvero quello dello switch della fase)
                    btnFinish.addActionListener(o -> {
                        if (getCurrentPlayer() == getNumPlayers() - 1) {
                            try {
                                mapInitialization(btnMatrix, map, mapBase);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            initColorSpawnpoint();
                            updateCurrentPlayer();
                            System.out.println("siamo al giocatore numero" + getCurrentPlayer());
                            System.out.println(getNumPlayers());
                        }
                    });
                }
            }
        }

        SwingUtilities.updateComponentTreeUI(map);

    }

    public static void p1Map() {
        JFrame map = new JFrame();
        FrameManager.setTitle(map);
        FrameManager.setIcon(map);
        initCharacterPlacementPhase();
        map.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map.setSize(900, 900);

        setNumPlayers(4);//TODO debug sake


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

                //devono stare final altrimenti da errori
                final int finalI = i;
                final int finalJ = j;

                mapBase[i][j] = new JPanel();
                mapBase[i][j].setBackground(Color.WHITE);
                mapBase[i][j].setLayout(new FlowLayout());

                // Aggiungo gli action listener al click
                btnMatrix[i][j] = createButton(i, j);

                if(i == 0 && j == COLS - 1) { //if(è nel bottone finish, ovvero quello dello switch della fase)
                    JButton btnFinish = btnMatrix[i][j];
                    btnFinish.addActionListener(f -> {
                        initializeCurrentPlayer();
                        System.out.println("siamo al giocatore numero" + getCurrentPlayer()); //debug sake
                        mapBase[finalI][finalJ].remove(btnFinish); //TODO come rimuovo questo bottone di merda?
                        if (getCharacterPlacementPhase() == false) {
                            btnMatrix[finalI][finalJ] = createButton(finalI, finalJ); //TODO una volta creato il bottone devo trovare il modo di non farlo ricreare nuovamente
                            mapBase[finalI][finalJ].add(btnMatrix[finalI][finalJ], BorderLayout.CENTER);
                        }
                        updateCharacterPlacementPhase();
                        p2Map(map, mapBase, btnMatrix, finalI, finalJ); //TODO mi chiama ventimila volte p2Map(..) perché non rimuovo l'actionlistener
                        map.repaint();
                        map.validate();
                    });
                } else {
                    // 'previousColorState' deve essere final e non puo' essere modificato dentro i metodi richiamati agli eventi
                    // Soluzione proposta dall'IDE
                    // Oggetto utilizzato per salvare lo stato del colore precedente al ricoloramento in grigio
                    final Color[] previousColorState = { btnMatrix[finalI][finalJ].getBackground() };

                    btnMatrix[i][j].addActionListener(e -> previousColorState[0] = switchBackground(mapBase, finalI, finalJ, previousColorState[0], btnMatrix, map));

                    /* btnMatrix[i][j].addMouseListener(new MouseAdapter() {
                        // Quando l'utente passa sopra una cella cambia colore e diventa grigio
                        
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            previousColorState[0] = mapBase[finalI][finalJ].getBackground();
                            Color menuColor = Color.decode("#EF7B10"); //da mettere grigio, non arancione
                            mapBase[finalI][finalJ].setBackground(menuColor);
                        }

                        // Reimposta il colore della cella
                        @Override
                        public void mouseExited(MouseEvent e) {
                            mapBase[finalI][finalJ].setBackground(previousColorState[0]);
                        }
                    }); */
                }
                mapBase[i][j].add(btnMatrix[i][j], BorderLayout.CENTER); //la linea terra di default alla base della mappa
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