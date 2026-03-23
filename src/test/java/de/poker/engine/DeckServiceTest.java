package de.poker.engine;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class DeckServiceTest {

    @Test
    void shouldDrawSameCardWithSameSeed() {
        Random seededRandom = new Random(42);
        DeckService deck = new DeckService(seededRandom);

        Card card = deck.takeRandom();

        assertThat(card).isEqualTo(new Card(Suit.Hearts, Rank.Ace));
    }

    @Test
    void shouldNotDrawSameCardTwice() {
        DeckService deck = new DeckService();

        Card c1 = deck.takeRandom();
        Card c2 = deck.takeRandom();

        assertThat(c1).isNotEqualTo(c2);
    }

}