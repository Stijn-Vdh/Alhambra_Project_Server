package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    @Test
    void createGame(){

        AlhambraController controller = new AlhambraController();
        assertEquals("group01-1", controller.createGame());
        assertEquals("group01-2", controller.createGame());

    }
}
