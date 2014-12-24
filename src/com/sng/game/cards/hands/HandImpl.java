package com.sng.game.cards.hands;

import api.game.cards.hands.Hand;
import com.sng.game.cards.Card;
import com.sng.game.cards.Rank;
import com.sng.game.cards.Suit;

import java.util.*;

public class HandImpl implements Hand {
    List<Card> playerCards;

    List<Card> bestHand = new ArrayList<>();
    HandType type = HandType.HighCard;


    NavigableMap<Rank, List<Card>> rankMap = new TreeMap<>();
    Map<Suit, List<Card>> suitMap = new TreeMap<>();

    public HandImpl(List<Card> playerCards) {
        this.playerCards = playerCards;
        bestHand = playerCards;

        if (playerCards.get(0).getRank() == playerCards.get(1).getRank()) {
            type = HandType.OnePair;
        }
        setCards(playerCards);
    }

    public HandImpl() {

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
        boolean checkFlash = checkFlash();
        List<Card> flash = bestHand;

        if (checkFlash) {
            if ((checkStraight()) && flash.equals(bestHand)) {
                return HandType.StraightFlush;
            }
        }

        Map<Integer, LinkedList<Rank>> sizeMap = getSizeMap();

        Set<Integer> sizes = sizeMap.keySet();

        if (sizes.contains(4)) {
            bestHand = new ArrayList<>();
            bestHand.addAll(rankMap.get(sizeMap.get(4).getLast()));
            bestHand.add(rankMap.lastEntry().getValue().get(0));
            return HandType.Quads;
        }
        if (sizes.contains(3)) {
            LinkedList<Rank> ranks = sizeMap.get(3);
            if (ranks.size() == 2 || (sizes.contains(2))) {
                bestHand = new ArrayList<>();
                bestHand.addAll(rankMap.get(ranks.getLast()));
                ranks.removeLast();
                if (ranks.size() != 0)
                    bestHand.addAll(rankMap.get(ranks.getLast()).subList(0, 2)); // take first 2 cards from the lower set
                else
                    bestHand.addAll(rankMap.get(sizeMap.get(2).getLast()));
                return HandType.FullHouse;
            }
        }
        if (checkFlash) {
            bestHand = flash;
            return HandType.Flush;
        }

        if (checkStraight()) return HandType.Straight;

        if (sizes.contains(3)) {
            bestHand = new ArrayList<>();
            bestHand.addAll(rankMap.get(sizeMap.get(3).getLast()));

            LinkedList<Rank> ranks = sizeMap.get(1);

            while (bestHand.size() != 5) {
                bestHand.addAll(rankMap.get(ranks.getLast()));
                ranks.removeLast();
            }
            return HandType.Set;
        }
        if (sizes.contains(2)) {
            bestHand = new ArrayList<>();

            LinkedList<Rank> ranks = sizeMap.get(2);
            while (ranks.size() != 0 && bestHand.size() != 4) {
                bestHand.addAll(rankMap.get(ranks.getLast()));
                ranks.removeLast();
            }
            if (bestHand.size() == 2) {
                ranks = sizeMap.get(1);
                while (bestHand.size() != 5) {
                    bestHand.addAll(rankMap.get(ranks.getLast()));
                    ranks.removeLast();
                }
                return HandType.OnePair;
            } else {
                Rank last = null;
                if (sizeMap.containsKey(1)) {
                    last = sizeMap.get(1).getLast();
                }
                if (ranks.size() != 0) {
                    Rank first = ranks.getFirst();
                    if (last != null) {
                        if (first.compareTo(last) > 0) last = first;
                    } else
                        last = first;
                }
                bestHand.add(rankMap.get(last).get(0));
                return HandType.TwoPairs;
            }
        }

        ArrayList<Card> allCards = new ArrayList<>();
        for (List<Card> cards : rankMap.values())
            allCards.addAll(cards);
        int size = allCards.size();
        bestHand = allCards.subList(size - 5, size);
        return HandType.HighCard;
    }

    private boolean checkStraight() {
        if (rankMap.keySet().size() < 5) return false;

        Iterator<Rank> iterator = rankMap.keySet().iterator();
        Rank previous = null;
        List<Card> straightCards = new ArrayList<>();

        while (iterator.hasNext()) {
            Rank current = iterator.next();

            if (previous == null) {
                previous = current;
                continue;
            }
            if (previous.ordinal() + 1 == current.ordinal()) {
                if (straightCards.size() == 0) {
                    straightCards.add(rankMap.get(previous).get(0));
                }
                straightCards.add(rankMap.get(current).get(0));
            } else {
                if (straightCards.size() >= 4) break;
                straightCards = new ArrayList<>();
            }
            previous = current;
        }

        int size = straightCards.size();

        if ((size == 4) && (straightCards.get(0).getRank() == Rank.Two) && (rankMap.keySet().contains(Rank.Ace))) {
            bestHand = new ArrayList<>();
            bestHand.addAll(straightCards);
            bestHand.add(rankMap.get(Rank.Ace).get(0));
            return true;
        }

        if (size == 5) {
            checkStraightFlush();
            bestHand = straightCards.subList(size - 5, size);
            return true;
        }
        return false;
    }

    private void checkStraightFlush() {

    }

    private boolean checkFlash() {
        for (List<Card> cards : suitMap.values()) {
            int size = cards.size();
            if (size >= 5) {
                Collections.sort(cards);
                bestHand = new ArrayList<>();
                bestHand.addAll(cards.subList(size - 5, size));
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

    private Map<Integer, LinkedList<Rank>> getSizeMap() {
        Map<Integer, LinkedList<Rank>> rankSizes = new TreeMap<>();
        for (Rank rank : rankMap.keySet()) {
            int size = rankMap.get(rank).size();
            LinkedList<Rank> ranks = rankSizes.get(size);
            if (ranks == null) {
                ranks = new LinkedList<>();
            }
            ranks.add(rank);
            Collections.sort(ranks);
            rankSizes.put(size, ranks);
        }
        return rankSizes;
    }

    public List<Card> getBestHand() {
        Collections.sort(bestHand);
        return bestHand;
    }

    public boolean PlayerCardsTakePart() {
        for (Card playerCard : playerCards) {
            if (bestHand.contains(playerCard)) return true;
        }
        return false;
    }
}