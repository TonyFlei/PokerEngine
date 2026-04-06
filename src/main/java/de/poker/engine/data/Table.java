package de.poker.engine.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Table {
    private static final Logger LOG = LoggerFactory.getLogger(Table.class);

    private String id;
    private List<Player> players;
    private GameState gameState;

    public Table() {
        this.id = UUID.randomUUID().toString();
        this.players = new ArrayList<>();
        this.gameState = new GameState();
    }

    public Player registerPlayer() {
        Player player = new Player();

        players.add(player);

        LOG.info("Added a new player to players with the id: {}", player.getId());

        return player;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public String getId() {
        return id;
    }

    public boolean checkAndStartGame() {
        //When game is already running return false
        if (!gameState.getStatus().equals(GameStatus.WAITING)) return false;

        startGame();

        return true;
    }

    private void startGame() {
        //ToDo Status anpassen, Karten austeilen, Spieler List blocken usw.
    }
}
