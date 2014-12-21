package com.sng.game.cards.hands;

import api.game.cards.hands.Hand;
import com.sng.game.cards.Card;

import java.util.List;

public class HandImpl implements Hand {
    List<Card> cards;
    HandType type;

    public HandImpl(List<Card> allCards) {

    }

    @Override
    public HandType getType() {

        if (cards.size() == 2) {
            if (cards.get(0).getRank() == cards.get(1).getRank()) {
                return HandType.OnePair;
            }
            return null;
        }

        for (Card card : cards) {
            
        }


        return null;
    }

    @Override
    public List<Card> getCards() {
        return null;
    }
}