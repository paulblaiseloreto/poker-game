package com.synacy.poker.model.game;

import com.synacy.poker.model.card.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * A player in the game.
 */
public class Player {

	private String name;
	private List<Card> hand = new ArrayList<>();

	public Player(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<Card> getHand() {
		return hand;
	}

	public void addToHand(Card card) {
		hand.add(card);
	}

	public void clearHand() {
		hand.clear();
	}

	public String toString() {
		return name;
	}

}
