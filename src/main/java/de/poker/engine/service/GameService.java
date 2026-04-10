package de.poker.engine.service;

import de.poker.engine.data.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
public class GameService {

    private static final Logger LOG = LoggerFactory.getLogger(GameService.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);

    @Autowired
    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    public boolean startGame(Table table) {
        LOG.info("Starting a new game on Table: {}", table.getId());

        //ToDo - ready checks oder so?

        ScheduledFuture<?> future = scheduler.scheduleAtFixedRate(() -> {
            //ToDo - game stoppen
            System.out.println("gameRunning");

            runGameLoop(table);

        }, 0, 1000, TimeUnit.MILLISECONDS);

        return true;
    }

    private void runGameLoop(Table table) {
        //All players which will play this round. Meanwhile, other Players can join the table.
        table.setPlayersForGame(table.getPlayers());

        table.preFlop();

    }
}
