package com.sng;

import com.sng.game.cards.Card;
import com.sng.game.cards.hands.HandImpl;
import com.sng.screen.*;
import com.sng.screen.figures.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainTest {
    public static void main(String[] args) throws IOException {
        // testImage();
        testFileParser();
        //      testStraight();
    }

    private static void testStraight() {
        String[] cards = {"2c", "3h", "4h", "5h", "6h", "Ah"};
        HandImpl hand = new HandImpl();
        for (String card : cards) {
            hand.setCards(Card.parse(card));
        }
        System.out.println(cards);
        System.out.println(hand.getType());
        System.out.println(hand.getBestHand());
    }

    private static void testFileParser() throws IOException {
        GameService service = new GameService();
//        analyze(service.getAllGames());
//        testHand(service.getAllGames().get(4));
        int count = 0;
        for (Game game : service.getAllGames()) {
            testHand(game);
            count++;
        }
        System.out.println(count);


    }

    private static void testHand(Game game) {
        List<Card> handCards = game.getPreFlop().getCards();
        ArrayList<Card> table = new ArrayList<>();
        for (Street street : game.getStreetList()) {
            table.addAll(street.getCards());
        }
        HandImpl hand = new HandImpl(handCards);
        if (table.size() == 0) return;
        hand.setCards(table);
        Collections.sort(table);
        System.out.println(handCards + ", " + table);
        System.out.println(hand.getType());
        System.out.println(hand.getBestHand());
        System.out.println(hand.PlayerCardsTakePart());
        System.out.println("------------------------------");
    }

    private static void analyze(List<Game> games) {
        int count = 0;
        for (Game game : games) {
            Street preFlop = game.getPreFlop();
            int playerIndex = preFlop.getPlayerIndex(game.getPlayerName());
            Player player = preFlop.getPlayer(playerIndex);
            Move move = player.getMoves().getLast();
            State state = move.getState();

            if (preFlop.getCards().get(1).getRank().toString().equals("A")) {
                //  if (preFlop.getCards().get(0).getRank().toString().equalsIgnoreCase(preFlop.getCards().get(1).getRank().toString())) {
                if (state != State.FOLD
                        && state != State.BIG_BLIND
                        && state != State.CHECK
                    // && (player.getMoves().getLast().getBet() > game.getBigBlind())
                        ) {
                    //if (state == State.BIG_BLIND) {
                    //if (true) {
                    count++;
                    System.out.println(" bb " + game.getBigBlind() + " stack " + player.getStack() +
                            " " + preFlop.getCards() +
                            " " + state + " " + move.getBet() + " " + move.getStack() +
                            " ");
                }
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
