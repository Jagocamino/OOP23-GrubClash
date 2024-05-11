package it.unibo.grubclash.view.Implementation;

import it.unibo.grubclash.view.Application_Programming_Interface.FrameManager;

import javax.swing.*;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class Menu extends JFrame{

    //FM creo il FrameManager visto che creando l'interfaccia non posso avere più i metodi statici
    private static final FrameManager frameManager = new FrameManagerImpl();

    final char FS = File.separatorChar;

    public Menu() {

        //Creazione del frame
        JFrame frameMenu = new JFrame();
        frameMenu.setTitle("GrubClash");
        frameMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameMenu.setSize(frameManager.getWindowWidth(), frameManager.getWindowHeight());
        frameMenu.setResizable(false);
        frameMenu.setLayout(new BorderLayout());

        //visto che ho creato l'interfaccia di FrameManager e non posso mettere i metodi statici creo il frame Fanager e gli setto l'icono FM
        FrameManager frameManager = new FrameManagerImpl();
        frameManager.setIcon(frameMenu);

        //Pannello menù
        final JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, frameManager.getWindowWidth(), frameManager.getWindowHeight());
        frameMenu.add(panel);

        //Impostazione dello sfondo del menu
        ImageIcon image = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "menu_bg.jpg");
        JLabel menu_bg = new JLabel(image);
        menu_bg.setBounds(0, 0, frameManager.getWindowWidth(), frameManager.getWindowHeight());

        //Creazione del bottone play
        final JButton playButton = new JButton();
        playButton.setBorderPainted(false);
        playButton.setBounds(frameManager.getWindowWidth()-1150,frameManager.getWindowHeight()-350, 200, 200);

        ImageIcon originalIcon = new ImageIcon("src" + FS + "main" + FS + "resources" + FS + "menu" + FS + "play.png");
        Image originalImage = originalIcon.getImage();

        ImageIcon scaledIcon = new ImageIcon(originalImage.getScaledInstance(playButton.getWidth(), playButton.getHeight(), Image.SCALE_SMOOTH));
        playButton.setIcon(scaledIcon);

        JComboBox<Integer> playerSelect = new JComboBox<>(new Integer[]{2, 3, 4, 5});
        playerSelect.setBounds(frameManager.getWindowWidth()-950, frameManager.getWindowHeight()-455, 200, 30);

        // Evento scatenato al click del bottone
        playButton.addActionListener(e -> {
            Object player = playerSelect.getSelectedItem();
            int playerCount=0;
            if (player != null) {
                playerCount = (int) player;
            }
            else{
                System.out.println("Errore nella selezione del player");
                System.exit(1);
            }
            frameMenu.dispose();
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

        panel.add(playButton);
        panel.add(playerSelect);
        panel.add(menu_bg);
        frameMenu.setVisible(true);
    }
}
