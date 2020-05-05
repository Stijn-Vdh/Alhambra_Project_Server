package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.game.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    void createGame(){

        AlhambraController controller = new AlhambraController();
        Game game1 = new Game(0);
        Game game2 = new Game(1);
        assertEquals(game1, controller.initializeGame());
        assertEquals(game2, controller.initializeGame());

    }
}
