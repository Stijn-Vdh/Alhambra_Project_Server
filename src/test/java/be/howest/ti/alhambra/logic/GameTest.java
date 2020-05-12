package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Lobby;
import be.howest.ti.alhambra.logic.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    AlhambraController controller = new AlhambraController();

    @Test
    void joinGame() {
        controller.initializeLobby();
        Lobby firstGame = controller.getLobbies().get("group01-0");
        assertEquals(0, firstGame.getPlayers().size());
        controller.joinGame("group01-0", "john");
        assertEquals(1, firstGame.getPlayers().size());

        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.joinGame("group01-5159", "john"));
    }

    @Test
    void startGame(){
        controller.initializeLobby();

        controller.joinGame("group01-0", "john");
        controller.joinGame("group01-0", "danny");

        controller.setReadyState("john","group01-0");
        assertEquals(1, controller.getLobbies().size());

        controller.setReadyState("danny","group01-0");

        assertEquals(0, controller.getLobbies().size());
        assertEquals(1, controller.getOngoingGames().size());
    }

    @Test
    void receiveStartingMoneyOnGameStart(){
        List<Player> players = new ArrayList<>();
        Player denJohn = new Player("John");
        Player denEddy = new Player("Eddy");
        
        players.add(denJohn);
        players.add(denEddy);

        Game game = new Game(players, "group01-0");

        assertNotNull(denJohn.getBag());
        assertFalse(denJohn.getBag().computeTotalCoinsValue() < 20);
        assertFalse(denJohn.getBag().computeTotalCoinsValue() > 28);

        assertNotNull(denEddy.getBag());
        assertFalse(denEddy.getBag().computeTotalCoinsValue() < 20);
        assertFalse(denEddy.getBag().computeTotalCoinsValue() > 28);
    }

}
