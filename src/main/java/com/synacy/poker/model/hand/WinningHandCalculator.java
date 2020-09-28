package com.synacy.poker.model.hand;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.game.Player;
import com.synacy.poker.utils.RankingUtil;
import com.synacy.poker.model.hand.types.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

/**
 * A service class used to calculate the winning hand.
 */
@Component
public class WinningHandCalculator {


    @Autowired
    private HandIdentifier handIdentifier;


    /**
     * identifying the hand of the player based on their cards and the community cards on the table
     * @param player
     * @param communityCards
     * @return
     */
    private Hand identifyPlayerHand(Player player, List<Card> communityCards){
        return handIdentifier.identifyHand(player.getHand(), communityCards);
    }

    /**
     * calculates the winner player from their cards and the community cards on the table.
     * @param communityCards and players
     * @return The winning {@link Hand} from a list of player hands.
     */
    public List<Hand> calculateWinningHand(List<Player> players, List<Card> communityCards) {
        List<Hand> winningHands = new ArrayList<>();
        Player winnerPlayer = players.get(0);

        for (Player player : players) {
            Hand playerHand = handIdentifier.identifyHand(player.getHand(), communityCards);
            Integer playerHandType = RankingUtil.getHandTypeToInt(playerHand);

            Hand winnerHand = handIdentifier.identifyHand(winnerPlayer.getHand(), communityCards);
            Integer winnerType = RankingUtil.getHandTypeToInt(winnerHand);

            if (winnerType.equals(playerHandType)) {
                switch (winnerHand.getHandType()) {
                    case ROYAL_FLUSH:
                        winningHands.clear();
                        winningHands.addAll(getRoyalWinner(winnerHand, playerHand));
                        break;
                    case STRAIGHT_FLUSH:
                        List<Player> wins = getStraightFlushWinner(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(wins.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = wins.get(0);
                        break;
                    case FOUR_OF_A_KIND:
                        List<Player> fourOfAKinds = getFourOfAKind(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(fourOfAKinds.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = fourOfAKinds.get(0);
                        break;
                    case FULL_HOUSE:
                        List<Player> fullHouse = getFullHouse(winnerPlayer, player, communityCards);
                        winningHands.addAll(fullHouse.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = fullHouse.get(0);
                        break;
                    case FLUSH:
                        List<Player> flush = getFlush(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(flush.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = flush.get(0);
                        break;
                    case STRAIGHT:
                        List<Player> straight = getStraightFlushWinner(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(straight.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = straight.get(0);
                        break;
                    case THREE_OF_A_KIND:
                        List<Player> threeOfAKind = getThreeOfAKindWinner(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(threeOfAKind.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = threeOfAKind.get(0);
                        break;
                    case TWO_PAIR:
                        List<Player> twoPairWinner = get2PairWinner(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(twoPairWinner.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = twoPairWinner.get(0);
                        break;
                    case ONE_PAIR:
                        List<Player> onePairWinner = getOnePairWinner(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(onePairWinner.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = onePairWinner.get(0);
                        break;
                    case HIGH_CARD:
                        List<Player> highCardWinner = getHighCardWinner(winnerPlayer, player, communityCards);
                        winningHands.clear();
                        winningHands.addAll(highCardWinner.stream().map(player1 -> identifyPlayerHand(player1, communityCards)).collect(Collectors.toList()));
                        winnerPlayer = highCardWinner.get(0);
                        break;
                    default:
                        //ignored default value
                }
            } else if (winnerType < playerHandType) {
                winnerPlayer = player;
                winningHands.clear();
                winningHands.add(playerHand);
            }
        }
        return winningHands;
    }

    /**
     * get the player that has the higher card
     * @param player1
     * @param player2
     * @param communityCards
     * @return
     */
    private List<Player> getHighCardWinner(Player player1, Player player2, List<Card> communityCards) {
        List<Card> player1Cards = RankingUtil.getMergedCardList(player1.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getMergedCardList(player2.getHand(), communityCards);

        CardRank cardRank1 = RankingUtil.getHighCard(player1Cards);
        CardRank cardRank2 = RankingUtil.getHighCard(player2Cards);
        if (cardRank1.ordinal() > cardRank2.ordinal()) {
            return Collections.singletonList(player1);
        } else if (cardRank1.ordinal() < cardRank2.ordinal()) {
            return Collections.singletonList(player2);
        } else {
            CardRank secondHighCardRank1 = RankingUtil.getSecondHighCard(player1Cards);
            CardRank secondHighCardRank2 = RankingUtil.getSecondHighCard(player2Cards);
            if (secondHighCardRank1.ordinal() > secondHighCardRank2.ordinal()) {
                return Collections.singletonList(player1);
            } else if (secondHighCardRank1.ordinal() < secondHighCardRank2.ordinal()) {
                return Collections.singletonList(player2);
            } else {
                CardRank thirdHighCardRank1 = RankingUtil.getThirdHighCard(player1Cards);
                CardRank thirdHighCardRank2 = RankingUtil.getThirdHighCard(player2Cards);
                if (thirdHighCardRank1.ordinal() > thirdHighCardRank2.ordinal()) {
                    return Collections.singletonList(player1);

                } else if (thirdHighCardRank1.ordinal() < thirdHighCardRank2.ordinal()) {
                    return Collections.singletonList(player2);

                } else {
                    CardRank fourthHighCardRank1 = RankingUtil.getFourthHighCard(player1Cards);
                    CardRank fourthHighCardRank2 = RankingUtil.getFourthHighCard(player2Cards);
                    if (fourthHighCardRank1.ordinal() > fourthHighCardRank2.ordinal()) {
                        return Collections.singletonList(player1);
                    } else if (fourthHighCardRank1.ordinal() < fourthHighCardRank2.ordinal()) {
                        return Collections.singletonList(player2);
                    } else {
                        CardRank fifthHighCardRank1 = RankingUtil.getFifthHighCard(player1Cards);
                        CardRank fifthHighCardRank2 = RankingUtil.getFifthHighCard(player2Cards);
                        if (fifthHighCardRank1.ordinal() > fifthHighCardRank2.ordinal()) {
                            return Collections.singletonList(player1);
                        } else {
                            return Collections.singletonList(player2);
                        }
                    }
                }
            }
        }

    }


    /**
     * gets the winner if 2 players have the same hand {@link OnePair}
     * @param player1
     * @param player2
     * @param communityCards
     * @return
     */
    private List<Player> getOnePairWinner(Player player1, Player player2, List<Card> communityCards) {
        List<Card> player1Cards = RankingUtil.getMergedCardList(player1.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getMergedCardList(player2.getHand(), communityCards);
        List<Card> firstPair1 = RankingUtil.checkPair(player1Cards, 2);
        List<Card> cards1 = RankingUtil.getNumberOfHighCards(player1Cards, firstPair1, 5);

        List<Card> firstPair2 = RankingUtil.checkPair(player2Cards, 2);
        List<Card> cards2 = RankingUtil.getNumberOfHighCards(player2Cards, firstPair2, 5);

        if (firstPair1.get(0).getRank().ordinal() > firstPair2.get(0).getRank().ordinal()) {
            return Collections.singletonList(player1);
        } else if (firstPair1.get(0).getRank().ordinal() < firstPair2.get(0).getRank().ordinal()) {
            return Collections.singletonList(player2);
        } else {
            if (cards1.get(0).getRank().ordinal() > cards2.get(0).getRank().ordinal()) {
                return Collections.singletonList(player1);
            } else if (cards1.get(0).getRank().ordinal() < cards2.get(0).getRank().ordinal()) {
                return Collections.singletonList(player2);
            } else {
                if (cards1.get(1).getRank().ordinal() > cards2.get(1).getRank().ordinal()) {
                    return Collections.singletonList(player1);
                } else if (cards1.get(1).getRank().ordinal() < cards2.get(1).getRank().ordinal()) {
                    return Collections.singletonList(player2);
                } else {
                    if (cards1.get(2).getRank().ordinal() > cards2.get(2).getRank().ordinal()) {
                        return Collections.singletonList(player1);

                    } else if (cards1.get(2).getRank().ordinal() < cards2.get(2).getRank().ordinal()) {
                        return Collections.singletonList(player2);
                    } else {
                        return Arrays.asList(player1, player2);
                    }
                }
            }
        }
    }

    /**
     * gets the winner if 2 players have the same hand {@link TwoPair}
     * @param player1
     * @param player2
     * @param communityCards
     * @return
     */
    private List<Player> get2PairWinner(Player player1, Player player2, List<Card> communityCards) {
        List<Card> player1Cards = RankingUtil.getMergedCardList(player1.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getMergedCardList(player2.getHand(), communityCards);
        List<Card> firstPair1 = RankingUtil.checkPair(player1Cards, 2);
        List<Card> secondPair1 = RankingUtil.checkPair(RankingUtil.getNumberOfHighCards(player1Cards,
                firstPair1, 5), 2);

        List<Card> firstPair2 = RankingUtil.checkPair(player2Cards, 2);
        List<Card> secondPair2 = RankingUtil.checkPair(RankingUtil.getNumberOfHighCards(player2Cards,
                firstPair2, 5), 2);

        if (firstPair1.get(0).getRank().ordinal() > firstPair2.get(0).getRank().ordinal()) {
            return Collections.singletonList(player1);
        } else if (firstPair1.get(0).getRank().ordinal() < firstPair2.get(0).getRank().ordinal()) {
            return Collections.singletonList(player2);
        } else {
            if (secondPair1.get(0).getRank().ordinal() > secondPair2.get(0).getRank().ordinal()) {
                return Collections.singletonList(player1);
            } else if (secondPair1.get(0).getRank().ordinal() < secondPair2.get(0).getRank().ordinal()) {
                return Collections.singletonList(player2);
            } else {
                List<Card> withoutFirstPair1 =
                        player1Cards.stream().filter(card -> !card.getRank().equals(firstPair1.get(0).getRank())).collect(Collectors.toList());
                List<Card> withoutSecondPair1 =
                        withoutFirstPair1.stream().filter(card -> !card.getRank().equals(secondPair1.get(0).getRank())).collect(Collectors.toList());

                List<Card> withoutFirstPair2 =
                        player2Cards.stream().filter(card -> !card.getRank().equals(firstPair2.get(0).getRank())).collect(Collectors.toList());
                List<Card> withoutSecondPair2 =
                        withoutFirstPair2.stream().filter(card -> !card.getRank().equals(secondPair2.get(0).getRank())).collect(Collectors.toList());


                if (RankingUtil.getHighCard(withoutSecondPair1).ordinal() > RankingUtil.getHighCard(withoutSecondPair2).ordinal()) {
                    return Collections.singletonList(player1);
                } else {
                    return Collections.singletonList(player2);
                }

            }
        }

    }


    /**
     * gets the winner if 2 players have the same hand {@link Flush}
     * @param player1
     * @param player2
     * @param communityCards
     * @return
     */
    private List<Player> getFlush(Player player1, Player player2, List<Card> communityCards) {

        List<Card> player1Cards = RankingUtil.getFlush(player1.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getFlush(player2.getHand(), communityCards);

        CardRank cardRank1 = RankingUtil.getHighCard(player1Cards);
        CardRank cardRank2 = RankingUtil.getHighCard(player2Cards);
        if (cardRank1.ordinal() > cardRank2.ordinal()) {
            return Collections.singletonList(player1);
        } else if (cardRank1.ordinal() < cardRank2.ordinal()) {
            return Collections.singletonList(player2);
        } else {
            CardRank secondHighCardRank1 = RankingUtil.getSecondHighCard(player1Cards);
            CardRank secondHighCardRank2 = RankingUtil.getSecondHighCard(player2Cards);
            if (secondHighCardRank1.ordinal() > secondHighCardRank2.ordinal()) {
                return Collections.singletonList(player1);
            } else if (secondHighCardRank1.ordinal() < secondHighCardRank2.ordinal()) {
                return Collections.singletonList(player2);
            } else {
                CardRank thirdHighCardRank1 = RankingUtil.getThirdHighCard(player1Cards);
                CardRank thirdHighCardRank2 = RankingUtil.getThirdHighCard(player2Cards);
                if (thirdHighCardRank1.ordinal() > thirdHighCardRank2.ordinal()) {
                    return Collections.singletonList(player1);

                } else if (thirdHighCardRank1.ordinal() < thirdHighCardRank2.ordinal()) {
                    return Collections.singletonList(player2);

                } else {
                    CardRank fourthHighCardRank1 = RankingUtil.getFourthHighCard(player1Cards);
                    CardRank fourthHighCardRank2 = RankingUtil.getFourthHighCard(player2Cards);
                    if (fourthHighCardRank1.ordinal() > fourthHighCardRank2.ordinal()) {
                        return Collections.singletonList(player1);
                    } else if (fourthHighCardRank1.ordinal() < fourthHighCardRank2.ordinal()) {
                        return Collections.singletonList(player2);
                    } else {
                        CardRank fifthHighCardRank1 = RankingUtil.getFifthHighCard(player1Cards);
                        CardRank fifthHighCardRank2 = RankingUtil.getFifthHighCard(player2Cards);
                        if (fifthHighCardRank1.ordinal() > fifthHighCardRank2.ordinal()) {
                            return Collections.singletonList(player1);
                        } else {
                            return Collections.singletonList(player2);
                        }
                    }
                }
            }
        }
    }


    /**
     * gets the winner if 2 players have the same hand {@link ThreeOfAKind}
     * @param player1
     * @param player2
     * @param communityCards
     * @return
     */
    private List<Player> getThreeOfAKindWinner(Player player1, Player player2, List<Card> communityCards) {
        List<Card> player1Cards = RankingUtil.getMergedCardList(player1.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getMergedCardList(player2.getHand(), communityCards);

        List<Card> threeOfAKind1 = RankingUtil.checkPair(player1Cards, 3);
        List<Card> threeOfAKind2 = RankingUtil.checkPair(player2Cards, 3);
        CardRank cardRank1 = RankingUtil.getHighCard(threeOfAKind1);
        CardRank cardRank2 = RankingUtil.getHighCard(threeOfAKind2);
        if (cardRank1.ordinal() > cardRank2.ordinal()) {
            return Collections.singletonList(player1);
        } else if (cardRank1.ordinal() < cardRank2.ordinal()) {
            return Collections.singletonList(player2);
        } else {
            List<Card> others1 = RankingUtil.getNumberOfHighCards(player1Cards, threeOfAKind1, 2);
            List<Card> others2 = RankingUtil.getNumberOfHighCards(player2Cards, threeOfAKind2, 2);
            if (others1.get(0).getRank().ordinal() > others2.get(0).getRank().ordinal()) {
                return Collections.singletonList(player1);
            } else if (others1.get(0).getRank().ordinal() < others2.get(0).getRank().ordinal()) {
                return Collections.singletonList(player2);
            } else {
                if (others1.get(1).getRank().ordinal() > others2.get(1).getRank().ordinal()) {
                    return Collections.singletonList(player1);
                } else if (others1.get(1).getRank().ordinal() < others2.get(1).getRank().ordinal()) {
                    return Collections.singletonList(player2);
                } else {
                    return Arrays.asList(player1, player2);
                }
            }
        }


    }


    /**
     * gets the winner if 2 players have the same hand {@link FullHouse}
     * @param player1
     * @param player2
     * @param communityCards
     * @return
     */
    private List<Player> getFullHouse(Player player1, Player player2, List<Card> communityCards) {

        List<Card> player1Cards = RankingUtil.getFullHouse(player1.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getFullHouse(player2.getHand(), communityCards);

        List<Card> threeOfAKind1 = RankingUtil.checkPair(player1Cards, 3);
        CardRank cardRank1 = RankingUtil.getHighCard(threeOfAKind1);

        List<Card> threeOfAKind2 = RankingUtil.checkPair(player2Cards, 3);
        CardRank cardRank2 = RankingUtil.getHighCard(threeOfAKind2);

        if (cardRank1.ordinal() > cardRank2.ordinal()) {
            return Collections.singletonList(player1);
        } else if (cardRank1.ordinal() < cardRank2.ordinal()) {
            return Collections.singletonList(player2);
        } else {
            List<Card> pair2 = RankingUtil.checkPair(player2Cards, 2);
            CardRank secondCardRank1 = RankingUtil.getHighCard(pair2);

            List<Card> pair1 = RankingUtil.checkPair(player1Cards, 2);
            CardRank secondCardRank2 = RankingUtil.getHighCard(pair1);


            if (secondCardRank1.ordinal() > secondCardRank2.ordinal()) {
                return Collections.singletonList(player1);
            } else {
                return Collections.singletonList(player2);
            }
        }

    }

    /**
     * gets the winner if 2 players have the same hand {@link FourOfAKind}
     * @param player1
     * @param player2
     * @param communityCards
     * @return
     */
    private List<Player> getFourOfAKind(Player player1, Player player2, List<Card> communityCards) {
        List<Card> player1Cards = RankingUtil.getMergedCardList(player1.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getMergedCardList(player2.getHand(), communityCards);
        List<Card> cards1 = RankingUtil.getFourOfAKind(player1Cards);
        CardRank cardRank1 = RankingUtil.getHighCard(cards1);

        List<Card> cards2 = RankingUtil.getFourOfAKind(player2Cards);
        CardRank cardRank2 = RankingUtil.getHighCard(cards2);
        if (cardRank1.ordinal() > cardRank2.ordinal()) {
            return Collections.singletonList(player1);
        } else {
            return Collections.singletonList(player2);
        }

    }

    /**
     * gets the winner if 2 players have the same Hand {@link StraightFlush}
     * @param winner
     * @param player
     * @param communityCards
     * @return
     */
    private List<Player> getStraightFlushWinner(Player winner, Player player, List<Card> communityCards) {
        List<Card> player1Cards = RankingUtil.getMergedCardList(winner.getHand(), communityCards);
        List<Card> player2Cards = RankingUtil.getMergedCardList(player.getHand(), communityCards);
        CardRank cardRank1 = RankingUtil.getHighStraightFlushCard(player1Cards);
        CardRank cardRank2 = RankingUtil.getHighStraightFlushCard(player2Cards);
        if (cardRank1.ordinal() > cardRank2.ordinal()) {
            return Collections.singletonList(winner);
        } else if (cardRank1.ordinal() < cardRank2.ordinal()) {
            return Collections.singletonList(player);
        } else {
            return Arrays.asList(winner, player);
        }
    }

    /**
     * gets the winner if 2 players have the same Hand {@link RoyalFlush }
     * @param winnerHand
     * @param playerHand
     * @return
     */
    private List<Hand> getRoyalWinner(Hand winnerHand, Hand playerHand) {
        return Arrays.asList(winnerHand, playerHand);
    }




}
