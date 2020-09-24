package banking.model;

import banking.LuhnAlgorithm;

public class Card {
    private String cardNumber;
    private String pin;

    public Card(String cardNumber, String pin) {
        this.cardNumber = cardNumber;
        this.pin = pin;
    }


    public String getPin() {
        return pin;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public String toString() {
        return "Card number: " + cardNumber + System.lineSeparator()
            + "Pin: " + pin;
    }
}
