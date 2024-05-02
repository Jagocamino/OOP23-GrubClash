package it.unibo.grubclash.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel{
    
    private BufferedImage image;
    
    public PlayerPanel() {

        try {

            image = ImageIO.read(new File("src\\main\\resources\\menu\\Grub.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image, 0, 0, 50, 50, null);
    }
}
