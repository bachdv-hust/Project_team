package com.company;

import java.util.List;

public class Guess {
    public boolean isAccusation() {
        return isAccusation;
    }

    public Guess(boolean isAccusation, List<Card> listCards) {
        this.isAccusation = isAccusation;
        this.listCards = listCards;
    }

    public List<Card> getListCards() {
        return listCards;
    }

    private final boolean isAccusation;
    private final List<Card> listCards;

    @Override
    public String toString() {
        return listCards.toString();
    }
}
