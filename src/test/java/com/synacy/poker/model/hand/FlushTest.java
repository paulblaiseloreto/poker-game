package com.synacy.poker.model.hand;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.card.CardSuit;
import com.synacy.poker.model.hand.types.Flush;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FlushTest {

    @Test
    public void toString_withAceHighFlush() {
        List<Card> cards = Arrays.asList(
                new Card(CardRank.ACE, CardSuit.CLUBS),
                new Card(CardRank.KING, CardSuit.CLUBS),
                new Card(CardRank.QUEEN, CardSuit.CLUBS),
                new Card(CardRank.SEVEN, CardSuit.CLUBS),
                new Card(CardRank.TWO, CardSuit.CLUBS)
        );

        Flush flush = new Flush(cards);

        assertEquals("Flush (A High)", flush.toString());
    }

    @Test
    public void toString_withKingHighFlush() {
        List<Card> cards = Arrays.asList(
                new Card(CardRank.KING, CardSuit.CLUBS),
                new Card(CardRank.SEVEN, CardSuit.CLUBS),
                new Card(CardRank.SIX, CardSuit.CLUBS),
                new Card(CardRank.TEN, CardSuit.CLUBS),
                new Card(CardRank.NINE, CardSuit.CLUBS)
        );

        Flush flush = new Flush(cards);

        assertEquals("Flush (K High)", flush.toString());
    }

}
