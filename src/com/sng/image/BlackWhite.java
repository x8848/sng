package com.sng.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackWhite {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i++) {
                BufferedImage image = ImageIO.read(new File("images/table/" + i + ".png"));
                BufferedImage blackWhite = getBlackWhite(image);
                ImageIO.write(blackWhite, "png", new File("images/table/bw/" + i + ".png"));
            }

        } catch (IOException e) {
        }


    }

    public static boolean imagesAreEqual(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false;
        }
        for (int x = 0; x < image2.getWidth(); x++) {
            for (int y = 0; y < image2.getHeight(); y++) {
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static BufferedImage getBlackWhite(BufferedImage image) {
        BufferedImage blackWhite = new BufferedImage(image.getWidth(), image.getHeight(),
                BufferedImage.TYPE_BYTE_BINARY);

        Graphics2D g2d = blackWhite.createGraphics();
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return blackWhite;
    }


}
