package com.sng.screen;

import com.sng.screen.figures.State;

public class Player {

    protected int seat;
    private State state;
    private boolean button;
    private String stack;

    public Player(int seat) {
        this.seat = seat;
    }
}