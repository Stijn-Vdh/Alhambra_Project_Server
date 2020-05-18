package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void setReadyState() {
        AlhambraController controller = new AlhambraController();
        controller.initializeLobby();
        controller.joinLobby("group01-0", "john");

        assertFalse(controller.getPlayers().get(0).isReady());

        controller.setReady("john","group01-0");

        assertTrue(controller.getPlayers().get(0).isReady());

        controller.setNotReady("john", "group01-0");

        assertFalse(controller.getPlayers().get(0).isReady());
    }
}
