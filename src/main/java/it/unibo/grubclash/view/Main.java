package it.unibo.grubclash.view;

import javax.swing.*;
import java.io.File;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    public static void main(String[] args) {

        final char FS = File.separatorChar;

        JFrame frame = new JFrame();
        frame.setTitle("GrubClash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FrameManager.WINDOW_WIDTH, FrameManager.WINDOW_HEIGHT);
        frame.setResizable(false);
        frame.setLayout(null);
        FrameManager.setIcon(frame);

        //Pannello menù
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, FrameManager.WINDOW_WIDTH, FrameManager.WINDOW_HEIGHT);
        frame.add(panel); 

        //Pannello di gioco
        /* GrubPanel grubPanel = new GrubPanel();
        frame.add(grubPanel); */

        // Impostazione dello sfondo del menu
        ImageIcon image = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "menu_bg.jpg");
        JLabel menu_bg = new JLabel(image);
        menu_bg.setBounds(0, 0, FrameManager.WINDOW_WIDTH, FrameManager.WINDOW_HEIGHT);

        // Creazione del bottone play
        final JButton playButton = new JButton();
        playButton.setBorderPainted(false);
        playButton.setBounds(FrameManager.getPercentage(FrameManager.WINDOW_WIDTH, 75), FrameManager.getPercentage(FrameManager.WINDOW_HEIGHT, 45), 200, 200);

        ImageIcon originalIcon = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play.png");
        Image originalImage = originalIcon.getImage();

        ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
        playButton.setIcon(scaledIcon);

        JComboBox<Integer> playerSelect = new JComboBox<>(new Integer[]{2, 3, 4, 5});
        playerSelect.setBounds(FrameManager.getPercentage(FrameManager.WINDOW_WIDTH, 63), FrameManager.getPercentage(FrameManager.WINDOW_HEIGHT, 55), 200, 30);

        // Evento scatenato al click del bottone
        playButton.addActionListener(e -> {
            int playerCount = (int)playerSelect.getSelectedItem();
            frame.dispose();
            MapBuilder mb = new MapBuilder(playerCount);
            mb.p1Map();
        });

        // Effetto sul bottone al passaggio del mouse
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

        // Aggiunta componenti al frame
        panel.add(playerSelect);
        panel.add(playButton);
        panel.add(menu_bg);
        frame.setVisible(true);
    }

}