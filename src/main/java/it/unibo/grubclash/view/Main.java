package it.unibo.grubclash.view;

import javax.swing.*;

import it.unibo.grubclash.model.GrubPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class Main {
    
    static final int width_menu = 1702;
    static final int height_menu = 956;
    public static JFrame frame;

    public static void main(String[] args) {

        frame = new JFrame();
        frame.setTitle("GrubClash");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width_menu, height_menu);
        frame.setResizable(false);
        frame.setLayout(null);
        new Main().setIcon();

        //Pannello menù
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, width_menu, height_menu);
        frame.add(panel); 

        //Pannello di gioco
        GrubPanel grubPanel = new GrubPanel();
        frame.add(grubPanel);

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
        /*playButton.addActionListener(e ->
            JOptionPane.showMessageDialog(null, "Bottone cliccato!")
        );*/
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TestMappa.p1mappa(frame);
            }
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
        panel.add(playButton);
        panel.add(menu_bg);
        frame.setVisible(true);
    }

    //icona della applicazione
    public void setIcon(){
        ImageIcon icon = new ImageIcon("src\\main\\resources\\menu\\Grub.png");
        frame.setIconImage(icon.getImage());
    }

}