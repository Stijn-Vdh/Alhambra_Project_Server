package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;

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
    void removeGameIfEmptyTest() {
        AlhambraController controller = new AlhambraController();
        controller.initializeLobby();
        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john", "group01-0");
        controller.setReady("danny", "group01-0");

        assertEquals(1, controller.getOngoingGames().size());
        controller.leaveGame("group01-0", "john");
        controller.leaveGame("group01-0", "danny");
        assertTrue(controller.getOngoingGames().isEmpty());
    }

    @Test
    void getAllBuildingsTest(){
        AlhambraController controller = new AlhambraController();

        assertEquals(BuildingRepo.getAllBuildings(), controller.getAllBuildings());
    }

    @Test
    void getCurrencies(){
        AlhambraController controller = new AlhambraController();

        assertEquals(Currency.BLUE, controller.getCurrencies()[0]);
        assertEquals(Currency.GREEN, controller.getCurrencies()[1]);
        assertEquals(Currency.ORANGE, controller.getCurrencies()[2]);
        assertEquals(Currency.YELLOW, controller.getCurrencies()[3]);
    }

    @Test
    void getBuildingTypes(){
        AlhambraController controller = new AlhambraController();

        assertEquals(BuildingType.PAVILION, controller.getBuildingTypes()[0]);
        assertEquals(BuildingType.SERAGLIO, controller.getBuildingTypes()[1]);
        assertEquals(BuildingType.ARCADES, controller.getBuildingTypes()[2]);
        assertEquals(BuildingType.CHAMBERS, controller.getBuildingTypes()[3]);
        assertEquals(BuildingType.GARDEN, controller.getBuildingTypes()[4]);
        assertEquals(BuildingType.TOWER, controller.getBuildingTypes()[5]);
    }
}
