package com.sng;

import com.sng.screen.*;
import com.sng.screen.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class MainTest {

    public static final String BB = "big blind";

    static final String button = "is the button";
    static final String players = "Total number of players :";
    static final String pf = "** Dealing down cards **";

    static final String flop = "Dealing flop";
    static final String turn = "Dealing turn";
    static final String river = "Dealing river";

    static final String name = "fftttt";
    static final String CARDS = "Dealt to " + name;

    static String line;

    public static void main(String[] args) throws IOException {
        // testImage();
        int count = 0;
        for (String game : readFile("test.txt").split("#Game")) {
            parseGame(game);
        }
    }

    static void parseGame(String block) throws IOException {
        StringReader reader = new StringReader(block);
        BufferedReader br = new BufferedReader(reader);
        String cards = "";
        String blind = "";

        while ((line = br.readLine()) != null) {

            if (line.contains(flop) || (line.contains("Summary"))) {
                break;
            }

            if (line.contains(CARDS)) {
                cards = line.split(CARDS)[1];
            }
            if (line.startsWith(name + " posts")) {
                blind = line.replaceAll("\\D","");
            }


            if (line.startsWith(name)) {
                if (!line.startsWith(name + " folds") && !line.startsWith(name + " posts")) {
                    System.out.println(cards + " & " + line + " & " + blind);
                    blind = "";
                }
            }
        }
    }

    private static String getValue(String value) {
        if (line.contains(value)) {
            return line.split(value)[1];
        }
        return null;
    }

    static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            String board = "";

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    static void testImage() {
        try {
            BufferedImage image = ImageIO.read(new File("images/test/all.png"));

            List<Player> playerList = new ArrayList<Player>();

            PlayerState state = new PlayerState();
            PlayerButton button = new PlayerButton();
            PlayerStack stack = new PlayerStack();
            PlayerBet bet = new PlayerBet();
            TableCards cards = new TableCards();

            System.out.println("pot " + bet.getPot(image));
            System.out.println("bank " + bet.getBank(image));
            System.out.println("my cards " + cards.getHand(image));
            System.out.println("table cards " + cards.getFlopTurnRiver(image));
            System.out.println("---------------");


            for (int i = 0; i < 9; i++) {
                System.out.println("player" + i);
                Player player = new Player(i);
                if (i != 0) System.out.println("state " + state.getState(image, i));
                System.out.println("button " + button.checkButton(image, i));
                if (i != 0) System.out.println("stack " + stack.getStack(image, i));
                System.out.println("bet " + bet.getPlayerBet(image, i));
                System.out.println("---------------");
                playerList.add(player);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ;
}
