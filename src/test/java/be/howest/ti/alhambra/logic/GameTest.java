package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameTest {
    AlhambraController controller = new AlhambraController();

    @Test
    void createGame(){
        Game game1 = new Game(1);
        Game game2 = new Game(2);
        assertEquals(game1.getGameID(), controller.initializeGame());
        assertEquals(game2.getGameID(), controller.initializeGame());

    }

    @Test
    void joinGame() {
        controller.initializeGame();
        Game firstGame = controller.getGames().get(0);
        assertEquals(0, firstGame.getPlayers().size());
        controller.joinGame("group01-1", "john");
        assertEquals(1, firstGame.getPlayers().size());

        assertThrows(AlhambraEntityNotFoundException.class, () -> controller.joinGame("group01-5159", "john"));
    }
}
