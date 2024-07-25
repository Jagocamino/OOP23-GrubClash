package it.unibo.grubclash.view.Implementation;

import it.unibo.grubclash.controller.Implementation.MapBuilder;
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

    public Menu() {

        JFrame frameMenu = createMenuFrame();
        JPanel panel = createMenuPanel(frameMenu);

        //Impostazione dello sfondo del menu
        ImageIcon image = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "menu_bg.jpg");
        JLabel menu_bg = new JLabel(image);
        menu_bg.setBounds(0, 0, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());

        //Creazione del bottone play
        JButton playButton = createPlayButton(frameMenu);

        //Pixel removal Insert the combobox in the right place
        final int removePixelsX = 950;
        final int removePixelsY = 455;
        final int widthPlayerSelect = 200;
        final int heightPlayerSelect = 30;

        playerSelect = new JComboBox<>(new Integer[]{2, 3, 4, 5});
        playerSelect.setBounds(frameManager.getWindowWidth().get()-removePixelsX, frameManager.getWindowHeight().get() - removePixelsY, widthPlayerSelect, heightPlayerSelect);

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
        final int newX=0;
        final int newY=0;
        panel.setLayout(null);
        panel.setBounds(newX, newY, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());
        frameMenu.add(panel);
        return panel;
    }

    protected JButton createPlayButton(JFrame frameMenu) {
        JButton playButton = new JButton();

        //Pixel removal Insert the PlayButton in the right place
        final int removePixelsX = 1150;
        final int removePixelsY = 350;
        final int widthPlayButton = 200;
        final int heightPlayButton = 200;

        playButton.setBorderPainted(false);
        playButton.setBounds(frameManager.getWindowWidth().get() - removePixelsX, frameManager.getWindowHeight().get() - removePixelsY, widthPlayButton, heightPlayButton);

        ImageIcon originalIcon = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play.png");
        Image originalImage = originalIcon.getImage();
        ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
        playButton.setIcon(scaledIcon);

        playButton.addActionListener(e -> {
            frameMenu.dispose();
            JFrame frameMappa = createMenuFrame();
            createMapPanel(frameMappa);
            frameMappa.setVisible(true);
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

    protected JPanel createMapPanel(JFrame frameMappa) {
        JPanel panelMappa = new JPanel();
        final int newX=0;
        final int newY=0;
        panelMappa.setLayout(null);
        panelMappa.setBackground(Color.WHITE);
        panelMappa.setBounds(newX, newY, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());

        ImageIcon mappa = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "grubRules.png");
        JLabel mappa_label = new JLabel(mappa);
        mappa_label.setBounds(0, 0, frameManager.getWindowWidth().get(), frameManager.getWindowHeight().get());

        JButton play2 = new JButton();

        //Pixel removal Insert the play2 (Jbutton) in the right place
        final int removePixelsX = 100;
        final int new_y = 350;
        final int widthPlay2 = 200;
        final int heightPlay2 = 200;

        play2.setBorderPainted(false);
        play2.setBounds((frameManager.getWindowWidth().get() / 2) - removePixelsX, new_y, widthPlay2, heightPlay2);
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
            frameMappa.dispose();
            Object player = playerSelect.getSelectedItem();
            int playerCount = 0;
            if (player != null) {
                playerCount = (int) player;
            } else {
                System.out.println("Errore nella selezione del player");
                System.exit(1);
            }
            new MapBuilder(playerCount);
        });

        panelMappa.add(play2);
        panelMappa.add(mappa_label);
        frameMappa.add(panelMappa);

        return panelMappa;
    }
}
