package com.synacy.poker.model.hand;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.hand.types.*;
import com.synacy.poker.utils.RankingUtil;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * A service that is to used to identify the {@link Hand} given the player's cards and the community
 * cards.
 */
@Component
public class HandIdentifier {

    /**
     * Given the player's cards and the community cards, identifies the player's hand.
     *
     * @param playerCards
     * @param communityCards
     * @return The player's {@link Hand} or `null` if no Hand was identified.
     */
    public Hand identifyHand(List<Card> playerCards, List<Card> communityCards) {
        List<Card> cards = RankingUtil.getMergedCardList(playerCards, communityCards);
        List<Card> rankings  = RankingUtil.getRoyalFlushCards(cards);
        if(!rankings.isEmpty()){
            return new StraightFlush(rankings);
        }

        rankings = RankingUtil.getStraightFlush(cards);
        if(!rankings.isEmpty()){
            return new StraightFlush(cards);
        }

        rankings = RankingUtil.getFourOfAKind(cards);
        if(!rankings.isEmpty()){
            return new FourOfAKind(rankings, RankingUtil.getOthers(rankings, cards));
        }

        rankings = RankingUtil.getFullHouse(playerCards, communityCards);
        if(!rankings.isEmpty()) {
            return new FullHouse(RankingUtil.checkPair(rankings, 3), RankingUtil.checkPair(rankings, 2));
        }

        rankings = RankingUtil.getFlush(playerCards, communityCards);
        if(!rankings.isEmpty()) {
            return new Flush(rankings);
        }

        rankings = RankingUtil.getStraight(cards);
        if(!rankings.isEmpty()) {
            return new Straight(cards);
        }

        rankings = RankingUtil.getThreeOfAKind(playerCards, communityCards);
        if(!rankings.isEmpty()){
            return new ThreeOfAKind(rankings, RankingUtil.getNumberOfHighCards(cards, rankings, 2));
        }

        rankings = RankingUtil.getTwoPair(playerCards, communityCards);
        if (!rankings.isEmpty()) {
            List<Card> firstPair = RankingUtil.checkPair(rankings, 2);
            List<Card> withoutFirstPair =
                    rankings.stream().filter(card -> !card.getRank().equals(firstPair.get(0).getRank())).collect(Collectors.toList());
            List<Card> secondPair = RankingUtil.checkPair(withoutFirstPair, 2);
            return new TwoPair(firstPair, secondPair,
                    RankingUtil.getNumberOfHighCards(cards, rankings
                            , 1));
        }

        rankings = RankingUtil.getOnePair(playerCards, communityCards);
        if(!rankings.isEmpty()){
            return new OnePair(rankings, RankingUtil.getNumberOfHighCards(cards, rankings, 3));
        }


        rankings = RankingUtil.getSequenceHighCards(cards, 5);
        if(!rankings.isEmpty()){
            return new HighCard(rankings);
        }
        return null;
    }

}
