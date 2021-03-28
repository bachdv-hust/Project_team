package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements IPlayer{
    private List<Card> currentCards = new ArrayList<>();
    private int index = -1;
    private Scanner scanner;
    ArrayList<Card> ppl;
    ArrayList<Card> places;
    ArrayList<Card> weapons;
    @Override
    public void setUp(int numPlayers, int index, ArrayList<Card> ppl, ArrayList<Card> places, ArrayList<Card> weapons) {
        this.index = index;
        this.ppl = ppl;
        this.places = places;
        this.weapons = weapons;
    }

    @Override
    public void setCard(Card c) {
        currentCards.add(c);
        System.out.println("You received the card "+c.toString());
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Guess getGuess() {
        // TODO: 3/29/2021 validate

        try {
            scanner = new Scanner(System.in);
            ArrayList<Card> listSuggest = new ArrayList<>();

            System.out.println("Which person index do you want to suggest?");
            int susIndex = scanner.nextInt();
            listSuggest.add(ppl.get(susIndex));

            System.out.println("Which location index do you want to suggest?");
            int locIndex = scanner.nextInt();
            listSuggest.add(places.get(locIndex));

            System.out.println("Which weapon index do you want to suggest?");
            int weaIndex = scanner.nextInt();
            scanner.nextLine();
            listSuggest.add(weapons.get(weaIndex));
            System.out.println("Is this an accusation (Y/[N])?");
            String res = scanner.nextLine();

            boolean isAccusation = false;
            if(res.contains("Y") || res.contains("y")) {
                isAccusation = true;
            }

            Guess guess = new Guess(isAccusation, listSuggest);

            //scanner.close();
            return guess;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void receiveInfo(IPlayer ip, Card c) {
        if(ip == null) {
            System.out.println("No one could refute your suggestion.");
        } else {
            System.out.println("Player "+ ip.getIndex() + " refuted your suggestion by showing you " + c.toString());
        }
    }

    @Override
    public Card canAnswer(Guess guess, IPlayer ip) {
        if(ip == this) {
            return null;
        }
        List<Card> guessCard = guess.getListCards();
        List<Card> cardContained = new ArrayList<>();
        for (Card c: guessCard) {
            if (currentCards.contains(c)) {
                cardContained.add(c);
            }
        }

        switch (cardContained.size()) {
            case 0 -> {
                System.out.println("Player " + ip.getIndex() + " asked you about " + guess + ", but you couldn't answer.");
                return null;
            }
            case 1 -> {
                System.out.println("Player " + ip.getIndex() + " asked you about " + guess + " ,you only have one card," + cardContained.get(0) + ", showed it to them.");
                return cardContained.get(0);
            }
            default -> {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Player " + ip.getIndex() + " asked you about " + guess + " .Which do you show?" + cardContained);
                int index = scanner.nextInt();
                if(index >= cardContained.size()) {
                    index = 0;
                }
                //scanner.close();
                return cardContained.get(index);
            }
        }
    }
}
