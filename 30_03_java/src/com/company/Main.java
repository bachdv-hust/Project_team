package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ArrayList<IPlayer> players = new ArrayList<>();
        players.add(new HumanPlayer());
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());
        players.add(new ComputerPlayer());

        ArrayList<Card> originalCards = initOriginalCards();
        Model model = new Model(originalCards, players);
        model.startGame();
    }

    private static ArrayList<Card> initOriginalCards() {
        ArrayList<Card> originalCards = new ArrayList<>();
        ///
        for (int i = 0; i < 8; i++) {
            originalCards.add(new Card(Card.CardType.SUSPECT, "sus " + i));
        }

        for (int i = 0; i < 8; i++) {
            originalCards.add(new Card(Card.CardType.LOCATION, "loc " + i));
        }

        for (int i = 0; i < 8; i++) {
            originalCards.add(new Card(Card.CardType.WEAPON, "wea " + i));
        }
        return originalCards;
    }
}
