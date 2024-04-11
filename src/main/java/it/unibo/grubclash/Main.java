package it.unibo.grubclash;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {

    static final int width_menu = 1702;
    static final int height_menu = 956;

    public static void main(String[] args) {

        final JFrame frame = new JFrame();
        frame.setTitle("GrubClash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width_menu, height_menu);
        frame.setResizable(false);
        frame.setLayout(null);

        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, width_menu, height_menu);
        frame.add(panel);

        // Impostazione dello sfondo del menu
        ImageIcon image = new ImageIcon("src\\main\\resources\\menu\\menu_bg.jpg");
        JLabel menu_bg = new JLabel(image);
        menu_bg.setBounds(0, 0, width_menu, height_menu);

        // Creazione del bottone play
        final JButton playButton = new JButton();
        playButton.setBorderPainted(false);
        playButton.setBounds(width_menu - 1300, height_menu - 420, 200, 200);

        ImageIcon originalIcon = new ImageIcon("src\\main\\resources\\menu\\play.png");
        Image originalImage = originalIcon.getImage();

        ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
        playButton.setIcon(scaledIcon);

        // Evento scatenato al click del bottone
        playButton.addActionListener(e ->
            JOptionPane.showMessageDialog(null, "Bottone cliccato!")
        );

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
        panel.add(playButton);
        panel.add(menu_bg);
        frame.setVisible(true);
    }

}