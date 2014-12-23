package com.sng.game.cards.hands;

import api.game.cards.hands.Hand;
import com.sng.game.cards.Card;
import com.sng.game.cards.Rank;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class HandImpl implements Hand {
    List<Card> cards = new ArrayList<>();
    List<Card> hand;
    List<Card> table;
    HandType type = HandType.HighCard;

    public HandImpl(List<Card> hand, List<Card> table) {
        this.hand = hand;
        this.table = table;
        cards.addAll(hand);
        cards.addAll(table);
    }

    public void checkType() {
        System.out.println(hand);
        System.out.println(table);

        if (cards.size() == 2) {
            if (cards.get(0).getRank() == cards.get(1).getRank()) {
                type = HandType.OnePair;
            }
            return;
        }

        Map<Rank, List<Card>> rankMap = new TreeMap<>();

        for (Card card : cards) {
            Rank rank = card.getRank();
            List<Card> rankCards = rankMap.get(rank);

            if (rankCards != null) {
                if (rankCards.size() == 5) {
                    rankCards = getBestHand(rankCards, card);
                } else
                    rankCards.add(card);
            } else {
                rankCards = new ArrayList<>();
                rankCards.add(card);
            }
            rankMap.put(rank, rankCards);
        }

        if (checkFlash()) {
            type = checkStraight() ? HandType.StraightFlush : HandType.Flush;
            return;
        }

        switch (rankMap.size()) {
            case 1:
                throw new RuntimeException();
            case 2:
                for (Rank rank : rankMap.keySet()) {
                    int size = rankMap.get(rank).size();
                    if ((size == 1) || (size == 4)) {
                        type = HandType.Quads;
                        return;
                    }
                    if ((size == 2) || (size == 3)) {
                        type = HandType.FullHouse;
                        return;
                    }
                }
            case 3:
                List<Integer> sizes = new ArrayList<>(3);
                for (Rank rank : rankMap.keySet()) {
                    sizes.add(rankMap.get(rank).size());
                }
                type = sizes.contains(3) ? HandType.Set : HandType.TwoPairs;
                break;
            case 4:
                type = HandType.TwoPairs;
                break;
            case 5:
                type = checkStraight() ? HandType.Straight : HandType.HighCard;
                break;
        }
    }

    private boolean checkStraight() {
        return false;
    }

    private boolean checkFlash() {
        return false;
    }


    private List<Card> getBestHand(List<Card> rankCards, Card card) {
        return rankCards;
    }

    @Override
    public HandType getType() {
        return type;
    }

    @Override
    public List<Card> getCards() {
        return cards;
    }
}