package com.synacy.poker.model.hand.types;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.hand.Hand;
import com.synacy.poker.model.hand.HandType;
import com.synacy.poker.utils.EnumUtil;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Full_house">What is a Full House?</a>
 */
public class FullHouse extends Hand {

    private List<Card> threeOfAKindCards;
    private List<Card> pairCards;

    public FullHouse(List<Card> threeOfAKindCards, List<Card> pairCards) {
        this.threeOfAKindCards = threeOfAKindCards;
        this.pairCards = pairCards;
    }

    public HandType getHandType() {
        return HandType.FULL_HOUSE;
    }

    /**
     * @return The name of the hand with rank of the three pair and two pair, e.g.
     * 444AA - Full House (4,A)
     */
    @Override
    public String toString() {
        return EnumUtil.getHandTypeValue(HandType.FULL_HOUSE)
                + " ("+ threeOfAKindCards.get(0).getRank()
                + ","
                + pairCards.get(0).getRank()
                + ")";
    }

}
