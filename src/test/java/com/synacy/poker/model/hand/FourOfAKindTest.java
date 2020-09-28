package com.synacy.poker.model.hand;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.card.CardSuit;
import com.synacy.poker.model.hand.types.FourOfAKind;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FourOfAKindTest {

    @Test
    public void toString_withQuadFoursAndAceKicker() {
        List<Card> quads = Arrays.asList(
                new Card(CardRank.FOUR, CardSuit.CLUBS),
                new Card(CardRank.FOUR, CardSuit.DIAMONDS),
                new Card(CardRank.FOUR, CardSuit.SPADES),
                new Card(CardRank.FOUR, CardSuit.HEARTS)
        );
        List<Card> kicker = Collections.singletonList(
                new Card(CardRank.ACE, CardSuit.CLUBS)
        );

        FourOfAKind fourOfAKind = new FourOfAKind(quads, kicker);

        assertEquals("Quads (4) - A High", fourOfAKind.toString());
    }

}
