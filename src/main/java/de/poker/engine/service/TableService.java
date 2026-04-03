package de.poker.engine.service;

import de.poker.engine.data.Player;
import de.poker.engine.data.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableService.class);
    private Table table;

    public String registerPlayer() {
        if (table == null) {
            LOGGER.info("Creating a new Table");

            table = new Table();
        }

        Player player = table.registerPlayer();

        return player.getId();
    }
}
