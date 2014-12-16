package com.sng;

import com.sng.screen.*;
import com.sng.screen.figures.Game;
import com.sng.screen.figures.Player;
import com.sng.screen.figures.Street;

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
    private static String name = "fftttt";
    static Game game;
    static List<Game> gameList = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        // testImage();

        int count = 0;

        String input = readFile("test.txt");
        String[] list = input.split("#Game");

        for (String gameData : list) {

            if (count == 0) {
                count++;
                continue;
            }

            game = new Game(name);

            String[] tokens = gameData.split(Pattern.quote(" **"));

            for (int i = 0; i < tokens.length; i++) {
                block = tokens[i];
                switch (i) {
                    case 0:
                        parseGameNumber();
                        break;
                    case 1:
                        parseBigBlind();
                        break;
                    case 2:
                        parsePlayers();
                        break;
                    case 3:
                        parsePreFlop();
                        break;
                    default:
                        if (!block.split("\\n")[0].equals("")) parseStreet();
                        break;
                }
            }
            gameList.add(game);
            count++;
        }
        System.out.println(gameList.toString());
    }

    private static void parsePreFlop() {
        Street preFlop = game.getPreFlop();

        String[] tokens = block.split("\\n");
        preFlop.setCards(tokens[1].split("\\[")[1]);

        for (int i = 0; i < tokens.length - 3; i++) {
            preFlop.parseMove(tokens[i + 2]);
        }
        game.setPreFlop(preFlop);
    }

    private static void parseStreet() {
        Street street = new Street();
        Street lastStreet = game.getLastStreet();

        street.setPlayerList(lastStreet.getNextStreetPlayerList());
        street.setBank(lastStreet.getBank());

        String[] tokens = block.split("\\n");

        street.setCards(tokens[0]);

        for (int i = 0; i < tokens.length - 2; i++) {
            street.parseMove(tokens[i + 1]);
        }
        game.addStreet(street);
    }

    private static void parsePlayers() {
        String[] tokens = block.split("Seat ");
        int buttonSeat = tokens[1].charAt(0) - '0';
        game.setButtonSeat(buttonSeat);

        Street preFlop = new Street();

        int number = (tokens[1].split(": ")[1].charAt(0) - '0');

        preFlop.setPlayersNumber(number);


        for (int i = 0; i < number; i++) {
            String[] playerData = tokens[i + 2].split(" ");
            int seat = playerData[0].charAt(0) - '0';

            String name = playerData[1];
            if (name.equals(game.getPlayerName())) {
                game.setPlayerSeat(seat);
            }
            int stack = getInt(playerData[3]);
            Player player = new Player(seat, name, stack);

            preFlop.addPlayer(player);

            if (i == (number - 1)) {
                String[] split = tokens[i + 2].split("\\n");
                for (int j = 0; j < split.length - 2; j++) {
                    preFlop.parseMove(split[j + 1]);
                }
            }
        }
        game.setPreFlop(preFlop);
    }

    private static void parseBigBlind() {
        game.setBigBlind(Integer.valueOf(block.split(Pattern.quote("$"))[2].split(" ")[0]));
    }

    private static void parseGameNumber() {
        int number = getInt(block.split(" ")[3]);
        game.setGameNumber(number);
    }

    public static int getInt(String line) {
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

            List<Player> playerList = new ArrayList<>();

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
