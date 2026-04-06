package de.poker.api.dto;

import java.util.List;

public record PlayerUpdate(String table, String newPlayer, List<String> oldPlayers) {
}
