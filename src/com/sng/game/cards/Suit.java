package com.sng.game.cards;

public enum Suit {
    Hearts("h"),
    Clubs("c"),
    Diamonds("d"),
    Spades("s");

    private final String suit;

    private Suit(String suit) {
        this.suit = suit;
    }
}