package api.game.cards.hands;

import com.sng.game.cards.Card;
import com.sng.game.cards.hands.HandType;

import java.util.List;

public interface Hand {

    HandType getType();

    List<Card> getCards();

}
