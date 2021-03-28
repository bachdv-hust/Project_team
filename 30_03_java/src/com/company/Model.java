package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Model {
    private final ArrayList<Card> suspects = new ArrayList<>();
    private final ArrayList<Card> locations = new ArrayList<>();
    private final ArrayList<Card> weapons = new ArrayList<>();
    private final ArrayList<IPlayer> players;
    private ArrayList<Card> allCards = new ArrayList<>();
    private ArrayList<Card> result = new ArrayList<>();

    public Model(ArrayList<Card> allCards, ArrayList<IPlayer> players) {
        this.players = players;
        this.allCards.addAll(allCards);
        for (Card card : allCards) {
            switch (card.getType()) {
                case SUSPECT -> suspects.add(card);
                case WEAPON -> weapons.add(card);
                case LOCATION -> locations.add(card);
            }
        }
    }

    void startGame() {
        // take 3 cards
        Random random = new Random();
        Card tmp = suspects.get(Math.abs(random.nextInt()) % suspects.size());
        result.add(tmp);
        this.allCards.remove(tmp);

        tmp = locations.get(Math.abs(random.nextInt()) % locations.size());
        result.add(tmp);
        this.allCards.remove(tmp);

        tmp = weapons.get(Math.abs(random.nextInt()) % weapons.size());
        result.add(tmp);
        this.allCards.remove(tmp);

        System.out.println(result);

        int turn = 0;
        int count = 0;
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setUp(players.size(), i, suspects, locations, weapons);
        }
        shuffleCardsToPlayer();
        while (true) {
            count++;
            System.out.println("------- Turn " + count + " ---------");

            System.out.println("Here are the names of all the suspects:");
            System.out.println(suspects);
            System.out.println("Here are the names of all the locations:");
            System.out.println(locations);
            System.out.println("Here are the names of all the weapons:");
            System.out.println(weapons);

            IPlayer currentPlayer = players.get(turn);
            if (currentPlayer instanceof HumanPlayer) {
                System.out.println("It's your turn");
            } else {
                System.out.println("Current turn: player " + turn);
            }

            Guess guess = currentPlayer.getGuess();
            if (guess.isAccusation()) {
                boolean isEqual = true;
                for (Card card : guess.getListCards()) {
                    if (!result.contains(card)) {
                        isEqual = false;
                        break;
                    }
                }

                if (isEqual) {
                    System.out.println("Player " + currentPlayer.getIndex() + "won the game.");
                    break;
                } else {
                    System.out.println("Player " + currentPlayer.getIndex() + " made a bad accusation and was removed from the game.");
                    players.remove(currentPlayer);
                    if (players.size() == 1) {
                        System.out.println("Game over");
                        break;
                    }
                }

            } else {
                // normal guess
                boolean isNoOneCanAnswer = true;
                for (IPlayer player : players) {
                    Card answer = player.canAnswer(guess, currentPlayer);
                    if (answer != null) {
                        currentPlayer.receiveInfo(player, answer);
                        isNoOneCanAnswer = false;
                    }
                }

                if (isNoOneCanAnswer) {
                    currentPlayer.receiveInfo(null, null);
                }
            }

            turn = (turn + 1) % players.size();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void shuffleCardsToPlayer() {
        Collections.shuffle(allCards);
        for (int i = 0; i < allCards.size(); i++) {
            players.get(i % players.size()).setCard(allCards.get(i));
        }
    }
}
