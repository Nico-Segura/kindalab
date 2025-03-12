package com.kindalab.elevatorsystem.model;

public class Keycard {
    private String cardId;
    private boolean valid;

    public Keycard(String cardId, boolean valid) {
        this.cardId = cardId;
        this.valid = valid;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardId() {
        return cardId;
    }

    // Validates the card itaself, could be expiration date for example.
    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean isValid){
        this.valid = isValid;
    }
}