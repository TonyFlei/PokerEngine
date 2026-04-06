package de.poker.engine.data;

import de.poker.engine.service.DeckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Table {
    private static final Logger LOG = LoggerFactory.getLogger(Table.class);

    private final String id;
    private final List<Player> players;
    private List<Player> playersForGame = new ArrayList<>();
    private final GameState gameState;
    private final DeckService deckService;

    public Table() {
        this.id = UUID.randomUUID().toString();
        this.players = new ArrayList<>();
        this.gameState = new GameState();
        this.deckService = new DeckService();
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

    public void setPlayersForGame(List<Player> playersForGame) {
        this.playersForGame = playersForGame;
    }

    public void preFlop() {
        gameState.setStatus(GameStatus.PRE_FLOP);

        for (Player p : playersForGame) {

        }

        //ToDo Status anpassen, Karten austeilen, Spieler List blocken usw. Wichtig: Wer hat Big Blind, wer Bekommt die erste Karte usw.
    }

    public void stopGame() {
        gameState.setStatus(GameStatus.WAITING);
    }
}
