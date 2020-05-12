package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void setReadyState() {
        AlhambraController controller = new AlhambraController();
        controller.initializeLobby();
        controller.joinGame("group01-0", "john");

        assertFalse(controller.getPlayers().get(0).isReady());

        controller.setReadyState("john","group01-0");

        assertTrue(controller.getPlayers().get(0).isReady());

        controller.setReadyState("john","group01-0");

        assertFalse(controller.getPlayers().get(0).isReady());
    }
}
