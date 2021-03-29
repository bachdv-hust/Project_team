package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class HumanPlayer implements IPlayer {
    private boolean isGameOver = false;

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    private List<Card> currentCards = new ArrayList<>();
    private int index = -1;
    private Scanner scanner;
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

    @Override
    public void setCard(Card c) {
        currentCards.add(c);
        System.out.println("You received the card " + c.toString());
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Guess getGuess() {
        // TODO: 3/29/2021 validate

        try {
            ScannerUtils scanner = ScannerUtils.getInstance();
            ArrayList<Card> listGuess = new ArrayList<>();

            System.out.println("Which person index do you want to suggest?");
            int suspectIndex = scanner.nextInt(0,ppl.size()-1);
            listGuess.add(ppl.get(suspectIndex));

            System.out.println("Which location index do you want to suggest?");
            int locIndex = scanner.nextInt(0,places.size()-1);
            listGuess.add(places.get(locIndex));

            System.out.println("Which weapon index do you want to suggest?");
            int weaIndex = scanner.nextInt(0,weapons.size()-1);
            listGuess.add(weapons.get(weaIndex));
            System.out.println("Is this an accusation");
            String res = scanner.nextYesNoQues();

            boolean isAccusation = false;
            if (res.equalsIgnoreCase("y")) {
                isAccusation = true;
            }

            Guess guess = new Guess(isAccusation, listGuess);

            //scanner.close();
            return guess;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HumanPlayer)) return false;
        HumanPlayer that = (HumanPlayer) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public void receiveInfo(IPlayer ip, Card c) {
        if (ip == null) {
            System.out.println("No one could refute your suggestion.");
        } else {
            System.out.println("Player " + ip.getIndex() + " refuted your suggestion by showing you " + c.toString());
        }
    }

    @Override
    public Card canAnswer(Guess guess, IPlayer ip) {
        if (ip.equals(this)) {
            return null;
        }
        List<Card> guessCard = guess.getListCards();
        List<Card> cardContained = new ArrayList<>();
        for (Card c : guessCard) {
            if (currentCards.contains(c)) {
                cardContained.add(c);
            }
        }

        switch (cardContained.size()) {
            case 0:
                System.out.println("Player " + ip.getIndex() + " asked you about " + guess + ", but you couldn't answer.");
                return null;

            case 1:
                System.out.println("Player " + ip.getIndex() + " asked you about " + guess + " ,you only have one card," + cardContained.get(0) + ", showed it to them.");
                return cardContained.get(0);

            default:
                ScannerUtils scanner = ScannerUtils.getInstance();
                System.out.println("Player " + ip.getIndex() + " asked you about " + guess + " .Which do you show?" + cardContained);
                int index = scanner.nextInt(0, cardContained.size()-1);
                return cardContained.get(index);
        }
    }
}
