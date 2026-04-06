package de.poker.engine.service;

import de.poker.api.dto.PlayerJoinedEvent;
import de.poker.api.dto.PlayerUpdate;
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

    private void notifyPlayerJoined(PlayerUpdate update) {

        PlayerJoinedEvent event = new PlayerJoinedEvent(update.newPlayer());

        messagingTemplate.convertAndSend("/topic/table/" + update.table(), event);
    }

    public void handlePlayerJoined(PlayerUpdate update) {
        notifyPlayerJoined(update);
    }
}
