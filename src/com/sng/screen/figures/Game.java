package com.sng.screen.figures;

import java.util.ArrayList;
import java.util.List;

public class Game {
    int gameNumber;
    String cards;
    private String playerName;
    int playerSeat;
    int bigBlind;
    int button;
    int playersNumber;

    private List<Player> playerList = new ArrayList<Player>();

//    Stage stage;

    Street preFlop;
    Street flop;
    Street turn;
    Street river;

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerSeat() {
        return playerSeat;
    }

    public Game(String playerName) {
        this.playerName = playerName;
    }

    public void setGameNumber(int gameNumber) {
        this.gameNumber = gameNumber;
    }

    public void setPlayerSeat(int playerSeat) {
        this.playerSeat = playerSeat;
    }

    public void setPreFlop(Street preFlop) {
        this.preFlop = preFlop;
    }

    public void setFlop(Street flop) {
        this.flop = flop;
    }

    public void setTurn(Street turn) {
        this.turn = turn;
    }

    public void setRiver(Street river) {
        this.river = river;
    }

    public void setPlayersNumber(int playersNumber) {
        this.playersNumber = playersNumber;
    }

    public Street getFlop() {
        return flop;
    }

    public Street getTurn() {
        return turn;
    }

    public Street getRiver() {
        return river;
    }

    public int getPlayersNumber() {
        return playersNumber;
    }

    public void setBigBlind(int bigBlind) {
        this.bigBlind = bigBlind;
    }

    public void setButton(int button) {
        this.button = button;
    }

    public void setCards(String cards) {
        this.cards = cards;
    }

    public void addPlayer(Player player) {
        playerList.add(player);
    }

}