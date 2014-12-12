package com.sng;

import com.sng.screen.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainTest {

    public static String block;
    static Game game;

    public static void main(String[] args) throws IOException {
        // testImage();

        int count = 0;
        game = new Game("fftttt");

        String input = readFile("test.txt");
        String[] list = input.split("#Game");

        for (String gameData : list) {

            if (count == 0) {
                count++;
                continue;
            }

            String[] tokens = gameData.split(Pattern.quote(" **"));

            for (int i = 0; i < tokens.length; i++) {
                block = tokens[i];
                switch (i) {
                    case 0:
                        parseGameId();
                        break;
                    case 1:
                        parseBlind();
                        break;
                    case 2:
                        parsePlayers();
                        break;
                    case 3:
                        parsePreFlop();
                        break;
                    case 4:
                        parseFlop();
                        break;
                    case 5:
                        parseTurn();
                        break;
                    case 6:
                        parseRiver();
                        break;
                    case 7:
                        parseSummary();
                        break;
                    default:
                        break;
                }
            }

            if (count == 1) {
                System.out.println(game.toString());
                return;
            }
            count++;
        }
    }

    private static void parseFlop() {
        String[] tokens = block.split("\\n");

        if (game.getFlop().getPlayersNumber() != (tokens.length - 2)) {
            throw new NullPointerException();
        }
    }

    private static void parseTurn() {
        String[] tokens = block.split("\\n");

        if (game.getFlop().getPlayersNumber() != (tokens.length - 2)) {
            throw new NullPointerException();
        }
    }

    private static void parseRiver() {
        System.out.println();
    }
    private static void parseSummary() {
        System.out.println();
    }

    private static void parsePreFlop() {
        String[] tokens = block.split("\\n");
        game.setCards(tokens[1].split("\\[")[1]);

        if (game.getPlayersNumber() != (tokens.length - 3)) {
            throw new NullPointerException();
        }

        for (int i = 0; i < game.getPlayersNumber(); i++) {
            parseMove(tokens[i + 2]);
        }
    }

    private static void parseMove(String token) {
        String[] list = token.split(" ");
        // set player move depend on the name
    }

    private static void parsePlayers() {
        String[] tokens = block.split("Seat ");
        game.setButton(tokens[1].charAt(0) - '0');
        int number = (tokens[1].split(": ")[1].charAt(0) - '0');
        game.setPlayersNumber(number);

        for (int i = 0; i < number; i++) {
            String[] playerData = tokens[i + 2].split(" ");
            int seat = playerData[0].charAt(0) - '0';
            String name = playerData[1];
            if (name.equals(game.getName())) {
                game.setSeat(seat);
            }
            int stack = getInt(playerData[3]);
            Player player = new Player(seat, name, stack);
            game.addPlayer(player);
            if (i == (number - 1)) {
                //  get sb and bb players names
            }
        }
    }

    private static void parseBlind() {
        game.setBigBlind(Integer.valueOf(block.split(Pattern.quote("$"))[2].split(" ")[0]));
    }

    private static void parseGameId() {
        System.out.println(getInt(block.split(" ")[3]));
    }

    private static int getInt(String line) {
        return Integer.parseInt(line.replaceAll("\\D", ""));
    }

    static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

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
}
