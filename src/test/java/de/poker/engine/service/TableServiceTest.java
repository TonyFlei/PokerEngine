package de.poker.engine.service;

import de.poker.api.dto.PlayerUpdate;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TableServiceTest {

    @Test
    void registerNewPlayerNoTable(){
        TableService service = new TableService();
        PlayerUpdate testee = service.registerPlayer();

        assertThat(testee.oldPlayers()).isEmpty();
        assertThat(testee.table()).isNotBlank();
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerOneTable(){
        TableService service = new TableService();
        PlayerUpdate reference = service.registerPlayer();

        PlayerUpdate testee = service.registerPlayer();

        assertThat(testee.oldPlayers()).contains(reference.newPlayer());
        assertThat(testee.table()).isEqualTo(reference.table());
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerFullTable(){
        TableService service = new TableService();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        PlayerUpdate reference = service.registerPlayer();

        assertThat(reference.oldPlayers()).hasSize(5);

        PlayerUpdate testee = service.registerPlayer();

        assertThat(testee.oldPlayers()).isEmpty();
        assertThat(testee.table()).isNotBlank();
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerTwoFullTable(){
        TableService service = new TableService();
        //first Table
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();

        //second Table
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();
        service.registerPlayer();

        //should be on thirdTable
        PlayerUpdate testee = service.registerPlayer();

        assertThat(testee.oldPlayers()).isEmpty();
        assertThat(testee.table()).isNotBlank();
        assertThat(testee.newPlayer()).isNotBlank();
    }

}