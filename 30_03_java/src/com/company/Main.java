package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ArrayList<IPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer());
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            cards.add(new Card(Card.CardType.SUSPECT, "sus "+i));
        }

        for (int i = 0; i < 5; i++) {
            cards.add(new Card(Card.CardType.LOCATION, "loc "+i));
        }

        for (int i = 0; i < 5; i++) {
            cards.add(new Card(Card.CardType.WEAPON, "wea "+i));
        }

	    Model model = new Model(cards ,players);
        model.startGame();
    }
}
