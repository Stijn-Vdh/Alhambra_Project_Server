package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Location;
import be.howest.ti.alhambra.logic.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RedesignTest {

    @Test
    void redesignCity(){

        AlhambraController controller = new AlhambraController();

        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");

        Player player = game.getCurrentPlayer();


        Location location = new Location(-1,0);
        Walls walls = new Walls(true, false, true, false);
        Building building = new Building(BuildingType.PAVILION, 1, walls);
        assertNull(player.getCity().getBoard()[2][3]);
        controller.placeBuildingOnBoard(game.getGameID(), player.getName(), building, location);
        assertNotNull(player.getCity().getBoard()[2][3]);



        Player player1 = game.getCurrentPlayer();

        assertEquals(player, player1);

        Building building1 = null;

        controller.redesignCity(game.getGameID(), player1.getName(), building1, location);
        assertNull(player1.getCity().getBoard()[2][3]);
        assertFalse(player1.getReserve().isEmpty());

        game.changeCurrentPlayer();

        controller.redesignCity(game.getGameID(), player1.getName(), building, location);
        assertNotNull(player1.getCity().getBoard()[2][3]);
        assertTrue(player1.getReserve().isEmpty());

    }
}
