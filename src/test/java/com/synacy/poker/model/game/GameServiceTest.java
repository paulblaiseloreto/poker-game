package com.synacy.poker.model.game;

import com.synacy.poker.model.deck.DeckBuilder;
import com.synacy.poker.model.hand.HandIdentifier;
import com.synacy.poker.model.hand.WinningHandCalculator;
import com.synacy.poker.services.GameService;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class GameServiceTest {


    @Test
    public void afterConstructorInit_eachPlayerHasTwoCards() {
        DeckBuilder deckBuilder = new DeckBuilder();
        HandIdentifier handIdentifier = mock(HandIdentifier.class);
        WinningHandCalculator winningHandCalculator = mock(WinningHandCalculator.class);

        GameService gameService = new GameService(deckBuilder, handIdentifier, winningHandCalculator);

        assertPlayersHaveTwoCardsEach(gameService);
    }

    @Test
    public void startNewGame_eachPlayerHasTwoCards() {
        DeckBuilder deckBuilder = new DeckBuilder();
        HandIdentifier handIdentifier = mock(HandIdentifier.class);
        WinningHandCalculator winningHandCalculator = mock(WinningHandCalculator.class);

        GameService gameService = new GameService(deckBuilder, handIdentifier, winningHandCalculator);

        assertPlayersHaveTwoCardsEach(gameService);
    }

    private void assertPlayersHaveTwoCardsEach(GameService gameService) {
        gameService.getPlayers().forEach(player ->
                Assert.assertEquals("Players should have 2 cards each",
                        2,
                        player.getHand().size()));
    }

    @Test
    public void nextAction_dealCommunityCards() {
        DeckBuilder deckBuilder = new DeckBuilder();
        HandIdentifier handIdentifier = mock(HandIdentifier.class);
        WinningHandCalculator winningHandCalculator = mock(WinningHandCalculator.class);

        GameService gameService = new GameService(deckBuilder, handIdentifier, winningHandCalculator);
        gameService.nextAction();
        assertEquals("Deal three community cards at the start", 3, gameService.getCommunityCards().size());

        gameService.nextAction();
        assertEquals("Expecting four community cards", 4, gameService.getCommunityCards().size());

        gameService.nextAction();
        assertEquals("Expecting 5 community cards", 5, gameService.getCommunityCards().size());
    }
}
