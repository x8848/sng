package com.sng.screen.figures;

import com.sng.GameService;
import com.sng.game.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Street {
    int bank;
    int pot;
    List<Card> cards;

    List<Player> playerList = new ArrayList<>();

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }

    public void setCard(Card card) {
        if (cards == null) cards = new ArrayList<>();
        cards.add(card);
    }

    public void parseMove(String token) {
        int moveIndex = 1;
        int betIndex = 2;

        String[] split = token.split(" ");

        int index = getPlayerIndex(split[0]);
        Player player = getPlayer(index);

        if ("posts".equals(split[1])) {
            moveIndex = 2;
            betIndex = 4;
        }
        player.parseState(split[moveIndex]);

        State state = player.getState();

        if (state == State.ANTE) {
            betIndex = 3;
        }

        if (state != State.CHECK && state != State.FOLD) {
            int bet = GameService.getInt(split[betIndex]);
            player.setBet(bet);
            bank = bank + bet;
            pot = pot + bet;
        }

        setPlayer(index, player);
    }

    public Player getPlayer(int index) {
        return playerList.get(index);
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

    public int getPlayerIndex(String name) {
        for (int i = 0; i < playerList.size(); i++) {
            if (name.equals(playerList.get(i).getName())) return i;
        }
        return 0;
    }

    public List<Player> getNextStreetPlayerList() {
        List<Player> nextStreetPlayerList = new ArrayList<>();

        for (Player player : playerList) {
            if (player.getState() != State.FOLD) {
                Player clone = player.clone();
                clone.setStack(player.getStack() - player.getBet());
                clone.setBet(0);
                nextStreetPlayerList.add(clone);
            }
        }
        return nextStreetPlayerList;
    }

    public void setPlayer(int smallBlindPlayerIndex, Player player) {
        playerList.set(smallBlindPlayerIndex, player);
    }

    @Override
    public String toString() {
        return "Street{" +
                "cards='" + cards + '\'' +
                ", bank=" + bank +
                ", pot=" + pot +
                ", playerList=" + playerList +
                '}';
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }

    public List<Card> getCards() {
        return cards;
    }
}