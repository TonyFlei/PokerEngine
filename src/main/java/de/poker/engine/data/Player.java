package de.poker.engine.data;

import java.util.UUID;

public class Player {

    private final String id;
    private int chips;
    private final Card[] cards = new Card[2];
    private boolean folded = false;

    public Player() {
        this.id = UUID.randomUUID().toString();
        this.chips = 0;
    }

    public String getId() {
        return id;
    }
}
