package com.kindalab.elevatorsystem.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KeycardTest {

    @Test
    void testKeycardValidity() {
        Keycard keycard = new Keycard("abc123", true);
        assertTrue(keycard.isValid(), "Keycard should be valid when created with valid=true");

        keycard.setValid(false);
        assertFalse(keycard.isValid(), "Keycard should be invalid after setting valid to false");
    }

    @Test
    void testGetCardId() {
        Keycard keycard = new Keycard("testKeyCard01", true);
        assertEquals("testKeyCard01", keycard.getCardId(), "Keycard ID should match the value passed in constructor");
    }
}
