package com.sng.Table;

import com.sng.image.BlackWhite;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlopTurnRiver {

    public static void main(String[] args) {

        int cardWidth = 16;
        int cardHeight = 24;

        int cardY = 232;

        int flop1X = 425;
        int flop2X = 513;
        int flop3X = 600;
        int turnX = 687;
        int riverX = 774;

        int[] X = {flop1X, flop2X, flop3X, turnX, riverX};


        try {

            int newCardIndex = 132;


            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRect = new Rectangle(screenSize);


            //BufferedImage screen = ImageIO.read(new File("images/bank.png"));


            while (true) {


                Thread.sleep(5000);

                System.out.println("make screen shoot, card index is " + newCardIndex);

                BufferedImage screen = new Robot().createScreenCapture(screenRect);

                for (int x : X) {

                    BufferedImage cardImage = screen.getSubimage(x, cardY, cardWidth, cardHeight);

                    int length = newCardIndex;
                    boolean write = true;

                    if (length != 0) {

                        for (int j = 0; j < length; j++) {
                            BufferedImage card = ImageIO.read(new File("images/cards/" + j + ".png"));

                            if (BlackWhite.imagesAreEqual(cardImage, card)) {
                                write = false;
                            }
                        }
                        if (write) {
                            ImageIO.write(cardImage, "png", new File("images/cards/" + newCardIndex + ".png"));
                            newCardIndex = newCardIndex + 1;
                        }
                    } else {
                        ImageIO.write(cardImage, "png", new File("images/cards/" + newCardIndex + ".png"));
                        newCardIndex = newCardIndex + 1;
                    }
                }
            }

        } catch (IOException e) {
        } catch (AWTException e) {
        } catch (InterruptedException e) {
        }
    }
}
