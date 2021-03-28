package com.company;

import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer implements IPlayer{
    private List<Card> currentCards = new ArrayList<>();
    private int index = -1;
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
        //System.out.println("Computer received the card "+c.toString());
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public Guess getGuess() {
        ArrayList<Card> cards = new ArrayList<>();
        cards.add(ppl.get(0));
        cards.add(places.get(0));
        cards.add(weapons.get(0));
        return new Guess(false, cards);
    }

    @Override
    public void receiveInfo(IPlayer ip, Card c) {

    }

    @Override
    public Card canAnswer(Guess g, IPlayer ip) {
        Card card = null;
        for (Card c : currentCards) {
            if(g.getListCards().contains(c)) {
                card = c;
                break;
            }
        }
        return card;
    }
}
