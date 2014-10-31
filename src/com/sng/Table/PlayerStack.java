package com.sng.Table;

import com.sng.image.BlackWhite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sng.Table.Stack.commaWidth;
import static com.sng.Table.Stack.numberHeight;
import static com.sng.Table.Stack.numberWidth;

public class PlayerStack extends PlayerCards {

    List<Stack> stackList = new ArrayList<Stack>();

    Map<String, BufferedImage> numbers = new HashMap<String, BufferedImage>();
    BufferedImage comma;
    private static boolean checkComma = false;

    private int stackX;
    private String stackValue;

    public PlayerStack() throws IOException {
        super();
        for (Cards cards : getCardsList()) stackList.add(new Stack(cards.getX() + 13, cards.getY() + 69));

        String fileName = "images/numbers/comma.png";
        comma = ImageIO.read(new File(fileName));

        for (int i = 0; i < 10; i++) {
            fileName = "images/numbers/" + i + ".png";
            BufferedImage number = ImageIO.read(new File(fileName));
            numbers.put(((Integer) i).toString(), number);
        }
    }

    public int getStack(BufferedImage image, int seat) throws IOException {
        Stack stack = stackList.get(seat);

        String value = "";
        stackX = stack.getX();
        int count = 0;

        while (value != null) {
            value = getValue(image, stack.getY());
            if (value != null) {
                stackValue = stackValue + value;
            }
            count++;
            if (count == 1) checkComma = true;
        }

        if (stackValue == null) return 0;

        return 0;
    }

    private String getValue(BufferedImage image, int y) throws IOException {
        if (checkComma) {
            BufferedImage check = image.getSubimage(stackX, y,
                    commaWidth, numberHeight);

            check = BlackWhite.getBlackWhite(check);

            ImageIO.write(check, "png", new File("images/" + stackX + ".png"));

            if (BlackWhite.imagesAreEqual(check, comma)) {
                stackX = stackX + commaWidth;
                checkComma = false;
            }
        }

        BufferedImage numberImage = image.getSubimage(stackX, y,
                numberWidth, numberHeight);

        numberImage = BlackWhite.getBlackWhite(numberImage);

        ImageIO.write(numberImage, "png", new File("images/" + stackX + ".png"));

        for (String number : numbers.keySet()) {
            if (BlackWhite.imagesAreEqual(numberImage, numbers.get(number))) {
                stackX = stackX + numberWidth;
                return number;
            }
        }
        return null;
    }
}