package com.synacy.poker.services;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.deck.Deck;
import com.synacy.poker.model.deck.DeckBuilder;
import com.synacy.poker.model.game.Player;
import com.synacy.poker.model.hand.Hand;
import com.synacy.poker.model.hand.HandIdentifier;
import com.synacy.poker.model.hand.WinningHandCalculator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The game engine.
 */
@Service
public class GameService {

    private List<Player> players = new ArrayList<>();

    private List<Card> communityCards = new ArrayList<>();

    private DeckBuilder deckBuilder;
    private HandIdentifier handIdentifier;
    private WinningHandCalculator winningHandCalculator;

    private Deck deck;

    private List<Hand> winningHand = new ArrayList<>();

    private static final int MAX_PLAYER_CARDS = 2;
    private static final int MAX_COMMUNITY_CARDS = 5;

    public GameService(DeckBuilder deckBuilder,
                       HandIdentifier handIdentifier,
                       WinningHandCalculator winningHandCalculator) {
        players.add(new Player("Alex"));
        players.add(new Player("Bob"));
        players.add(new Player("Jane"));

        this.deckBuilder = deckBuilder;
        this.handIdentifier = handIdentifier;
        this.winningHandCalculator = winningHandCalculator;

        startNewGame();
    }

    /**
     * Starts a new game.
     *
     * <h3>The following describes a new game.</h3>
     * <ul>
     * <li>Players' previous hands are cleared</li>
     * <li>Community cards are cleared</li>
     * <li>A new deck is used</li>
     * <li>The deck is shuffled</li>
     * <li>Players' are dealt with new cards.</li>
     * </ul>
     */
    public void startNewGame() {
        players.forEach(Player::clearHand);
        communityCards.clear();

        deck = deckBuilder.buildDeck();
        deck.shuffle();

        dealHands();
    }

    /**
     * The action to take after a new game has been started.
     *
     * <ol>
     * <li>Deal three community cards</li>
     * <li>Deal one community card</li>
     * <li>Deal another community card</li>
     * <li>Determine the winner/s</li>
     * </ol>
     * <p>
     * Dealt community are of course removed from the deck at the time their placed on the table.
     */
    public void nextAction() {
        if (communityCards.isEmpty()) {
            burnCard();
            dealThreeCommunityCards();
        } else if (communityCards.size() < MAX_COMMUNITY_CARDS) {
            burnCard();
            dealOneCommunityCard();
        }

        if (hasEnded()) {
            identifyWinningHand();
        }
    }

    /**
     * Checks the combination of the players and community cards to identify the winning hand.
     *
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    public void identifyWinningHand() {

        winningHand = winningHandCalculator.calculateWinningHand(players, communityCards);
    }

    /**
     * Checks if the player won
     *
     * @param player
     * @return true if the player's hand is equal to the winning hand.
     */
    public boolean checkIfPlayerWon(Player player) {

        Hand playerHand = identifyPlayerHand(player);
        return !winningHand.isEmpty() && winningHand.stream().anyMatch(hand -> hand.toString().equalsIgnoreCase(playerHand.toString()));
    }

    /**
     * Identifies the player's hand. A hand is combination of the two cards in the player's
     * possession and the community cards on the table.
     *
     * @param player
     * @return The {@link} of a player, e.g. High Card, One Pair, Straight, etc.
     * @see <a href="https://www.youtube.com/watch?v=GAoR9ji8D6A">Poker rules</a>
     */
    public Hand identifyPlayerHand(Player player) {
        List<Card> playerCards = player.getHand();
        return handIdentifier.identifyHand(playerCards, communityCards);
    }

    /**
     * @return The list of {@link Player}s
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return The list of community cards {@link Card}
     */
    public List<Card> getCommunityCards() {
        return communityCards;
    }

    /**
     * @return true if the number of community cards is equal to the maximum community cards allowed.
     */
    public boolean hasEnded() {
        return communityCards.size() >= MAX_COMMUNITY_CARDS;
    }

    private void dealHands() {
        for (int i = 0; i < MAX_PLAYER_CARDS; i++) {
            dealOneCardToEachPlayer();
        }
    }

    private void dealOneCardToEachPlayer() {
        players.forEach(player -> player.addToHand(deck.removeFromTop()));
    }

    private void dealThreeCommunityCards() {
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
        communityCards.add(deck.removeFromTop());
    }

    private void dealOneCommunityCard() {
        communityCards.add(deck.removeFromTop());
    }

    private void burnCard() {
        deck.removeFromTop();
    }




}
