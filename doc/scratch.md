# 🧠 Poker Engine – Architektur & Konzept (Texas Hold'em)

## 1. Grundannahmen

- Texas Hold’em ist **stateful**
- Die Engine muss daher **Spielzustand im RAM halten**
- Fokus (MVP):
    - keine DB notwendig
    - einfache, schnelle Verarbeitung
- Spieler können sein:
    - Bots
    - echte User

---

## 2. Architektur-Überblick

### 🔹 Core Engine (Stateful)
- hält den kompletten Spielzustand im Speicher
- verarbeitet alle Aktionen
- steuert den Spielablauf (Dealer-Logik)

---

### 🔹 API Layer

#### REST
- Spiel erstellen
- Spieler registrieren

#### WebSocket
- Echtzeitkommunikation
- bidirektional:
    - Engine → Spieler (Events)
    - Spieler → Engine (Aktionen)

---

### 🔹 Clients
- Bots (automatisiert)
- UI (optional später)

---

## 3. Kommunikationsmodell

### 🔁 Event-getrieben

Spieler reagieren auf Events wie:

- neue Runde startet
- Spieler hat geraist
- eigene Aktion ist gefragt

---

### 📡 WebSocket

**Warum?**
- Echtzeit notwendig
- kein Polling
- Server pusht Events aktiv

---

### Beispiel Eventfluss

```text
Engine → "ROUND_STARTED"
Engine → "PLAYER_X_RAISED"
Engine → "YOUR_TURN"

Bot → "CALL"
Bot → "RAISE 50"
```

## 4. Domain

### Player
- id: String
- chips: int
- holeCards: List<Card>
- folded: boolean
- allIn: boolean

### Card
- rank: Rank
- suit: Suit

### Deck
- cards: List<Card>

-shuffle()
-draw(): Card

### GameState
- players: List<Player>
- communityCards: List<Card>
- pot: int
- currentBet: int
- dealerPosition: int
- currentPlayerIndex: int
- phase: Phase

### Action
- playerId: String
- type: ActionType
- amount: int (optional)

### ActionType
- FOLD
- CALL
- RAISE
- CHECK

### Phase
- PREFLOP
- FLOP
- TURN
- RIVER
- SHOWDOWN