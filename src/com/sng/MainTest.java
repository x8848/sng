package com.sng;

import com.sng.screen.*;
import com.sng.screen.figures.Game;
import com.sng.screen.figures.Player;
import com.sng.screen.figures.State;
import com.sng.screen.figures.Street;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws IOException {
        // testImage();
        testFileParser();
    }

    private static void testFileParser() throws IOException {
        GameService service = new GameService();
        analyze(service.getAllGames());
    }

    private static void analyze(List<Game> games) {
        int count = 0;
        for (Game game : games) {
            Street preFlop = game.getPreFlop();
            int playerIndex = preFlop.getPlayerIndex(game.getPlayerName());
            Player player = preFlop.getPlayer(playerIndex);
            State state = player.getState();
            if (state != State.FOLD && state != State.BIG_BLIND) {
                //if (state == State.BIG_BLIND) {
                //if (true) {
                count++;
                System.out.println(" bb " + game.getBigBlind() + " stack " + player.getStack() +
                        " " + preFlop.getCards() +
                        " move " + state + " " + player.getPreviousState() + " " + player.getBet() +
                        " ");
            }
        }
        System.out.println("number: " + count);
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
