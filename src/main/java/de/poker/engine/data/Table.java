package de.poker.engine.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private static final Logger LOG = LoggerFactory.getLogger(Table.class);

    private List<Player> players;
    private GameState gameState;

    public Table() {
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
}
