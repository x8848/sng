package com.sng.game.cards;

public enum Suit {
    Hearts("h"),
    Clubs("c"),
    Diamonds("d"),
    Spades("s");

    private final String value;

    Suit(String value) {
        this.value = value;
    }

    public static Suit parse(String s) {
        for (Suit suit : Suit.values()) {
            if (s.equalsIgnoreCase(suit.value)) return suit;
        }
        throw new RuntimeException("wrong suit" + s);
    }

    @Override
    public String toString() {
        return value;
    }
}