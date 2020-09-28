package com.synacy.poker.utils;

import com.synacy.poker.model.hand.HandType;

public class EnumUtil {

    public static String getHandTypeValue(HandType handType) {
        switch (handType) {
            case FLUSH:
                return "Flush";
            case FULL_HOUSE:
                return "Full House";
            case ONE_PAIR:
                return "One Pair";
            case FOUR_OF_A_KIND:
                return "Quads";
            case STRAIGHT:
                return "Straight";
            case STRAIGHT_FLUSH:
                return "Straight Flush";
            case ROYAL_FLUSH:
                return "Royal Flush";
            case TWO_PAIR:
                return "Two Pair";
            case HIGH_CARD:
                return "High card";
            case THREE_OF_A_KIND:
                return "Trips";
            default:
                //ignored the default
                return "";
        }
    }
}
