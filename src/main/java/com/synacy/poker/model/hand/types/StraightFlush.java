package com.synacy.poker.model.hand.types;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.hand.HandType;
import com.synacy.poker.utils.EnumUtil;
import com.synacy.poker.utils.RankingUtil;

import java.util.List;

/**
 * @see <a href="https://en.wikipedia.org/wiki/List_of_poker_hands#Straight_flush">What is a Straight Flush?</a>
 */
public class StraightFlush extends Straight {

    public StraightFlush(List<Card> cards) {
        super(cards);
    }

    @Override
    public HandType getHandType() {
        return HandType.STRAIGHT_FLUSH;
    }

    /**
     * @return Royal Flush if the hand is a royal flush, or Straight Flush with the highest rank card,
     * e.g. Straight Flush (K High)
     */
    @Override
    public String toString() {
        if(!RankingUtil.getRoyalFlush(getCards()).isEmpty()){
            return EnumUtil.getHandTypeValue(HandType.ROYAL_FLUSH);
        }else{
            return EnumUtil.getHandTypeValue(HandType.STRAIGHT_FLUSH)
                    + " ("
                    + RankingUtil.getHighStraightFlushCard(getCards())
                    + " High)"
                    ;
        }
    }

}
