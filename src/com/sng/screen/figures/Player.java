package com.sng.screen.figures;

public class Player implements Cloneable {

    private int seat;
    private String name;
    private int stack;
    private int bet;
    private State state;

    private State previousState = null;

    public Player(int seat, String name, int stack) {
        this.seat = seat;
        this.name = name;
        this.stack = stack;
    }

    public void setBet(int bet) {
        this.bet = bet;
        stack = stack - bet;
        if (stack == 0) state = State.ALL_IN;
    }

     public State getState() {
        return state;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "seat=" + seat +

                ", state=" + state +
                ", previousState=" + previousState +
                ", stack=" + stack +
                ", bet=" + bet +
                ", name='" + name + '\'' +
                '}';
    }

    public void parseState(String string) {
        if (state != null) {
            previousState = state;
        }
        switch (string) {
            case "small":
                state = State.SMALL_BLIND;
                break;
            case "big":
                state = State.BIG_BLIND;
                break;
            case "folds":
                state = State.FOLD;
                break;
            case "checks":
                state = State.CHECK;
                break;
            case "bets":
                state = State.BET;
                break;
            case "calls":
                state = State.CALL;
                break;
            case "raises":
                state = State.RAISE;
                break;
            default:
                throw new FileParseException();
        }
    }

    @Override
    protected Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new FileParseException();
        }
    }
}