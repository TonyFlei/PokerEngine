package de.poker.engine.service;

import de.poker.api.dto.PlayerUpdate;
import de.poker.api.exceptions.TableFullException;
import de.poker.api.exceptions.TableNotFoundException;
import de.poker.engine.data.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TableServiceTest {
    
    TableService service;
    
    @BeforeEach
    void init() {
        GameService gameServiceMock = Mockito.mock(GameService.class);
        
        this.service = new TableService(gameServiceMock);
    }

    @Test
    void registerNewPlayerNoTable(){
        PlayerUpdate testee = service.registerPlayer(null);

        List<Player> players = service.getTableById(testee.table()).getPlayers();
        assertThat(players).hasSize(1);
        assertThat(testee.table()).isNotBlank();
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerOneTable(){
        PlayerUpdate reference = service.registerPlayer(null);

        PlayerUpdate testee = service.registerPlayer(null);

        List<Player> players = service.getTableById(testee.table()).getPlayers();
        assertThat(players).hasSize(2);
        assertThat(testee.table()).isEqualTo(reference.table());
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerFullTable(){
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        PlayerUpdate reference = service.registerPlayer(null);

        List<Player> playersReference = service.getTableById(reference.table()).getPlayers();
        assertThat(playersReference).hasSize(6);

        PlayerUpdate testee = service.registerPlayer(null);

        List<Player> players = service.getTableById(testee.table()).getPlayers();
        assertThat(players).hasSize(1);
        assertThat(testee.table()).isNotBlank();
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerTwoFullTable(){
        //first Table
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);

        //second Table
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);

        //should be on thirdTable
        PlayerUpdate testee = service.registerPlayer(null);

        List<Player> players = service.getTableById(testee.table()).getPlayers();
        assertThat(players).hasSize(1);
        assertThat(testee.table()).isNotBlank();
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerOnDedicatedTable(){
        //first Table
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        var reference = service.registerPlayer(null);

        //second Table
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);
        service.registerPlayer(null);

        //should be on firstTable
        PlayerUpdate testee = service.registerPlayer(reference.table());

        List<Player> players = service.getTableById(testee.table()).getPlayers();
        assertThat(players).hasSize(7);
        assertThat(testee.table()).isEqualTo(reference.table());
        assertThat(testee.newPlayer()).isNotBlank();
    }

    @Test
    void registerNewPlayerOnDedicatedTableNotFound(){

        assertThatThrownBy(() -> service.registerPlayer("notATable")).isInstanceOf(TableNotFoundException.class).hasMessage("Table with id: notATable does not exist");
    }

    @Test
    void registerNewPlayerOnDedicatedTableFullTable(){

        var reference = service.registerPlayer(null);

        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());
        service.registerPlayer(reference.table());

        assertThatThrownBy(() -> service.registerPlayer(reference.table())).isInstanceOf(TableFullException.class).hasMessage("Table with id: " + reference.table() +" is full");
    }
}