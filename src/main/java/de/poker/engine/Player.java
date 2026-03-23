package de.poker.engine;

import java.util.UUID;

public class Player {

    private final UUID id;
    private int chips;
    private final Card[] cards = new Card[2];
    private boolean folded = false;

    public Player(UUID id, int chips) {
        this.id = id;
        this.chips = chips;
    }


}
