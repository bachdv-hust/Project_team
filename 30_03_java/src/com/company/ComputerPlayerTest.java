package com.company;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComputerPlayerTest {
    ComputerPlayer computerPlayer;
    HumanPlayer humanPlayer;
    ArrayList<Card> originalCards;
    ArrayList<Card> susCards;
    ArrayList<Card> locCards;
    ArrayList<Card> weaCards;

    {
        originalCards = new ArrayList<>();
        susCards = new ArrayList<>();
        locCards = new ArrayList<>();
        weaCards = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            susCards.add(new Card(Card.CardType.SUSPECT, "sus " + i));
        }

        for (int i = 0; i < 8; i++) {
            locCards.add(new Card(Card.CardType.LOCATION, "loc " + i));
        }

        for (int i = 0; i < 8; i++) {
            weaCards.add(new Card(Card.CardType.WEAPON, "wea " + i));
        }
        originalCards.addAll(susCards);
        originalCards.addAll(locCards);
        originalCards.addAll(weaCards);
        computerPlayer = new ComputerPlayer();
        humanPlayer = new HumanPlayer();
    }

    @Test
    void canAnswerCase1() {
        Card actual = computerPlayer.canAnswer(new Guess(true, new ArrayList<>()), humanPlayer);
        assertNull(actual);
    }

    @Test
    void canAnswerCase2() {
        List<Card> cards = new ArrayList<>();
        Card checkedCard = new Card(Card.CardType.SUSPECT, "sus 0");
        cards.add(checkedCard);
        cards.add(new Card(Card.CardType.SUSPECT, "sus 1"));
        cards.add(new Card(Card.CardType.SUSPECT, "sus 2"));
        computerPlayer.setCard(checkedCard);
        Card actual = computerPlayer.canAnswer(new Guess(true, cards), humanPlayer);
        assertEquals(checkedCard, actual);
    }

    @Test
    void canAnswerCase3() {
        List<Card> cards = new ArrayList<>();
        Card checkedCard1 = new Card(Card.CardType.SUSPECT, "sus 0");
        Card checkedCard2 = new Card(Card.CardType.SUSPECT, "sus 1");
        cards.add(checkedCard1);
        cards.add(checkedCard2);
        cards.add(new Card(Card.CardType.SUSPECT, "sus 2"));
        computerPlayer.setCard(checkedCard1);
        computerPlayer.setCard(checkedCard2);
        Card actual = computerPlayer.canAnswer(new Guess(true, cards), humanPlayer);
        assertTrue(cards.contains(actual));
    }

    @Test
    void getGuessCase1() {
        Card checkedCard1 = new Card(Card.CardType.SUSPECT, "sus 0");
        Card checkedCard2 = new Card(Card.CardType.LOCATION, "loc 0");
        Card checkedCard3 = new Card(Card.CardType.WEAPON, "wae 0");

        computerPlayer.setUp(2, 0, susCards, locCards, weaCards);
        computerPlayer.setCard(checkedCard1);
        computerPlayer.setCard(checkedCard2);
        computerPlayer.setCard(checkedCard3);

        List<Card> cardsGuess = computerPlayer.getGuess().getListCards();
        assertTrue(!cardsGuess.contains(checkedCard1)
                && !cardsGuess.contains(checkedCard2)
                && !cardsGuess.contains(checkedCard3));
    }

    @Test
    void getGuessCase2() {
        Card checkedCard1 = new Card(Card.CardType.SUSPECT, "sus 0");
        Card checkedCard2 = new Card(Card.CardType.LOCATION, "loc 0");
        Card checkedCard3 = new Card(Card.CardType.WEAPON, "wae 0");

        susCards = new ArrayList<>();
        susCards.add(checkedCard1);

        locCards = new ArrayList<>();
        locCards.add(checkedCard2);

        weaCards = new ArrayList<>();
        weaCards.add(checkedCard3);

        computerPlayer.setUp(2, 0, susCards, locCards, weaCards);

        List<Card> cardsGuess = computerPlayer.getGuess().getListCards();
        assertTrue(cardsGuess.contains(checkedCard1)
                && cardsGuess.contains(checkedCard2)
                && cardsGuess.contains(checkedCard3));
    }


    @Test
    void getGuessCase3() {
        Card checkedCard1 = new Card(Card.CardType.SUSPECT, "sus 0");
        Card checkedCard1_2 = new Card(Card.CardType.SUSPECT, "sus 1");
        Card checkedCard2 = new Card(Card.CardType.LOCATION, "loc 0");
        Card checkedCard3 = new Card(Card.CardType.WEAPON, "wae 0");


        susCards = new ArrayList<>();
        susCards.add(checkedCard1);
        susCards.add(checkedCard1_2);

        locCards = new ArrayList<>();
        locCards.add(checkedCard2);

        weaCards = new ArrayList<>();
        weaCards.add(checkedCard3);

        computerPlayer.setUp(2, 0, susCards, locCards, weaCards);
        computerPlayer.receiveInfo(humanPlayer, checkedCard1_2);

        List<Card> cardsGuess = computerPlayer.getGuess().getListCards();
        assertTrue(cardsGuess.contains(checkedCard1)
                && cardsGuess.contains(checkedCard2)
                && cardsGuess.contains(checkedCard3));
    }

    @Test
    void humanCanAnswerCase1() {
        List<Card> cards = new ArrayList<>();
        Card checkedCard1 = new Card(Card.CardType.SUSPECT, "sus 0");
        Card checkedCard2 = new Card(Card.CardType.LOCATION, "loc 0");
        Card checkedCard3 = new Card(Card.CardType.WEAPON, "wae 0");
        cards.add(checkedCard1);
        cards.add(checkedCard2);
        cards.add(checkedCard3);

        humanPlayer.setCard(checkedCard1);
        humanPlayer.setCard(checkedCard2);
        humanPlayer.setCard(checkedCard3);

        Card actual = humanPlayer.canAnswerTest(new Guess(false, cards), computerPlayer);

        assertTrue(cards.contains(actual));
    }
}