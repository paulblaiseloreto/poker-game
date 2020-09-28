package com.synacy.poker.model.deck;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.card.CardSuit;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A service class used to build a {@link Deck}
 */
@Component
public class DeckBuilder {

	/**
	 * Builds a complete {@link Deck} without Jokers. Does not shuffle the deck.
	 *
	 * @return a {@link Deck} of {@link Card}
	 */
	public Deck buildDeck() {
		Deck deck = new Deck();
		deck.addCards(generateCards());

		return deck;
	}

	private List<Card> generateCards() {
		CardSuit[] cardSuits = CardSuit.values();

		return Arrays.stream(cardSuits)
				.map(this::cardsForSuit)
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}

	private List<Card> cardsForSuit(CardSuit suit) {
		CardRank[] cardRanks = CardRank.values();

		return Arrays.stream(cardRanks)
				.map(rank -> new Card(rank, suit))
				.collect(Collectors.toList());
	}

}
