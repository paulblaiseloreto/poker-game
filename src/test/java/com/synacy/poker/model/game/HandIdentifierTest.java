package com.synacy.poker.model.game;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.card.CardSuit;
import com.synacy.poker.model.hand.Hand;
import com.synacy.poker.model.hand.HandIdentifier;
import com.synacy.poker.model.hand.types.Flush;
import com.synacy.poker.model.hand.types.FourOfAKind;
import com.synacy.poker.model.hand.types.FullHouse;
import com.synacy.poker.model.hand.types.HighCard;
import com.synacy.poker.model.hand.types.OnePair;
import com.synacy.poker.model.hand.types.Straight;
import com.synacy.poker.model.hand.types.StraightFlush;
import com.synacy.poker.model.hand.types.ThreeOfAKind;
import com.synacy.poker.model.hand.types.TwoPair;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class HandIdentifierTest {

	private HandIdentifier handIdentifier = new HandIdentifier();

	@Test
	public void identifyHand_royalFlush() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.ACE, CardSuit.SPADES),
				new Card(CardRank.KING, CardSuit.SPADES)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.SPADES),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof StraightFlush);
		assertEquals("Royal Flush", identifiedHand.toString());
	}

	@Test
	public void identifyHand_straightFlush() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.SPADES),
				new Card(CardRank.JACK, CardSuit.SPADES)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof StraightFlush);
		assertEquals("Straight Flush (Q High)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_fourOfAKind() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.DIAMONDS),
				new Card(CardRank.EIGHT, CardSuit.HEARTS),
				new Card(CardRank.SEVEN, CardSuit.SPADES),
				new Card(CardRank.SIX, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof FourOfAKind);
		assertEquals("Quads (8) - 7 High", identifiedHand.toString());
	}

	@Test
	public void identifyHand_fullHouse() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.DIAMONDS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.SEVEN, CardSuit.HEARTS),
				new Card(CardRank.SEVEN, CardSuit.SPADES),
				new Card(CardRank.EIGHT, CardSuit.CLUBS),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof FullHouse);
		assertEquals("Full House (8,7)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_flush() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.SEVEN, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.SPADES)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.SPADES),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.ACE, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof Flush);
		assertEquals("Flush (Q High)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_straight() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.EIGHT, CardSuit.SPADES),
				new Card(CardRank.KING, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof Straight);
		assertEquals("Straight (K High)", identifiedHand.toString());
	}

	@Test
	public void identifyHand_threeOfAKind() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof ThreeOfAKind);
		assertEquals("Trips (2) - Q,J High", identifiedHand.toString());
	}

	@Test
	public void identifyHand_twoPair() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.SPADES),
				new Card(CardRank.NINE, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof TwoPair);
		assertEquals("Two Pair (9,2) - Q High", identifiedHand.toString());
	}

	@Test
	public void identifyHand_onePair() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.TWO, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.THREE, CardSuit.SPADES),
				new Card(CardRank.SIX, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof OnePair);
		assertEquals("One Pair (2) - Q,J,10 High", identifiedHand.toString());
	}

	@Test
	public void identifyHand_highCard() {
		List<Card> playerCards = Arrays.asList(
				new Card(CardRank.ACE, CardSuit.SPADES),
				new Card(CardRank.TWO, CardSuit.CLUBS)
		);

		List<Card> communityCards = Arrays.asList(
				new Card(CardRank.QUEEN, CardSuit.DIAMONDS),
				new Card(CardRank.JACK, CardSuit.SPADES),
				new Card(CardRank.TEN, CardSuit.SPADES),
				new Card(CardRank.THREE, CardSuit.SPADES),
				new Card(CardRank.SIX, CardSuit.DIAMONDS)
		);

		Hand identifiedHand = handIdentifier.identifyHand(playerCards, communityCards);

		assertTrue(identifiedHand instanceof HighCard);
		assertEquals("A,Q,J,10,6", identifiedHand.toString());
	}
}
