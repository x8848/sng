package com.sng.game.cards;

import java.util.HashSet;
import java.util.Set;

public class Hand {
    private final Set<Card> cards = new HashSet<Card>();

    public Hand(Card card1, Card card2) {
        cards.add(card1);
        cards.add(card2);
    }

    public Set<Card> getCards() {
        return cards;
    }
}
