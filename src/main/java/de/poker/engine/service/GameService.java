package de.poker.engine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private DeckService deckService;

    @Autowired
    public GameService(DeckService deckService) {
        this.deckService = deckService;
    }
}
