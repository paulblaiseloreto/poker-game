package com.synacy.poker.controller;


import com.synacy.poker.model.card.BlankCard;
import com.synacy.poker.model.card.Card;
import com.synacy.poker.services.GameService;
import com.synacy.poker.model.game.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Iterator;
import java.util.List;

@Controller
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("game", gameService);

		List<Player> players = gameService.getPlayers();
		for (int i = 0; i < players.size(); i++) {
			int playerNumber = i + 1;
			model.addAttribute("player" + playerNumber, players.get(i));
		}

		Iterator<Card> communityCardIterator = gameService.getCommunityCards().iterator();
		for (int communityCardNumber = 1; communityCardNumber <= 5; communityCardNumber++) {
			model.addAttribute("communityCard" + communityCardNumber, fetchNextCommunityCard(communityCardIterator));
		}

		return "index";
	}

	private Card fetchNextCommunityCard(Iterator<Card> communityCardIterator) {
		if (communityCardIterator.hasNext()) {
			return communityCardIterator.next();
		} else {
			return new BlankCard();
		}
	}

	@GetMapping("/nextAction")
	public String nextAction() {
		if (gameService.hasEnded()) {
			gameService.startNewGame();
		} else {
			gameService.nextAction();
		}

		return "redirect:/";
	}

}
