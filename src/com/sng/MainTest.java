package com.sng;

import com.sng.screen.*;
import com.sng.screen.Player;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


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

    public static List<Player> playerList = new ArrayList<Player>();

    public static void main(String[] args) throws IOException {
        // testImage();
        int count = 0;
        for (String game : readFile("test.txt").split("#Game")) {



            String stars = Pattern.quote(" **");


            for (String s : game.split(stars)) {
                System.out.println(s);
                System.out.println("----");
            }

            if (count == 1) return;

            parseGame(game);
           // System.out.println("player number is " + playerList.size());

            count++;

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

            boolean isButton = line.endsWith(button);

            if (!isButton && line.startsWith("Seat")) {

                String[] string = line.split(" ");
                playerList.add(new Player(getInt(string[1]), string[2], getInt(string[4])));

            }

            if (line.endsWith(button)) {
                System.out.println("button is " + getInt(line));
            }

            if (line.startsWith(players)) {
                System.out.println(players + " " + line.replaceAll("\\D", ""));
            }

            if (line.contains(CARDS)) {
                cards = line.split(CARDS)[1];
            }
            if (line.startsWith(name + " posts")) {
                blind = line.replaceAll("\\D", "");
            }


            if (line.startsWith(name)) {
                if (!line.startsWith(name + " folds") && !line.startsWith(name + " posts")) {
                    System.out.println(cards + " & " + line + " & " + blind);
                    blind = "";
                }
            }
        }
    }

    private static int getInt(String line) {
        return Integer.parseInt(line.replaceAll("\\D", ""));
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
                Player player = new Player(i, "", 0);
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
