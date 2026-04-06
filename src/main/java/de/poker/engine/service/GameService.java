package de.poker.engine.service;

import de.poker.engine.data.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GameService {

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void checkAndStartGame(Table table) {
        //As long as at least 2 Players seat on the Table the game loop will continue.

        //ToDo hier muss man den game loop von der request entkopeln, sonst wird die request nicht abgarbeitet
//        while (table.getPlayers().size() > 1) {
//
//            boolean startGame = table.checkAndStartGame();
//
//            if (startGame) {
//            } //ToDo notify people
//
//        }
    }
}
