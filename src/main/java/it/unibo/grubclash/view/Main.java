package it.unibo.grubclash.view;

import javax.swing.*;

import it.unibo.grubclash.model.GrubPanel;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    
    static final int MENU_WIDTH = 1502;
    static final int MENU_HEIGHT = 956;

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("GrubClash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(MENU_WIDTH, MENU_HEIGHT);
        frame.setResizable(false);
        frame.setLayout(null);
        FrameManager.setIcon(frame);

        //Pannello menù
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);
        frame.add(panel); 

        //Pannello di gioco
        GrubPanel grubPanel = new GrubPanel();
        frame.add(grubPanel);

        // Impostazione dello sfondo del menu
        ImageIcon image = new ImageIcon("src\\main\\resources\\menu\\menu_bg.jpg");
        JLabel menu_bg = new JLabel(image);
        menu_bg.setBounds(0, 0, MENU_WIDTH, MENU_HEIGHT);

        // Creazione del bottone play
        final JButton playButton = new JButton();
        playButton.setBorderPainted(false);
        playButton.setBounds(MENU_WIDTH - 1300, MENU_HEIGHT - 420, 200, 200);

        ImageIcon originalIcon = new ImageIcon("src\\main\\resources\\menu\\play.png");
        Image originalImage = originalIcon.getImage();

        ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
        playButton.setIcon(scaledIcon);

        JComboBox<Integer> playerSelect = new JComboBox<>(new Integer[]{2, 3, 4, 5});
        playerSelect.setBounds(MENU_WIDTH - 1080, MENU_HEIGHT - 532, 200, 30);

        // Evento scatenato al click del bottone
        playButton.addActionListener(e -> {
            int playerCount = (int)playerSelect.getSelectedItem();
            frame.dispose();
            MapBuilder.p1Map();
        });

        // Effetto sul bottone al passaggio del mouse
        playButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                ImageIcon originalIcon = new ImageIcon("src\\main\\resources\\menu\\play_on_hover.png");
                Image originalImage = originalIcon.getImage();

                ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
                playButton.setIcon(scaledIcon);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                ImageIcon originalIcon = new ImageIcon("src\\main\\resources\\menu\\play.png");
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