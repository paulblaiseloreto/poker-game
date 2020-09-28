package com.synacy.poker.model.game;

import com.synacy.poker.model.deck.Deck;
import com.synacy.poker.model.deck.DeckBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DeckBuilderTest {

	@Test
	public void buildDeck() {
		DeckBuilder deckBuilder = new DeckBuilder();

		Deck deck = deckBuilder.buildDeck();

		assertEquals(52, deck.size());
	}

}
