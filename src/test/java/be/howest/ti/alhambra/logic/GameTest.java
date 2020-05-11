package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Lobby;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {
    AlhambraController controller = new AlhambraController();

    @Test
    void joinGame() {
        controller.initializeLobby();
        Lobby firstGame = controller.getLobbies().get(0);
        assertEquals(0, firstGame.getPlayers().size());
        controller.joinGame("group01-0", "john");
        assertEquals(1, firstGame.getPlayers().size());

        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.joinGame("group01-5159", "john"));
    }
}
