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
     * Creates a new Player Object and seats the Player.
     * If a tableId is given and there is no Table with this id or the Table is full an Exception will be raised.
     * @param tableId tableId of the Table you want to join, if there is no tableId given you get seated on a Table, that is not full.
     * @return Information about the newly joined Player, the current Players on the Table and the Table.
     */
    public PlayerUpdate registerPlayer(String tableId) {
        Table table = getTable(tableId);

        PlayerUpdate update = seatPlayer(table);

        gameService.handlePlayerJoined(update);

        return update;
    }

    private Table getTable(String tableId) {
        Table table;

        if (tableId == null) {
            table = findTable();
        } else {
            table = tables.stream()
                    .filter(t -> t.getId().equals(tableId))
                    .findFirst()
                    .orElseThrow(() -> new TableNotFoundException(tableId));

            if (table.getPlayers().size() >= 10) {
                throw new TableFullException(tableId);
            }
        }
        return table;
    }

    private static @NonNull PlayerUpdate seatPlayer(Table table) {
        List<String> currentPlayers = table.getPlayers().stream().map(Player::getId).toList();

        Player player = table.registerPlayer();

        LOG.info("A new Player with the id: {} got seated on a Table with the id: {}", player.getId(), table.getId());

        return new PlayerUpdate(table.getId(), player.getId(), currentPlayers);
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
