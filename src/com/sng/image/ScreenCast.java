package com.sng.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenCast extends Thread {

    private Robot robot;
    private volatile boolean isRunning;

    public ScreenCast() {
        try {
            this.robot = new Robot();
            this.isRunning = true;

        } catch (AWTException e) {
        }
    }


    public void run() {
        isRunning = true;
        while (isRunning) {
            captureScreen();
            try {
                Thread.sleep((long) 1000);
            } catch (InterruptedException e) {
            }
        }
    }

    public void kill() {
        isRunning = false;
    }

    private void captureScreen() {
        try {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");

            String newFileName = "/images/" + dateFormat.format(date) + ".png";

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Rectangle screenRect = new Rectangle(screenSize);

            BufferedImage capture = robot.createScreenCapture(screenRect);

            ImageIO.write(capture, "png", new File(newFileName));

            System.out.println("new " + newFileName);

        } catch (IOException e) {
        }
    }

}