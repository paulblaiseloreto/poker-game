package com.synacy.poker.utils;

import com.synacy.poker.model.card.Card;
import com.synacy.poker.model.card.CardRank;
import com.synacy.poker.model.card.CardSuit;
import com.synacy.poker.model.game.Player;
import com.synacy.poker.model.hand.Hand;
import com.synacy.poker.model.hand.HandType;

import java.util.*;
import java.util.stream.Collectors;

import static com.synacy.poker.model.card.CardRank.*;


public class RankingUtil {

    /**
     * checking the card is the same suit
     * @param playerCards
     * @param tableCards
     * @return
     */
    private static Boolean isSameSuit(List<Card> playerCards, List<Card> tableCards) {
        CardSuit suit = playerCards.get(0).getSuit();

        if (!suit.equals(playerCards.get(1).getSuit())) {
            return false;
        }

        for (Card card : tableCards) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }

        return true;
    }

    /**
     * check all cards have the same suit or not
     * @param totalCards
     * @return
     */
    private static Boolean isSameSuit(List<Card> totalCards) {
        CardSuit suit = totalCards.get(0).getSuit();

        for (Card card : totalCards) {
            if (!card.getSuit().equals(suit)) {
                return false;
            }
        }

        return true;
    }

    /**
     * returns a card that has different suit
     * @param totalCards
     * @return
     */
    private static Card getDiffSuit(List<Card> totalCards) {
        CardSuit suit = totalCards.get(0).getSuit();
        List<Card> sameSuit =
                totalCards.stream().filter(card -> card.getSuit().equals(totalCards.get(0).getSuit())).collect(Collectors.toList());
        if (sameSuit.size() == 1) {
            sameSuit =
                    totalCards.stream().filter(card -> card.getSuit().equals(totalCards.get(1).getSuit())).collect(Collectors.toList());
            if (sameSuit.size() == 1) {
                return null;
            } else {
                suit = sameSuit.get(0).getSuit();
            }
        }
        for (Card card : totalCards) {
            if (!card.getSuit().equals(suit)) {
                return card;
            }
        }

        return null;
    }


    /**
     * The method is used to get Royal Flush card list. (5 cards)
     * @param totalCards
     * @return
     */
    public static List<Card> getRoyalFlushCards(List<Card> totalCards) {
        Optional<Card> cardTen =
                totalCards.stream().filter(card -> card.getRank().equals(TEN)).findFirst();
        Optional<Card> cardJack =
                totalCards.stream().filter(card -> card.getRank().equals(JACK)).findFirst();
        Optional<Card> cardQueen =
                totalCards.stream().filter(card -> card.getRank().equals(QUEEN)).findFirst();
        Optional<Card> cardKing =
                totalCards.stream().filter(card -> card.getRank().equals(KING)).findFirst();
        Optional<Card> cardAce =
                totalCards.stream().filter(card -> card.getRank().equals(ACE)).findFirst();

        if (cardTen.isPresent() && cardJack.isPresent() && cardQueen.isPresent() && cardKing.isPresent() && cardAce.isPresent()) {
            List<Card> royalFlush = Arrays.asList(cardAce.get(), cardKing.get(), cardQueen.get(),
                    cardJack.get(),
                    cardTen.get());
            Card cardDiffSuit = getDiffSuit(royalFlush);
            if (cardDiffSuit != null) {
                Optional<Card> cardSameSuit = getAnotherFromTheRest(cardDiffSuit, totalCards);
                royalFlush =
                        royalFlush.stream().filter(card -> !card.equals(cardDiffSuit)).collect(Collectors.toList());
                if (cardSameSuit.isPresent()) {
                    if (royalFlush.get(0).getSuit().equals(cardSameSuit.get().getSuit())) {
                        royalFlush.add(cardSameSuit.get());
                    } else {
                        royalFlush = Collections.emptyList();
                    }
                } else {
                    royalFlush = Collections.emptyList();
                }
            }
            return royalFlush;
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * get the remaining cards from total cards and without a specific card
     * @param cardDiffSuit
     * @param totalCards
     * @return
     */
    private static Optional<Card> getAnotherFromTheRest(Card cardDiffSuit, List<Card> totalCards) {
        return totalCards.stream().filter(card -> card.getRank().equals(cardDiffSuit.getRank()) && !card.getSuit().equals(cardDiffSuit.getSuit())).findFirst();
    }


    /**
     * @deprecated we haven't used yet.
     * @param playerCards
     * @param tableCards
     * @return
     */
    public static Card getHighCard(List<Card> playerCards, List<Card> tableCards) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(tableCards);
        allCards.add(playerCards.get(0));
        allCards.add(playerCards.get(1));

        Card highCard = allCards.get(0);
        for (Card card : allCards) {
            if (card.getRankToInt() > highCard.getRankToInt()) {
                highCard = card;
            }
        }
        return highCard;
    }

    /**
     * gets high card from all cards
     * @param allCards
     * @return
     */
    public static CardRank getHighCard(List<Card> allCards) {
        Card highCard = allCards.get(0);
        for (Card card : allCards) {
            if (card.getRankToInt() > highCard.getRankToInt()) {
                highCard = card;
            }
        }
        return highCard.getRank();
    }


    /**
     * @deprecated Uses getRoyalFlushCards instead.
     * @param cards
     * @return
     */
    public static List<Card> getRoyalFlush(List<Card> cards) {
        if (!isSameSuit(cards)) {
            return Collections.emptyList();
        }
        List<CardRank> rankEnumList = cards.stream().map(Card::getRank).collect(Collectors.toList());

        if (rankEnumList.contains(TEN)
                && rankEnumList.contains(JACK)
                && rankEnumList.contains(QUEEN)
                && rankEnumList.contains(KING)
                && rankEnumList.contains(ACE)) {

            return cards;
        }

        return Collections.emptyList();
    }


    /**
     * gets the straight flush from the merged card list
     * @param cards
     * @return
     */
    public static List<Card> getStraightFlush(List<Card> cards){
        return getSequence(cards, 5, true, HandType.STRAIGHT_FLUSH);
    }


    /**
     * orders Ascending card list
     * @param ordered
     * @return
     */
    private static List<Card> getOrderedCardList(List<Card> ordered) {
        Collections.sort(ordered, (c1, c2) -> c1.getRankToInt() < c2.getRankToInt() ? -1 : 1);
        return ordered;
    }

    /**
     * orders Descending card list
     * @param ordered
     * @return
     */
    private static List<Card> getOrderedDescCardList(List<Card> ordered) {
        Collections.sort(ordered, (c1, c2) -> c1.getRankToInt() > c2.getRankToInt() ? -1 : 1);
        return ordered;
    }


    /**
     * get list of card from total cards
     * @param totalCards
     * @param sequenceSize
     * @param compareSuit
     * @param handType
     * @return
     */
    private static List<Card> getSequence(List<Card> totalCards, Integer sequenceSize, Boolean compareSuit,
                                          HandType handType) {
        List<Card> orderedList = getOrderedCardList(totalCards);
        List<Card> sequenceList = new ArrayList<>();

        Card cardPrevious = null;
        for (Card card : orderedList) {
            if (cardPrevious != null) {
                if ((card.getRankToInt() - cardPrevious.getRankToInt()) == 1) {
                    if (!compareSuit
                            || cardPrevious.getSuit().equals(card.getSuit())) {
                        if (sequenceList.isEmpty()) {
                            sequenceList.add(cardPrevious);
                        }
                        sequenceList.add(card);
                    }
                } else {
                    if (sequenceList.size() == sequenceSize) {
                        return sequenceList;
                    }
                    sequenceList.clear();
                }
            }
            cardPrevious = card;
        }

        return (sequenceList.size() == sequenceSize) ? sequenceList :
                ( sequenceList.size() > sequenceSize && handType.equals(HandType.STRAIGHT) ?
                getTop5HighCards(sequenceList): Collections.emptyList());
    }

    /**
     * gets 5 cards for each players from the card list
     * @param sequenceList
     * @return
     */
    private static List<Card> getTop5HighCards(List<Card> sequenceList) {
       return sequenceList.stream().limit(5).collect(Collectors.toList());
    }

    /**
     * get card rank from cards list. Used for Straight and Straight Flush
     * @param allCards
     * @return
     */
    public static CardRank getHighStraightFlushCard(List<Card> allCards) {
        List<CardRank> cardRanks = allCards.stream().map(Card::getRank).collect(Collectors.toList());
        if (containsAce(allCards) && cardRanks.contains(TEN) && cardRanks.contains(JACK) && cardRanks.contains(QUEEN)
                && cardRanks.contains(KING)) {
            return ACE;
        }

        if (containsAce(allCards)) {
            allCards =
                    allCards.stream().filter(card -> !card.getRank().equals(ACE)).collect(Collectors.toList());
        }
        return getHighCard(allCards);
    }

    /**
     * checks whether the card list contains ACE or not.
     * @param cards
     * @return
     */
    private static boolean containsAce(List<Card> cards){
        return cards.stream().anyMatch(card -> card.getRank().equals(ACE));
    }

    /**
     * gets the card rank (toString) from the card list
     * @param cards
     * @return
     */
    public static String getSequenceHighCardRanks(List<Card> cards){
        List<CardRank> cardRanks = cards.stream()
                .sorted(Comparator.comparing(Card::getRankToInt, Comparator.reverseOrder()))
                .map(Card::getRank)
                .collect(Collectors.toList());
        return cardRanks.stream().map(CardRank::toString).collect(Collectors.joining(","));
    }

    /**
     * gets the number of cards from the cards list
     * @param cards
     * @param number
     * @return
     */
    public static List<Card> getSequenceHighCards(List<Card> cards, int number){
        return cards.stream()
                .sorted(Comparator.comparing(Card::getRankToInt, Comparator.reverseOrder()))
                .limit(number)
                .collect(Collectors.toList());
    }

    /**
     * returns a card list from the player's cards and the community cards  on the table
     * @param playerCards
     * @param tableCards
     * @return
     */
    public static List<Card> getMergedCardList(List<Card> playerCards,
                                                List<Card> tableCards) {
        List<Card> merged = new ArrayList<Card>();
        merged.addAll(tableCards);
        merged.add(playerCards.get(0));
        merged.add(playerCards.get(1));

        return merged;
    }

    public static List<Card> getFourOfAKind(List<Card> totalCards) {
        return checkPair(totalCards, 4);
    }


    /**
     * checks get card list that has the same rank. used to get three of a kind or a pair
     * @param mergedList
     * @param pairSize
     * @return
     */
    public static List<Card> checkPair(List<Card> mergedList, Integer pairSize) {
        List<Card> checkedPair = new ArrayList<>();
        for (Card card1 : mergedList) {
            checkedPair.add(card1);
            for (Card card2 : mergedList) {
                if (!card1.equals(card2)
                        && card1.getRank().equals(card2.getRank())) {
                    checkedPair.add(card2);
                }
            }
            if (checkedPair.size() == pairSize) {
                return checkedPair;
            }
            checkedPair.clear();
        }
        return Collections.emptyList();
    }

    /**
     * gets the other card from card list that without the specific card list
     * @param rankings
     * @param cards
     * @return
     */
    public static List<Card> getOthers(List<Card> rankings, List<Card> cards) {
       return cards.stream().filter(card -> !card.getRank().equals(rankings.get(0).getRank())).collect(Collectors.toList());
    }


    /**
     * gets the full house cards from player's cards and the community cards on the table
     * @param playerCards
     * @param tableCards
     * @return
     */
    public static List<Card> getFullHouse(List<Card> playerCards, List<Card> tableCards) {
        List<Card> mergedList = getMergedCardList(playerCards, tableCards);
        List<Card> threeList = checkPair(mergedList, 3);
        if (!threeList.isEmpty()) {
            mergedList.removeAll(threeList);
            List<Card> twoList = checkPair(mergedList, 2);
            if (!twoList.isEmpty()) {
                threeList.addAll(twoList);
                return threeList;
            }
        }
        return Collections.emptyList();
    }


    /**
     * gets the full house cards from the merged card list
     * @param mergedList
     * @return
     */
    public static List<Card> getFullHouse(List<Card> mergedList) {
        List<Card> threeList = checkPair(mergedList, 3);
        if (!threeList.isEmpty()) {
            mergedList.removeAll(threeList);
            List<Card> twoList = checkPair(mergedList, 2);
            if (!twoList.isEmpty()) {
                threeList.addAll(twoList);
                return threeList;
            }
        }
        return Collections.emptyList();
    }


    /**
     * gets the flush cards from the player's cards and the community cards on the table
     * @param playerCards
     * @param tableCards
     * @return
     */
    public static List<Card> getFlush(List<Card> playerCards, List<Card> tableCards) {
        List<Card> mergedList = getMergedCardList(playerCards, tableCards);
        List<Card> flushList = new ArrayList<>();

        for (Card card1 : mergedList) {
            for (Card card2 : mergedList) {
                if (card1.getSuit().equals(card2.getSuit())) {
                    if (!flushList.contains(card1)) {
                        flushList.add(card1);
                    }
                    if (!flushList.contains(card2)) {
                        flushList.add(card2);
                    }
                }
                if (flushList.size() == 5) {
                    return flushList;
                }
            }
            flushList.clear();
        }
        return Collections.emptyList();
    }


    /**
     * gets the straight from merged card list
     * @param totalCards
     * @return
     */
    public static List<Card> getStraight(List<Card> totalCards) {
        return getSequence(totalCards, 5, Boolean.FALSE, HandType.STRAIGHT);
    }


    /**
     * Gets thress of a kind from the player's card and community cards on the table
     * @param playerCards
     * @param tableCards
     * @return
     */
    public static List<Card> getThreeOfAKind(List<Card> playerCards,
                                             List<Card> tableCards) {
        List<Card> mergedList = getMergedCardList(playerCards, tableCards);
        return checkPair(mergedList, 3);
    }


    /**
     * get number of high cards from the total cards that without the specific cards
     * @param cards
     * @param rankings
     * @param number
     * @return
     */
    public static List<Card> getNumberOfHighCards(List<Card> cards, List<Card> rankings, int number) {
        for (Card card : rankings
        ) {
            cards =
                    cards.stream().filter(card2 -> !card2.getRank().equals(card.getRank())).collect(Collectors.toList());
        }
        return getOrderedDescCardList(cards).stream().limit(number).collect(Collectors.toList());
    }


    /**
     * get two paid cards from the player's card and cards on the table
     * @param playerCards
     * @param tableCards
     * @return
     */
    public static List<Card> getTwoPair(List<Card> playerCards, List<Card> tableCards) {
        List<Card> mergedList = getMergedCardList(playerCards, tableCards);
        mergedList.sort((c1, c2) -> c1.getRankToInt() > c2.getRankToInt() ? -1 : 1);
        List<Card> twoPair1 = checkPair(mergedList, 2);
        if (!twoPair1.isEmpty()) {
            mergedList.removeAll(twoPair1);
            List<Card> twoPair2 = checkPair(mergedList, 2);
            if (!twoPair2.isEmpty()) {
                twoPair1.addAll(twoPair2);
                return twoPair1;
            }
        }
        return Collections.emptyList();
    }


    /**
     * get one pair from player's card and cards on the table
     * @param playerCards
     * @param tableCards
     * @return
     */
    public static List<Card> getOnePair(List<Card> playerCards, List<Card> tableCards) {
        List<Card> mergedList = getMergedCardList(playerCards, tableCards);
        return checkPair(mergedList, 2);
    }

    public static Integer getHandTypeToInt(Hand hand) {
        return hand.getHandType().ordinal();
    }


    /**
     *
     * @param player
     * @return
     */
    private static Integer sumRankingList(Player player) {
        Integer sum = 0;
        for (Card card : player.getHand()) {
            sum += card.getRankToInt();
        }
        return sum;
    }

    /**
     * @deprecated check high sequence card, we haven't used yet.
     * @param player1
     * @param player2
     * @return
     */
    public static Player checkHighSequence(Player player1, Player player2) {
        Integer player1Rank = sumRankingList(player1);
        Integer player2Rank = sumRankingList(player2);
        if (player1Rank > player2Rank) {
            return player1;
        } else if (player1Rank < player2Rank) {
            return player2;
        }
        return null;
    }

    /**
     * get second high card from card list
     * @param cards
     * @return
     */
    public static CardRank getSecondHighCard(List<Card> cards) {
        CardRank highCard = getHighCard(cards);
        List<Card> remainingCards =
                cards.stream().filter(card -> !card.getRank().equals(highCard)).collect(Collectors.toList());
        return getHighCard(remainingCards);
    }

    /**
     * get third high card from card list
     * @param cards
     * @return
     */
    public static CardRank getThirdHighCard(List<Card> cards) {
       List<Card> cards1 = getOrderedDescCardList(cards);
       return cards1.get(2).getRank();

    }

    /**
     * get fourth high card from list cards
     * @param cards
     * @return
     */
    public static CardRank getFourthHighCard(List<Card> cards) {
        List<Card> cards1 = getOrderedDescCardList(cards);
        return cards1.get(3).getRank();
    }

    /**
     * get fifth high card from the list
     * @param cards
     * @return
     */
    public static CardRank getFifthHighCard(List<Card> cards) {
        List<Card> cards1 = getOrderedDescCardList(cards);
        return cards1.get(4).getRank();

    }


    /**
     * compares high card from 2 players, then return the higher player
     * @param player1
     * @param player1HighCard
     * @param player2
     * @param player2HighCard
     * @return
     */
    private Player compareHighCard(Player player1, Card player1HighCard,
                                   Player player2, Card player2HighCard) {
        if (player1HighCard.getRankToInt() > player2HighCard.getRankToInt()) {
            return player1;
        } else if (player1HighCard.getRankToInt() < player2HighCard
                .getRankToInt()) {
            return player2;
        }
        return null;
    }



}
