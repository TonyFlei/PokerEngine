package de.poker.api.dto;

import de.poker.engine.data.Player;

import java.util.List;

public record PlayerUpdate(String newPlayer, List<String> currentPlayers) {
}
