package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void setReadyState() {
        AlhambraController controller = new AlhambraController();
        controller.initializeGame();
        controller.joinGame("group01-1", "john");

        assertFalse(controller.getPlayers().get(0).isReady());

        controller.setReadyState("john");

        assertTrue(controller.getPlayers().get(0).isReady());

        controller.setReadyState("john");

        assertFalse(controller.getPlayers().get(0).isReady());
    }
}
