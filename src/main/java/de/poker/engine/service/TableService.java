package de.poker.engine.service;

import de.poker.api.dto.PlayerUpdate;
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
    private final List<Table> tables = new ArrayList<>();

    public PlayerUpdate registerPlayer() {

        return registerPlayer(null);
    }

    public PlayerUpdate registerPlayer(String tableId) {

        Table table = tableId != null ? tables.stream().filter(t -> t.getId().equals(tableId)).findFirst().orElseThrow(() -> new TableNotFoundException(tableId)) :
                findTable();

        return seatPlayer(table);
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
