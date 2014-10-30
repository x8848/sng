package com.sng.Table;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FlopTurnRiver {

    int cardWidth = 15;
    int cardHeight = 21;

    int cardY = 234;   // for flop1 + 1;

    int flop1X = 426;
    int flop2X = 514;
    int flop3X = 601;
    int turnX = 688;
    int riverX = 775;

    void getCards(BufferedImage image) {
        try {

            String timeStamp = System.currentTimeMillis() + ".png";
            int cardX = 0;

            BufferedImage cardImage = image.getSubimage(cardX, cardY, cardWidth, cardHeight);

            ImageIO.write(cardImage, "png", new File("images/cards/" + timeStamp));


        } catch (IOException e) {
        }
    }

}
