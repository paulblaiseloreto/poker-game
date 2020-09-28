package com.synacy.poker.model.hand;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.card.CardSuit;
import com.synacy.poker.model.hand.types.TwoPair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TwoPairTest {

    @Test
    public void toString_withTwoPairsAndAceKicker() {
        List<Card> firstPair = Arrays.asList(
                new Card(CardRank.FOUR, CardSuit.CLUBS),
                new Card(CardRank.FOUR, CardSuit.DIAMONDS)
        );
        List<Card> secondPair = Arrays.asList(
                new Card(CardRank.THREE, CardSuit.CLUBS),
                new Card(CardRank.THREE, CardSuit.DIAMONDS)
        );
        List<Card> kicker = Arrays.asList(
                new Card(CardRank.ACE, CardSuit.CLUBS)
        );

        TwoPair twoPair = new TwoPair(firstPair, secondPair, kicker);

        assertEquals("Two Pair (4,3) - A High", twoPair.toString());
    }

}
