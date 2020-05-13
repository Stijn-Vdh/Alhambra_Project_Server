package be.howest.ti.alhambra.logic;

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
        String gameID;
        Lobby lobby = new Lobby("group01-1");
        gameID = lobby.getGameID();
        lobby.addPlayer(new Player("stav"));
        //Assert gameID creation
        assertEquals("group01-1", gameID);
        //Assert get the name of a player
        assertEquals("stav", lobby.getPlayers().stream().findFirst().get().getName());
        //Assert checkReadyAmount
        assertEquals(0, lobby.getReadyAmount());
        // add a player to the lobby
        lobby.addPlayer(new Player("john"));
        //Assert checkReadyState to start the alhambra
        assertFalse(lobby.checkReadyStateForStartGame());
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
