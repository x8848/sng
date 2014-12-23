package com.sng.game.cards.hands;

import api.game.cards.hands.Hand;
import com.sng.game.cards.Card;
import com.sng.game.cards.Rank;
import com.sng.game.cards.Suit;

import java.util.*;

public class HandImpl implements Hand {
    List<Card> playerCards;
    HandType type = HandType.HighCard;


    Map<Rank, List<Card>> rankMap = new TreeMap<>();
    Map<Suit, List<Card>> suitMap = new TreeMap<>();

    public HandImpl(List<Card> playerCards) {
        this.playerCards = playerCards;

        if (playerCards.get(0).getRank() == playerCards.get(1).getRank()) {
            type = HandType.OnePair;
        }
        setCards(playerCards);
    }

    public void setCards(List<Card> cards) {
        for (Card card : cards) {
            setCards(card);
        }
    }

    public void setCards(Card card) {
        Rank rank = card.getRank();
        Suit suit = card.getSuit();

        List<Card> rankCards = rankMap.get(rank);
        List<Card> suitCards = suitMap.get(suit);

        if (rankCards != null) {
            rankCards.add(card);
        } else {
            rankCards = new ArrayList<>();
            rankCards.add(card);
        }
        rankMap.put(rank, rankCards);

        if (suitCards != null) {
            suitCards.add(card);
        } else {
            suitCards = new ArrayList<>();
            suitCards.add(card);
        }
        suitMap.put(suit, suitCards);
    }

    private HandType checkType() {
        if (checkFlash() && checkStraight()) return HandType.StraightFlush;

        Map<Integer, List<Rank>> sizeMap = getSizeMap();

        Set<Integer> sizes = sizeMap.keySet();

        if (sizes.contains(4)) {
            return HandType.Quads;
        }
        if (sizes.contains(3)) {
            if (sizeMap.get(3).size() == 2 || (sizes.contains(2))) {
                return HandType.FullHouse;
            }
            if (checkFlash()) return HandType.Flush;
            if (checkStraight()) return HandType.Straight;
            return HandType.Set;
        }
        if (sizes.contains(2)) {
            return (sizeMap.get(2).size() >= 2) ? HandType.TwoPairs : HandType.OnePair;
        }
        return HandType.HighCard;
    }

    private boolean checkStraight() {
        if (rankMap.keySet().size() < 5) return false;

        Iterator<Rank> iterator = rankMap.keySet().iterator();
        Rank current = null;
        Rank next;
        int counter = 0;

        while (iterator.hasNext()) {
            next = iterator.next();

            if (current == null) {
                current = next;
                continue;
            }

            if (current.ordinal() == next.ordinal() + 1) {
                counter++;
            }
            current = next;

        }
        return (counter == 5);
    }

    private boolean checkFlash() {
        for (List<Card> cards : suitMap.values()) {
            if (cards.size() >= 5) {
                return true;
            }
        }
        return false;
    }

    @Override
    public HandType getType() {
        type = checkType();
        return type;
    }

    private Map<Integer, List<Rank>> getSizeMap() {
        Map<Integer, List<Rank>> rankSizes = new TreeMap();
        for (Rank rank : rankMap.keySet()) {
            int size = rankMap.get(rank).size();
            List<Rank> ranks = rankSizes.get(size);
            if (ranks == null) {
                ranks = new ArrayList<>();
            }
            ranks.add(rank);
            rankSizes.put(size, ranks);
        }
        return rankSizes;
    }
}