package de.poker.api.dto;

public record PlayerJoinedEvent(String type, String player) {

    public PlayerJoinedEvent(String player) {
        this("Player_Joined", player);
    }
}
