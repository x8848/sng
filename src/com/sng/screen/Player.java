package com.sng.screen;

import com.sng.screen.figures.State;

public class Player {

    private int seat;
    private boolean button;

    private State state;
    private State previousState;
    private int stack;
    private int bet;
    private String name;

    public Player(int seat, String name, int stack) {
        this.seat = seat;
        this.name = name;
        this.stack = stack;
    }


}