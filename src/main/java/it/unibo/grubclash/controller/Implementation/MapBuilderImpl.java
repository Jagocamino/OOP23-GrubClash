package it.unibo.grubclash.controller.Implementation;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;

import it.unibo.grubclash.controller.Application_Programming_Interface.MapBuilder;
import it.unibo.grubclash.model.Implementation.EnumEntity;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;
import it.unibo.grubclash.view.Implementation.FrameManagerImpl;

import java.io.File;
import java.io.IOException;

public class MapBuilderImpl extends Canvas implements MapBuilder {

    private static final FrameManager frameManager = FrameManagerImpl.getInstance();

    private static final String color_game = "#EF7B10";

    private static final char FS = File.separatorChar;

    private static int itemNum;
    
    public static int getItemNum() {
        return itemNum;
    }

    private static JFrame mapContainer;
    public static JFrame getMapContainer() {
        return mapContainer;
    }
    public static void setMapContainer(JFrame mapContainer) {
        MapBuilderImpl.mapContainer = mapContainer;
    }

    private static JPanel map;
    public static JPanel getMap() {
        return map;
    }
    public static void setMap(JPanel map) {
        MapBuilderImpl.map = map;
    }

    private static JPanel mapBase[][];
    public static JPanel[][] getMapBase() {
        return mapBase;
    }
    public static JPanel getMapBase (int i, int j) {
        return mapBase[i][j];
    }
    public static void setMapBase(JPanel[][] mapBase) {
        MapBuilderImpl.mapBase = mapBase;   
    }
    public static int getXMapBase(int i, int j){
        return MapBuilderImpl.mapBase[i][j].getX();
    }
    public static int getYMapBase(int i, int j){
        return MapBuilderImpl.mapBase[i][j].getY();
    }
    public static int getWidthMapBase(int i, int j){
        return MapBuilderImpl.mapBase[i][j].getWidth();
    }
    public static int getHeightMapBase(int i, int j){
        return MapBuilderImpl.mapBase[i][j].getHeight();
    }


    private static JButton btnMatrix[][];
    public static JButton[][] getBtnMatrix() {
        return btnMatrix;
    }
    public static JButton getBtnMatrix(int i, int j) {
        return btnMatrix[i][j];
    }
    public static void setBtnMatrix(JButton[][] btnMatrix) {
        MapBuilderImpl.btnMatrix = btnMatrix;
    }

    private static JLayeredPane layeredPaneGrid;
    public static JLayeredPane getLayeredPaneGrid() {
        return layeredPaneGrid;
    }
    public static void setLayeredPaneGrid(JLayeredPane layeredPaneGrid) {
        MapBuilderImpl.layeredPaneGrid = layeredPaneGrid;
    }

    protected static int currentPlayer;
    protected static int numPlayers;
    
    protected static Color playerColor[];

    private static boolean characterPlacementPhase; //true when first finish is clicked
    private static boolean colorSpawnpoint;
    private static boolean mapDrawer;

    public final static int ROWS = 20;
    public final static int COLS = 20; 

    public static EnumEntity.Entities[][] entityMatrix;

    public MapBuilderImpl(final int playerCount) {
        setNumPlayers(playerCount);
        MapBuilderImpl.entityMatrix = new EnumEntity.Entities[ROWS][COLS];
        mapDrawer = false;
        p1Map();
    }

    public static boolean getMapDrawer () {
        return mapDrawer;
    }

    public static void updateMapDrawer () {
        if (mapDrawer == false) {
            mapDrawer = true;
        }else{
            mapDrawer = false;
        }
    }

    public static EnumEntity.Entities[][] getEntityMatrix() {
        return entityMatrix;
    }

    public static void setEntityInMatrix (int i, int j, EnumEntity.Entities entity) {
        MapBuilderImpl.entityMatrix[i][j] = entity;
    }

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
    
