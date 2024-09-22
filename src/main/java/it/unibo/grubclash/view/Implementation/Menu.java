package it.unibo.grubclash.view.Implementation;

import it.unibo.grubclash.controller.Implementation.MapBuilderImpl;
import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Menu extends JFrame{

    private static final FrameManager frameManager = FrameManagerImpl.getInstance();

    private final char FS = File.separatorChar;
    private JComboBox<Integer> playerSelect;

    //Setting the Menu Background
    private static final int newX=0;
    private static final int newY=0;

    //Pixel removal Insert the combobox (playerSelect) in the right place
    private static final int removePixelsPlayerSelectX = 950;
    private static final int removePixelsPlayerSelectY = 455;
    private static final int widthPlayerSelect = 200;
    private static final int heightPlayerSelect = 30;

    //Pixel removal Insert the PlayButton in the right place
    private static final int removePixelsPlayButtonX = 1150;
    private static final int removePixelsPlayButtonY = 350;
    private static final int widthPlayButton = 200;
    private static final int heightPlayButton = 200;

    //Pixel removal Insert the play2 (Jbutton) in the right place
    private static final int removePixelsPlay2X = 100;
    private static final int widthPlay2 = 100;
    private static final int heightPlay2 = 100;

    public Menu() {

        JFrame frameMenu = createMenuFrame();
        JPanel panel = createMenuPanel(frameMenu);

        ImageIcon image = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "menu_bg.jpg");
        JLabel menu_bg = new JLabel(image);
        menu_bg.setBounds(newX, newY, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());

        //Creating the play button
        JButton playButton = createPlayButton(frameMenu);


        playerSelect = new JComboBox<>(new Integer[]{2, 3, 4, 5});
        playerSelect.setBounds(frameManager.getWindowWidth().get()-removePixelsPlayerSelectX, frameManager.getWindowHeight().get() - removePixelsPlayerSelectY, widthPlayerSelect, heightPlayerSelect);

        panel.add(playButton);
        panel.add(playerSelect);
        panel.add(menu_bg);
        frameMenu.setVisible(true);
    }

    protected JFrame createMenuFrame() {
        JFrame frameMenu = new JFrame();
        frameMenu.setTitle("GrubClash");
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setSize(frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        frameMenu.setResizable(false);
        frameMenu.setLayout(new BorderLayout());
        frameManager.setIcon(frameMenu);
        return frameMenu;
    }

    protected JPanel createMenuPanel(JFrame frameMenu) {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(newX, newY, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        frameMenu.add(panel);
        return panel;
    }

    protected JButton createPlayButton(JFrame frameMenu) {
        JButton playButton = new JButton();

        playButton.setBorderPainted(false);
        playButton.setBounds(frameManager.getWindowWidth().get() - removePixelsPlayButtonX, frameManager.getWindowHeight().get() - removePixelsPlayButtonY, widthPlayButton, heightPlayButton);

        ImageIcon originalIcon = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play.png");
        Image originalImage = originalIcon.getImage();
        ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
        playButton.setIcon(scaledIcon);

        playButton.addActionListener(e -> {
            frameMenu.dispose();
            JFrame frameMap = createMenuFrame();
            createMapPanel(frameMap);
            frameMap.setVisible(true);
        });

        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon originalIcon = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play_on_hover.png");
                Image originalImage = originalIcon.getImage();
                ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
                playButton.setIcon(scaledIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon originalIcon = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play.png");
                Image originalImage = originalIcon.getImage();
                ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
                playButton.setIcon(scaledIcon);
            }
        });

        return playButton;
    }

    protected JPanel createMapPanel(JFrame frameMap) {
        JPanel panelMap = new JPanel();

        panelMap.setLayout(null);
        panelMap.setBackground(Color.WHITE);
        panelMap.setBounds(newX, newY, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());

        ImageIcon map = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "grubRules.png");
        JLabel map_label = new JLabel(map);
        map_label.setBounds(newX, newY, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());

        JButton play2 = new JButton();

        play2.setBorderPainted(false);
        play2.setBounds((frameManager.getWindowWidth().get() / 2) - removePixelsPlay2X, newY, widthPlay2, heightPlay2);
        ImageIcon originalIcon2 = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play2.png");
        play2.setIcon(originalIcon2);

        play2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon originalIcon2 = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play_on_hover2.png");
                play2.setIcon(originalIcon2);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon originalIcon2 = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play2.png");
                play2.setIcon(originalIcon2);
            }
        });

        play2.addActionListener(b -> {
            frameMap.dispose();
            Object player = playerSelect.getSelectedItem();
            int playerCount = 0;
            if (player != null) {
                playerCount = (int) player;
            } else {
                System.out.println("Errore nella selezione del player");
                System.exit(1);
            }
            new MapBuilderImpl(playerCount);
        });

        panelMap.add(play2);
        panelMap.add(map_label);
        frameMap.add(panelMap);

        return panelMap;
    }
}
