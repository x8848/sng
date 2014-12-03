package com.sng.screen;

import com.sng.screen.figures.Street;

import java.util.List;

public class Game {
    String cards;
    int seat;
    String name;
    int bBlind;
    Stage stage;

    Street pFlop;
    Street flop;
    Street turn;
    Street river;

    List<Player> playerList;
}