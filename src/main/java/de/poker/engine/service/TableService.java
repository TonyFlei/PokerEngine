package de.poker.engine.service;

import de.poker.api.dto.PlayerUpdate;
import de.poker.api.exceptions.TableFullException;
import de.poker.api.exceptions.TableNotFoundException;
import de.poker.engine.data.Player;
import de.poker.engine.data.Table;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TableService {

    private static final Logger LOG = LoggerFactory.getLogger(TableService.class);
    private final GameService gameService;
    private final List<Table> tables = new ArrayList<>();

    public TableService(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new player and assigns them to a table.
     *
     * <p>If a {@code tableId} is provided, the player will attempt to join the specified table.
     * If no table with the given ID exists or the table is already full, an exception is thrown.</p>
     *
     * <p>If no {@code tableId} is provided, the player will be seated at the first available table
     * that has free capacity.</p>
     *
     * <p>After successfully seating the player, all other players at the table are notified.
     * If the newly seated player is the second player at the table, the game start is triggered.</p>
     *
     * @param tableId the ID of the table to join; may be {@code null}
     * @return a {@link PlayerUpdate} containing information about the newly joined player,
     *         the current players at the table, and the table state
     *
     * @throws TableNotFoundException if the specified table does not exist
     * @throws TableFullException if the specified table has no available seats
     */
    public PlayerUpdate registerPlayer(String tableId) {
        Table table = getTable(tableId);

        PlayerUpdate update = seatPlayer(table);

        //Only if 1 Player is waiting you have to check if the game can start. The rest will be handled by the game loop.
        if (table.getPlayers().size() == 2) gameService.checkAndStartGame(table);

        return update;
    }

    private Table getTable(String tableId) {
        Table table;

        if (tableId == null) {
            table = findTable();
        } else {
            table = getTableById(tableId);

            if (table.getPlayers().size() >= 10) {
                throw new TableFullException(tableId);
            }
        }
        return table;
    }

    protected @NonNull Table getTableById(String tableId) {
        Table table;
        table = tables.stream()
                .filter(t -> t.getId().equals(tableId))
                .findFirst()
                .orElseThrow(() -> new TableNotFoundException(tableId));
        return table;
    }

    private static @NonNull PlayerUpdate seatPlayer(Table table) {

        Player player = table.registerPlayer();

        LOG.info("A new Player with the id: {} got seated on a Table with the id: {}", player.getId(), table.getId());

        return new PlayerUpdate(table.getId(), player.getId());
    }
    /**
     * Trys to find a Table with less than 6 Player.
     * If the List of Tables is empty it will create a new one.
     * @return the Table
     */
    private Table findTable() {
        if (tables.isEmpty()) {
            return createTable();
        }

        return getTableWithSpace();
    }

    private Table getTableWithSpace() {
        for (Table table : tables) {
            if (table.getPlayers().size() < 6) return table;
        }

        return createTable();
    }

    private @NonNull Table createTable() {
        Table table = new Table();
        tables.add(table);

        LOG.info("Created a new Table with the id: {}", table.getId());
        return table;
    }
}
