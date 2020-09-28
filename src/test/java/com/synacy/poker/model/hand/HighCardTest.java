package com.synacy.poker.model.hand;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.card.CardSuit;
import com.synacy.poker.model.hand.types.HighCard;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HighCardTest {

    @Test
    public void toString_withHighCards() {
        List<Card> cards = Arrays.asList(
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.KING, CardSuit.DIAMONDS),
                new Card(CardRank.QUEEN, CardSuit.SPADES),
                new Card(CardRank.TWO, CardSuit.CLUBS),
                new Card(CardRank.THREE, CardSuit.HEARTS)
        );

        HighCard highCard = new HighCard(cards);

        assertEquals("A,K,Q,3,2", highCard.toString());
    }

}
