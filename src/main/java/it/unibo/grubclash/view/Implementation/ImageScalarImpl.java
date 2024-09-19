package it.unibo.grubclash.view.Implementation;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import it.unibo.grubclash.view.Application_Programming_Interface.ImageScalar;

public class ImageScalarImpl implements ImageScalar {

    private static final int NEW_X = 0;
    private static final int NEW_Y = 0;

    @Override
    public BufferedImage scaleImage(BufferedImage original, int width, int height){

        int imageType = original.getType();
        
        // Custom or Unknown Image Type Management
        if (imageType == 0) {
            // Use a predefined image type
            imageType = BufferedImage.TYPE_INT_ARGB;
        }
        
        BufferedImage scaledImage = new BufferedImage(width, height, original.getType());
        Graphics2D g2 = scaledImage.createGraphics();
        g2.drawImage(original, NEW_X, NEW_Y, width, height, null);
        g2.dispose();
        return scaledImage;
    }
}
