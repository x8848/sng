package com.sng.image;

import com.sng.Table.Cards;
import com.sng.Table.PlayerCards;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BlackWhite {
    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("images/out247.png"));
            PlayerCards playerCards = new PlayerCards();
            int i = 0;
            for (Cards cards : playerCards.getCardsList()) {
                i++;
                System.out.print(" "+i + " is " + playerCards.getState(image, i).toString());


            }
       } catch (IOException e) {
            System.out.println("exception");
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
