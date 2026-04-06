package de.poker.engine.data;

import org.springframework.stereotype.Component;

@Component
public class GameState {
    private GameStatus status;

    public GameState() {
        this.status = GameStatus.WAITING;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }
}
