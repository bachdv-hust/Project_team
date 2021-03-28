package com.company;

import java.util.Objects;

public class Card {
    enum CardType {
        WEAPON, SUSPECT, LOCATION
    }
    private final CardType type;
    private final String value;

    public Card(CardType type, String value) {
        this.type = type;
        this.value = value;
    }

    public CardType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return type == card.type && value.equals(card.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, value);
    }
}