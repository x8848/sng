package com.sng.screen.figures;

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

    @Override
    public String toString() {
        return "Player{" +
                "seat=" + seat +
                ", button=" + button +
                ", state=" + state +
                ", previousState=" + previousState +
                ", stack=" + stack +
                ", bet=" + bet +
                ", name='" + name + '\'' +
                '}';
    }
}