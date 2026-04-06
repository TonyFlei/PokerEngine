package de.poker.engine.service;

import de.poker.engine.data.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GameService {

    private static final Logger LOG = LoggerFactory.getLogger(GameService.class);
    private final SimpMessagingTemplate messagingTemplate;
    private final ExecutorService executor = Executors.newCachedThreadPool();

    @Autowired
    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }


    public void startGame(Table table) {
        executor.submit(() -> runGameLoop(table));
    }

    private void runGameLoop(Table table) {

        LOG.info("Starting a new game on Table: {}", table.getId());

        //As long as at least 2 Players seat on the Table the game loop will continue.
        while (table.getPlayers().size() > 1) {
            try {
                Thread.sleep(100);
                System.out.println("gameRunning");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            table.initGame();

            //ToDo notify people

        }

        LOG.info("Stoping game on Table: {}", table.getId());

        table.stopGame();
    }
}
