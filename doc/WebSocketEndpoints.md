# WebSocket API Documentation

## Connection

**Endpoint:** http://localhost:8080/bot-connections

---

## Hello Event

Dient zum Testen der WebSocket-Verbindung.

### Send

/ws/hello

### Subscribe (Topic)

/topic/greetings

### Payload

```text
"Hallo"
```
---

## PlayerJoined Event

Wird ausgelöst, wenn ein neuer Spieler dem Spiel beitritt.

### Subscribe (Topic)

/topic/table/{tableId}

### Payload

```json
{
  "type" : "Player_Joined",
  "player" : "playerId"
}
```