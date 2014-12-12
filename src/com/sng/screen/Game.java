package com.sng.screen;

import com.sng.screen.figures.Street;

import java.util.ArrayList;
import java.util.List;

public class Game {
    String cards;
    String name;
    int seat;
    int bigBlind;
    int button;
    int playersNumber;

//    Stage stage;

    Street preFlop;
    Street flop;
    Street turn;
    Street river;

    List<Player> playerList = new ArrayList<Player>();

    public Game(String name) {
        this.name = name;
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

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public String getName() {
        return name;
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

    @Override
    public String toString() {
        return "Game{" +
                "cards='" + cards + '\'' +
                ", name='" + name + '\'' +
                ", seat=" + seat +
                ", bigBlind=" + bigBlind +
                ", button=" + button +
                ", playersNumber=" + playersNumber +
                ", pFlop=" + preFlop +
                ", flop=" + flop +
                ", turn=" + turn +
                ", river=" + river +
                ", playerList=" + playerList +
                '}';
    }
}