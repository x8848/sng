package com.sng.Table;

public class Player {

    int commaWidth = 5;
    int numberWidth = 10;
    int numberHeight = 12;

    private int seat;
    private State state;

    private int cardsX;
    private int cardsY;

    private int steakX;        // x-13; y-69
    private int steakY;


    public Player(int seat) {
        this.seat = seat;


    }
}