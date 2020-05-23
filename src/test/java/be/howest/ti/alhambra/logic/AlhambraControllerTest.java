package be.howest.ti.alhambra.logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlhambraControllerTest {
    @Test
    void clearServerTest(){
        AlhambraController controller = new AlhambraController();
        assertTrue(controller.getLobbies().isEmpty());
        assertTrue(controller.getOngoingGames().isEmpty());
        assertTrue(controller.getPlayers().isEmpty());

        controller.initializeLobby();
        assertEquals(1, controller.getLobbies().size());

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        assertTrue(controller.getLobbies().isEmpty());
        assertEquals(1, controller.getOngoingGames().size());

        controller.initializeLobby();
        controller.joinLobby("group01-1", "kim");

        assertEquals(3, controller.getPlayers().size());

        controller.clearServer();
        assertTrue(controller.getLobbies().isEmpty());
        assertTrue(controller.getOngoingGames().isEmpty());
        assertTrue(controller.getPlayers().isEmpty());
    }

    @Test
    void removeGameIfEmptyTest(){
        AlhambraController controller = new AlhambraController();
        controller.initializeLobby();
        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        assertEquals(1, controller.getOngoingGames().size());
        controller.leaveGame("group01-0", "john");
        controller.leaveGame("group01-0", "danny");
        assertTrue(controller.getOngoingGames().isEmpty());
}
}
