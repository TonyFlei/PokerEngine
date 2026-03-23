package de.poker.engine;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class DeckService {

    private final List<Card> deck;
    private final Random random;

    public DeckService(Random random) {
        this.deck = initDeck();
        this.random = random;
    }

    public DeckService() {
        this(new Random());
    }

    private List<Card> initDeck() {
        List<Card> cards = new ArrayList<>();

        for (Suit suit : Suit.values()){
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }

        return cards;
    }

    public Card takeRandom(){
        int randomIndex = random.nextInt(deck.size());

        return deck.remove(randomIndex);
    }
}
