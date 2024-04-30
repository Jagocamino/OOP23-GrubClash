package it.unibo.grubclash.view;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    public static void main(String[] args) {

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
        ImageIcon image = new ImageIcon("src\\main\\resources\\menu\\menu_bg.jpg");
        JLabel menu_bg = new JLabel(image);
        menu_bg.setBounds(0, 0, FrameManager.WINDOW_WIDTH, FrameManager.WINDOW_HEIGHT);

        // Creazione del bottone play
        final JButton playButton = new JButton();
        playButton.setBorderPainted(false);
        playButton.setBounds(FrameManager.WINDOW_WIDTH - 1300, FrameManager.WINDOW_HEIGHT - 420, 200, 200);

        ImageIcon originalIcon = new ImageIcon("src\\main\\resources\\menu\\play.png");
        Image originalImage = originalIcon.getImage();

        ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
        playButton.setIcon(scaledIcon);

        JComboBox<Integer> playerSelect = new JComboBox<>(new Integer[]{2, 3, 4, 5});
        playerSelect.setBounds(FrameManager.WINDOW_WIDTH - 1080, FrameManager.WINDOW_HEIGHT - 532, 200, 30);

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