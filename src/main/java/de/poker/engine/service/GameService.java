package de.poker.engine.service;

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

    public void notifyPlayerJoined(String playerId) {
        messagingTemplate.convertAndSend("/topic/playerJoined", playerId);
    }
}