    @Override
    public Color switchBackground(int i, int j, Color color) {
        JPanel[][] mapBase = getMapBase();
        if (getCharacterPlacementPhase() == false) {
            Color btnColor = (color == Color.WHITE ? Color.BLACK : Color.WHITE);
            mapBase[i][j].setBackground(btnColor);
            return btnColor;
        }else{ // if we can place the players
            Color btnColor;
            if (mapBase[i][j].getBackground() == Color.WHITE && getColorSpawnpoint() == false) { //permetto il cambio del colore solo se o lo sfondo è bianco, o è dello stesso colore del player che voglio cambiare
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

    private boolean canSwitchPhase() {
        int counter = 0;
        for(int i = 0; i < ROWS; i++ ) {
            for(int j = 0; j < COLS; j++) {
                if (mapBase[i][j].getBackground() == Color.WHITE) {
                    counter++;
                }
            }
        }
        if (counter >= ( getNumPlayers() + getItemNum())) {
            return true;
        }
        return false;
    }

    public static void getCh() {  
        final JFrame frame = new JFrame();  
        synchronized (frame) {  
            frame.setUndecorated(true);  
            frame.getRootPane().setWindowDecorationStyle(JRootPane.FRAME);  
            frame.addKeyListener(new KeyListener() {
                @Override 
                public void keyPressed(KeyEvent e) {  
                    synchronized (frame) {  
                        frame.setVisible(false);  
                        frame.dispose();  
                        frame.notify();  
                    }  
                }  
                @Override 
                public void keyReleased(KeyEvent e) {  
                }  
                @Override 
                public void keyTyped(KeyEvent e) {  
                }  
            });  
            frame.setVisible(true);  
            try {  
                frame.wait();  
            } catch (InterruptedException e1) {  
            }  
        }  
    }

    public static void setNumPlayers ( int numPlayers ) {
        MapBuilderImpl.numPlayers = numPlayers;
        initPlayerColor();
    }

    public static int getNumPlayers () {
        return numPlayers;
    }

    public static int getCurrentPlayer () {
        return MapBuilderImpl.currentPlayer;
    }

    protected static void updateCurrentPlayer () {
        if ( getCurrentPlayer() < getNumPlayers() ) {
            MapBuilderImpl.currentPlayer++;
        }else{
            initializeCurrentPlayer();
        }
    }

    protected static void initializeCurrentPlayer () {
        MapBuilderImpl.currentPlayer = 0;
    }

    private static void initPlayerColor () {
        Color[] playerColor = new Color[getNumPlayers()]; // colors identifies players
        for (int f = 0; f < getNumPlayers(); f++) {
            switch (f) {
                case 0:
                    playerColor[f] = Color.GREEN;
                    break;
                case 1:
                    playerColor[f] = Color.CYAN;
                    break;
                case 2:
                    playerColor[f] = Color.ORANGE;
                    break;
                case 3:
                    playerColor[f] = Color.YELLOW;
                    break;
                case 4:
                    playerColor[f] = Color.RED;
                    break;
            }
        }
        MapBuilderImpl.playerColor = playerColor;
    }

    private static Color getPlayerColor (int currentPlayer) {
        return playerColor[currentPlayer];
    }

    private static JButton createButton(int i, int j) {
        final int BUTTON_WIDTH = frameManager.getWindowWidth().get() / ROWS;
        final int BUTTON_HEIGHT = frameManager.getWindowHeight().get() / COLS;

        JButton invisibleBtn = new JButton();
        if (i == 0 && j == COLS - 1) {
            invisibleBtn.setText("Finish");
            invisibleBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        } else { // Gestisce tutti i bottoni che non sono quello di chiusura dell'editing della mappa
            invisibleBtn.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
            invisibleBtn.setBorderPainted(false);
            invisibleBtn.setContentAreaFilled(false);
            invisibleBtn.setOpaque(true);
        }
        return invisibleBtn; 
    }

    private static void mapInitialization() throws IOException {
        JFrame mapContainer = getMapContainer();
        JButton[][] btnMatrix = getBtnMatrix();
        JPanel[][] mapBase = getMapBase();
        mapContainer.setResizable(false);
        for(int i = 0; i < ROWS; i++ ) {
            for(int j = 0; j < COLS; j++) {
                btnMatrix[i][j].setVisible(false);
                if(mapBase[i][j].getBackground() == Color.BLACK) {
                    panelBackground(mapBase, i, j);
                    setEntityInMatrix(i, j, EnumEntity.Entities.WALL);
                }else if(mapBase[i][j].getBackground() == Color.GREEN){
                    mapBase[i][j].setBackground(Color.CYAN);
                    setEntityInMatrix(i, j, EnumEntity.Entities.PLAYER1);
                }else if(mapBase[i][j].getBackground() == Color.CYAN){
                    mapBase[i][j].setBackground(Color.CYAN);
                    setEntityInMatrix(i, j, EnumEntity.Entities.PLAYER2);
                }else if(mapBase[i][j].getBackground() == Color.ORANGE){
                    mapBase[i][j].setBackground(Color.CYAN);
                    setEntityInMatrix(i, j, EnumEntity.Entities.PLAYER3);
                }else if(mapBase[i][j].getBackground() == Color.YELLOW){
                    mapBase[i][j].setBackground(Color.CYAN);
                    setEntityInMatrix(i, j, EnumEntity.Entities.PLAYER4);
                }else if(mapBase[i][j].getBackground() == Color.RED){
                    mapBase[i][j].setBackground(Color.CYAN);
                    setEntityInMatrix(i, j, EnumEntity.Entities.PLAYER5);
                }else {
                    mapBase[i][j].setBackground(Color.CYAN);
                    setEntityInMatrix(i, j, EnumEntity.Entities.SKY);
                }        
                mapBase[i][j].repaint();
            }
        }
        initializeCurrentPlayer();
        createPlayableLayer();
    }

    private static void createPlayableLayer() { 
        JFrame mapContainer = getMapContainer();
        JLayeredPane layeredPaneGrid = getLayeredPaneGrid();
        GrubPanelImpl playableLayer = new GrubPanelImpl(getNumPlayers());
        playableLayer.startGameThread();
        playableLayer.setBounds(0, 0, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        playableLayer.setOpaque(false); 
        layeredPaneGrid.add(playableLayer, JLayeredPane.PALETTE_LAYER);
        layeredPaneGrid.setVisible(true);
        mapContainer.repaint();
        mapContainer.revalidate();
    }

    private static void panelBackground(JPanel[][] mapBase, int i, int j) throws IOException {
        if ( i==0 || mapBase[i-1][j].getBackground() == Color.WHITE || mapBase[i-1][j].getBackground() == Color.CYAN) {
            BufferedImage platformImg = ImageIO.read(new File("src" + FS + "main" + FS + "resources" + FS + "gameplay" + FS + "platform.png"));
            JLabel picLabel = new JLabel(new ImageIcon(platformImg));
            mapBase[i][j].add(picLabel);
        }else{
            BufferedImage platformImg = ImageIO.read(new File("src" + FS + "main" + FS + "resources" + FS + "gameplay" + FS + "platform_ground.png"));
            JLabel picLabel = new JLabel(new ImageIcon(platformImg));
            mapBase[i][j].add(picLabel);
        }
        BufferedImage platformImg = ImageIO.read(new File("src" + FS + "main" + FS + "resources" + FS + "gameplay" + FS + "platform.png"));
        JLabel picLabel = new JLabel(new ImageIcon(platformImg));
        mapBase[i][j].add(picLabel);
    }

    private static void p2Map(int btnFinishI, int btnFinishJ) {
        JButton btnFinish = btnMatrix[btnFinishI][btnFinishJ];
        JButton[][] btnMatrix = getBtnMatrix();

        if (getMapDrawer() == true) {
            updateMapDrawer();
        }
        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                final int finalI = i;
                final int finalJ = j;
                if(btnMatrix[i][j] == btnFinish) {
                    frameManager.showMessageBox("Messaggio", "Seleziona dove posizionare il primo giocatore e premi il bottone finish, fai così per tutti i vari giocatori.", JOptionPane.INFORMATION_MESSAGE);
                    btnFinish.addActionListener(o -> {
                        if (getCurrentPlayer() == getNumPlayers() - 1 && getColorSpawnpoint() == true) {
                            try {
                                mapInitialization();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }else{
                            if (getColorSpawnpoint()) { // if player is placed, it can be updated
                                switch (getCurrentPlayer()) {
                                    case 1:
                                        setEntityInMatrix(finalI, finalJ, EnumEntity.Entities.PLAYER1);
                                        break;
                                    case 2:
                                        setEntityInMatrix(finalI, finalJ, EnumEntity.Entities.PLAYER2);
                                        break;
                                    case 3:
                                        setEntityInMatrix(finalI, finalJ, EnumEntity.Entities.PLAYER3);
                                        break;
                                    case 4:
                                        setEntityInMatrix(finalI, finalJ, EnumEntity.Entities.PLAYER4);
                                        break;
                                    case 5:
                                        setEntityInMatrix(finalI, finalJ, EnumEntity.Entities.PLAYER5);
                                        break;
                                    default:
                                        break;
                                }
                                updateCurrentPlayer();
                            }
                        }
                        initColorSpawnpoint();
                    });
                }
            }
        }
        
        SwingUtilities.updateComponentTreeUI(map);
    }

    @Override
    public void p1Map() {

        JFrame mapContainer = new JFrame();
        setMapContainer(mapContainer);

        FrameManager frameManager = FrameManagerImpl.getInstance();
        frameManager.setTitle(getMapContainer());

        frameManager.setIcon(getMapContainer());
        initCharacterPlacementPhase();
        getMapContainer().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getMapContainer().setSize(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());

        setNumPlayers(numPlayers);
        MapBuilderImpl.itemNum = numPlayers * 5;
        mapContainer.setMinimumSize(new Dimension(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get()));
        mapContainer.setResizable(false);
        JLayeredPane layeredPaneGrid = new JLayeredPane();
        setLayeredPaneGrid(layeredPaneGrid);
        getLayeredPaneGrid().setPreferredSize(mapContainer.getSize());
        getLayeredPaneGrid().setLayout(null);

        JPanel map = new JPanel();
        setMap(map);
        getMap().setBounds(0, 0, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        getMap().setLayout(new GridLayout(ROWS, COLS)); 

        JPanel[][] mapBase = new JPanel[ROWS][COLS];
        setMapBase(mapBase);
        JButton[][] btnMatrix = new JButton[ROWS][COLS];
        setBtnMatrix(btnMatrix);

        for(int i = 0; i < ROWS; i++){
            for(int j = 0; j < COLS; j++){
                final int finalI = i; 
                final int finalJ = j;
                mapBase[i][j] = new JPanel();
                mapBase[i][j].setBackground(Color.WHITE);
                btnMatrix[i][j] = createButton(i, j);
                if(i == 0 && j == COLS - 1) { 
                    JButton btnFinish = btnMatrix[i][j];
                    btnFinish.addActionListener(f -> {
                        if (canSwitchPhase()) {
                            initializeCurrentPlayer();
                            mapBase[finalI][finalJ].remove(btnFinish);
                            if (!getCharacterPlacementPhase()) {
                                btnMatrix[finalI][finalJ] = createButton(finalI, finalJ);
                                mapBase[finalI][finalJ].add(getBtnMatrix(finalI, finalJ), BorderLayout.CENTER);
                            }
                            updateCharacterPlacementPhase();
                            p2Map(finalI, finalJ); 
                            mapContainer.repaint();
                            mapContainer.validate();
                        } else {
                            frameManager.showMessageBox("Messaggio", "Serve più spazio per sviluppare il gioco, ridisegnare la mappa grazie <3.", JOptionPane.INFORMATION_MESSAGE);//FM
                        }
                    });
                } else {

                    final Color[] previousColorState = { getBtnMatrix(finalI, finalJ).getBackground() };
                    btnMatrix[i][j].addMouseListener(new MouseAdapter() {
                    
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            if (!getMapDrawer()) {
                                switchBackground(finalI, finalJ, getMapBase(finalI, finalJ).getBackground());
                            }
                            if (!getCharacterPlacementPhase()) {
                                updateMapDrawer(); // if true, map can be drawn
                            }
                        };

                        @Override
                        public void mouseEntered(MouseEvent e) {
                            if (getMapDrawer()) {
                                switchBackground(finalI, finalJ, getMapBase(finalI, finalJ).getBackground());
                            }
                            previousColorState[0] = btnMatrix[finalI][finalJ].getBackground();
                            Color menuColor = Color.decode(color_game);
                            btnMatrix[finalI][finalJ].setBackground(menuColor);
                            btnMatrix[finalI][finalJ].setContentAreaFilled(true);
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            btnMatrix[finalI][finalJ].setBackground(previousColorState[0]);
                            btnMatrix[finalI][finalJ].setContentAreaFilled(false);
                        }
                    });
                }
                mapBase[i][j].add(btnMatrix[i][j], BorderLayout.CENTER);
                if(i == 19) {
                    mapBase[i][j].setBackground(Color.BLACK);
                }
                btnMatrix[i][j].setBackground(mapBase[i][j].getBackground());
                mapBase[i][j].setVisible(true);
                
                map.add(mapBase[i][j]);
            }
        }   
        layeredPaneGrid.add(map, JLayeredPane.DEFAULT_LAYER);
        mapContainer.add(layeredPaneGrid);
        mapContainer.pack();
        mapContainer.setLocationRelativeTo(null);
        mapContainer.setVisible(true);
        map.setVisible(true);
        frameManager.showMessageBox("Messaggio", "Crea o rimuovi spazi interagendo con i blocchi. Genera così la tua mappa!", JOptionPane.INFORMATION_MESSAGE);//FM
    }

}