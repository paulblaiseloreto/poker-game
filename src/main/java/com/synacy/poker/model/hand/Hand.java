package com.synacy.poker.model.hand;

import com.synacy.poker.model.hand.types.Flush;
import com.synacy.poker.model.hand.types.FullHouse;

/**
 * The base class of the different Hands such as {@link Flush},
 * {@link FullHouse}, etc.
 */
public abstract class Hand {

    /**
     * @return The {@link HandType}
     */
    public abstract HandType getHandType();

}
