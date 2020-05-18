package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Lobby;
import be.howest.ti.alhambra.logic.player.Player;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LobbyTest {
    AlhambraController controller = new AlhambraController();

    @Test
    void createLobby(){
        assertEquals(0, controller.getLobbies().size());
        assertEquals("group01-0", controller.initializeLobby());
        assertEquals("group01-1", controller.initializeLobby());
        assertEquals(2, controller.getLobbies().size());
    }

    @Test
    void lobby() {
        controller.initializeLobby();
        controller.initializeLobby();
        assertEquals(2, controller.getGameIds().size());

        Lobby lobby = controller.getLobbies().get("group01-0");

        controller.joinLobby(lobby.getGameID(), "daniel");
        controller.joinLobby(lobby.getGameID(), "stijn");
        controller.joinLobby(lobby.getGameID(), "robin");
        controller.joinLobby(lobby.getGameID(), "stav");
        controller.joinLobby(lobby.getGameID(), "cirdan");

        assertEquals(5, lobby.getPlayers().size());

        controller.joinLobby(lobby.getGameID(), "kim");
        assertEquals(6, lobby.getPlayers().size());
        assertEquals(0, lobby.getReadyAmount());
        assertEquals(1, controller.getGameIds().size());

        controller.setReady("stijn", lobby.getGameID());
        controller.setReady("cirdan", lobby.getGameID());
        controller.setReady("kim", lobby.getGameID());

        assertEquals(3, lobby.getReadyAmount());

        controller.leaveGame(lobby.getGameID(), "kim");
        assertEquals(2, controller.getGameIds().size());
        assertEquals(2, lobby.getReadyAmount());

        controller.joinLobby(lobby.getGameID(), "kim");
        assertEquals(1, controller.getGameIds().size());
        assertEquals(2, lobby.getReadyAmount());

        assertThrows(AlhambraGameRuleException.class , ()-> controller.joinLobby("group01-0", "kurt"));


    }

    @Test
    void LobbyJson(){
        Lobby lobby = new Lobby("group01-2");

        JsonObject lobbyAsJsonObject = JsonObject.mapFrom(lobby);

        assertTrue(lobbyAsJsonObject.containsKey("gameID"));

        assertEquals(lobby, lobbyAsJsonObject.mapTo(Lobby.class));

        assertEquals(lobby, Json.decodeValue(Json.encode(lobby), Lobby.class));
    }
}
