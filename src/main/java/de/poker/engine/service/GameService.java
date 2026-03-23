package de.poker.engine.service;

import de.poker.engine.data.GameState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private GameState gameState;
    private DeckService deckService;

    @Autowired
    public GameService(GameState gameState, DeckService deckService) {
        this.gameState = gameState;
        this.deckService = deckService;
    }
}
