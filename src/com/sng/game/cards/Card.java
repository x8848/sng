package com.sng.game.cards;

public class Card {
    private final Rank rank;
    private final Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public static Card parse(String s) {
        Rank rank = Rank.parse(s.substring(0, 1));
        Suit suit = Suit.parse(s.substring(1, 2));
        return new Card(rank, suit);
    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
}