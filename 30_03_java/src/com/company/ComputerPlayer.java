package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class ComputerPlayer implements IPlayer {
    private boolean isGameOver = false;

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    /// CAn quan li list ddau vao -> ham getGuess dam bao la ramdom card ma no chua co
    private List<Card> currentCards = new ArrayList<>();
    private int index = -1;
    private final ArrayList<Card> ppl = new ArrayList<>();
    private final ArrayList<Card> places = new ArrayList<>();
    private final ArrayList<Card> weapons = new ArrayList<>();

    @Override
    public void setUp(int numPlayers, int index, ArrayList<Card> ppl, ArrayList<Card> places, ArrayList<Card> weapons) {
        this.index = index;
        this.ppl.addAll(ppl);
        this.places.addAll(places);
        this.weapons.addAll(weapons);
    }

    private void removeCardIsPredicted(Card c) {
        switch (c.getType()) {
            case LOCATION:
                places.remove(c);
                break;
            case SUSPECT:
                ppl.remove(c);
                break;
            case WEAPON:
                weapons.remove(c);
                break;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ComputerPlayer)) return false;
        ComputerPlayer that = (ComputerPlayer) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public void setCard(Card c) {
        currentCards.add(c);
        removeCardIsPredicted(c);
    }

    @Override
    public int getIndex() {
        return index;
    }

    Random random = new Random();

    @Override
    public Guess getGuess() {
        ArrayList<Card> cards = new ArrayList<>();
        if (!(ppl.isEmpty() || places.isEmpty() || weapons.isEmpty())) {
            cards.add(ppl.get(random.nextInt(ppl.size())));
            cards.add(places.get(random.nextInt(places.size())));
            cards.add(weapons.get(random.nextInt(weapons.size())));
        }
        boolean isAccusation = ppl.size() == 1 && places.size() == 1 && weapons.size() == 1;
        return new Guess(isAccusation, cards);
    }

    @Override
    public void receiveInfo(IPlayer ip, Card c) {
        if (ip != null && c != null) {
            removeCardIsPredicted(c);
        }
    }

    @Override
    public Card canAnswer(Guess g, IPlayer ip) {
        Card card = null;
        for (Card c : currentCards) {
            if (g.getListCards().contains(c)) {
                card = c;
                break;
            }
        }
        return card;
    }
}
