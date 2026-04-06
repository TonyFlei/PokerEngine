package de.poker.api;

import de.poker.api.dto.PlayerUpdate;
import de.poker.engine.service.GameService;
import de.poker.engine.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiPaths.API)
public class TableController {

    private static final Logger LOG = LoggerFactory.getLogger(TableController.class);
    private final TableService tableService;
    private final GameService gameService;

    public TableController(TableService tableService, GameService gameService) {
        this.tableService = tableService;
        this.gameService = gameService;
    }

    @PostMapping("/players")
    public PlayerUpdate registerPlayer(
            @RequestParam(required = false) String table
    ) {
        LOG.info("Registering a new Player");

        PlayerUpdate update = tableService.registerPlayer(table);

        gameService.notifyPlayerJoined(update);

        LOG.info("A new Player with the id: {} has been registered", update.newPlayer());

        return update;
    }
}
