package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import com.company.Card.CardType;

public class Model {
    private final ArrayList<Card> suspects = new ArrayList<>();
    private final ArrayList<Card> locations = new ArrayList<>();
    private final ArrayList<Card> weapons = new ArrayList<>();
    private final ArrayList<IPlayer> players = new ArrayList<>();
    private final ArrayList<Card> allCards = new ArrayList<>();
    private final ArrayList<Card> result = new ArrayList<>();

    public Model(ArrayList<Card> allCards, ArrayList<IPlayer> players) {
        this.players.addAll(players);
        this.allCards.addAll(allCards);
        divideCardType(allCards);
    }

    private void divideCardType(ArrayList<Card> allCards) {
        for (Card card : allCards) {
            switch (card.getType()) {
                case SUSPECT:
                    suspects.add(card);
                    break;
                case WEAPON:
                    weapons.add(card);
                    break;
                case LOCATION:
                    locations.add(card);
                    break;
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

        int currentPlayerIndex = 0;
        int turnCountNum = 0;
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setUp(players.size(), i, suspects, locations, weapons);
        }
        shuffleCardsToPlayer();
        while (true) {
            IPlayer currentPlayer = players.get(currentPlayerIndex);
            if (currentPlayer instanceof HumanPlayer) {
                if (((HumanPlayer) currentPlayer).isGameOver()) {
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    continue;
                }
            } else if (currentPlayer instanceof ComputerPlayer) {
                if (((ComputerPlayer) currentPlayer).isGameOver()) {
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
                    continue;
                }
            }

            turnCountNum++;
            System.out.println("------- Turn " + turnCountNum + " ---------");

            System.out.println("Here are the names of all the suspects:");
            System.out.println(suspects);
            System.out.println("Here are the names of all the locations:");
            System.out.println(locations);
            System.out.println("Here are the names of all the weapons:");
            System.out.println(weapons);

            if (currentPlayer instanceof HumanPlayer) {
                System.out.println("It's your turn");
            } else {
                System.out.println("Current turn: player " + currentPlayerIndex);
            }
            Guess guess = currentPlayer.getGuess();
            if (guess.getListCards().isEmpty()){
                break;
            }

            if (guess.isAccusation()) {
                boolean isEqual = true;
                for (Card card : guess.getListCards()) {
                    if (!result.contains(card)) {
                        isEqual = false;
                        break;
                    }
                }

                if (isEqual) {
                    System.out.println("Player " + currentPlayer.getIndex() + " won the game.");
                    break;
                } else {
                    System.out.println("Player " + currentPlayer.getIndex() + " made a bad accusation and was removed from the game.");
                    if (currentPlayer instanceof HumanPlayer) {
                        ((HumanPlayer) currentPlayer).setGameOver(true);
                    } else if (currentPlayer instanceof ComputerPlayer) {
                        ((ComputerPlayer) currentPlayer).setGameOver(true);
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

            currentPlayerIndex = (currentPlayerIndex + 1) % players.size();


            try {
                Thread.sleep(1000);
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
