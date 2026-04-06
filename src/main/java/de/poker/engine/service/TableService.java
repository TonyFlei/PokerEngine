package de.poker.engine.service;

import de.poker.api.dto.PlayerUpdate;
import de.poker.engine.data.Player;
import de.poker.engine.data.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TableService.class);
    private Table table;

    public PlayerUpdate registerPlayer() {
        if (table == null) {
            LOGGER.info("Creating a new Table");

            table = new Table();
        }

        List<String> currentPlayers = table.getPlayers().stream().map(Player::getId).toList();

        Player player = table.registerPlayer();

        return new PlayerUpdate(player.getId(), currentPlayers);
    }
}
