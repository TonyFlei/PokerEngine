package de.poker.api;

import de.poker.api.dto.PlayerUpdate;
import de.poker.engine.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(ApiPaths.API)
public class TableController {

    private static final Logger LOG = LoggerFactory.getLogger(TableController.class);
    private final TableService tableService;

    public TableController(TableService tableService) {
        this.tableService = tableService;
    }

    @PostMapping("/players")
    public PlayerUpdate registerPlayer(
            @RequestParam(required = false) String table
    ) {
        LOG.info("Registering a new Player");

        PlayerUpdate update = tableService.registerPlayer(table);

        LOG.info("A new Player with the id: {} has been registered", update.newPlayer());

        return update;
    }

    @PostMapping("/start/{tableId}")
    public ResponseEntity<String> startGameAtTable(
            @PathVariable String tableId
    ) {
        LOG.info("Trying to start the game at Table: {}", tableId);

        boolean started = tableService.startGameAtTable(tableId);

        if (started) {
            return ResponseEntity.ok("Game started successfully");
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Game could not be started");
    }
}
