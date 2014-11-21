package com.sng.game.cards;

public enum Rank {
    Two("2"),
    Three("3"),
    Four("4"),
    Five("5"),
    Six("6"),
    Seven("7"),
    Eight("8"),
    Nine("9"),
    Ten("T"),
    Jack("J"),
    Queen("Q"),
    King("K"),
    Ace("A");

    private final String rank;

    Rank(String rank) {
        this.rank = rank;
    }
}