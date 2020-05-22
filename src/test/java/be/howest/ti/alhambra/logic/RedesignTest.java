package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
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


        Location location = new Location(0,1);
        Walls walls = new Walls(true, false, true, false);
        Building building = new Building(BuildingType.PAVILION, 1, walls);
        assertNull(player.getCity().getBoard()[3][4]);
        controller.placeBuilding(game.getGameID(), player.getName(), building, location);
        assertNotNull(player.getCity().getBoard()[3][4]);

        assertEquals(1, player.getBuildingTypesInCity().get(building.getType()));

        game.changeCurrentPlayer();


        Building building1 = null;

        assertThrows(AlhambraGameRuleException.class, () -> controller.redesignCity(game.getGameID(), player.getName(),building1, new Location(0,0)));
        assertNotNull(player.getCity().getBoard()[3][3]);


        controller.redesignCity(game.getGameID(), player.getName(), building1, location);
        assertNull(player.getCity().getBoard()[3][4]);
        assertFalse(player.getReserve().isEmpty());

        game.changeCurrentPlayer();

        controller.redesignCity(game.getGameID(), player.getName(), building, location);
        System.out.println(player.getCity().getBoard()[3][4]);
        assertNotNull(player.getCity().getBoard()[3][4]);
        assertTrue(player.getReserve().isEmpty());

    }
}
