package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Location;
import be.howest.ti.alhambra.logic.game.Market;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionalityTest {
    AlhambraController controller = new AlhambraController();

    @Test
    void takeMoneyTest(){

        List<Coin> coins = new ArrayList<>();

        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");
        Coin firstCoin = game.getBank().getCoinsOnBoard().get(0);

        coins.add(firstCoin);
        Player currentPlayer = game.getCurrentPlayer();

        int bagSize = currentPlayer.getMoney().getCoinsInBag().size();
        controller.takeMoney(currentPlayer.getName(), "group01-0", coins);
        assertEquals(bagSize+1 , currentPlayer.getMoney().getCoinsInBag().size());

        Coin fakeCoin = new Coin(Currency.YELLOW, 99);
        coins.add(fakeCoin);

        assertThrows(AlhambraGameRuleException.class, () -> controller.takeMoney("danny", "group01-0", coins));
        assertThrows(AlhambraGameRuleException.class, () -> controller.takeMoney("John", "group01-0", coins));
    }

    @Test
    void buyBuilding(){

        Coin coin1 = new Coin(Currency.BLUE, 5);
        Coin coin2 = new Coin(Currency.YELLOW, 5);
        Coin coin3 = new Coin(Currency.BLUE, 5);
        Coin coin4 = new Coin(Currency.GREEN, 4);
        Coin coin5 = new Coin(Currency.BLUE, 2);

        List<Coin> selectedCoins = new LinkedList<>();
        List<Coin> allCoins = new LinkedList<>();

        allCoins.add(coin1);
        allCoins.add(coin2);
        allCoins.add(coin3);
        allCoins.add(coin4);
        allCoins.add(coin5);

        Queue<Building> allBuildings = new LinkedList<>();

        Building building1 = new Building(BuildingType.ARCADES, 4, new Walls(true,false,true,false));
        Building building2 = new Building(BuildingType.PAVILION, 2, new Walls(true,false,true,false));
        Building building3 = new Building(BuildingType.TOWER, 4, new Walls(true,false,true,false));
        Building building4 = new Building(BuildingType.PAVILION, 3, new Walls(true,false,true,false));

        allBuildings.add(building1);
        allBuildings.add(building2);
        allBuildings.add(building3);
        allBuildings.add(building4);

        Market market = new Market(allBuildings);
        market.addBuildingsToBoard();

        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");

        Player player = game.getCurrentPlayer();
        player.getMoney().addCoins(allCoins);
        selectedCoins.add(coin1);

        assertTrue(player.getBuildingsInHand().isEmpty());


        player.getMoney().addSelectedCoins(selectedCoins);
        assertEquals(selectedCoins, player.getMoney().getSelectedCoins());

        market.buyBuilding(player, Currency.BLUE, player.getMoney().getSelectedCoins());

        assertEquals(1, player.getBuildingsInHand().size());
    }

    @Test
    void placeBuilding(){

        AlhambraController controller = new AlhambraController();

        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");

        Player player = game.getCurrentPlayer();

        Location location = new Location(0,-1);

        Walls walls = new Walls(true, false, true, false);
        Building building = new Building(BuildingType.PAVILION, 1, walls);

        assertNull(player.getCity().getBoard()[3][2]);
        controller.placeBuilding(game.getGameID(), player.getName(), building, location);
        assertNotNull(player.getCity().getBoard()[3][2]);

    }

    @Test
    void placeBuildingInReserve(){
        AlhambraController controller = new AlhambraController();

        controller.initializeLobby();

        controller.joinLobby("group01-0", "john");
        controller.joinLobby("group01-0", "danny");
        controller.setReady("john","group01-0");
        controller.setReady("danny","group01-0");

        Game game = controller.getOngoingGames().get("group01-0");

        Player player = game.getCurrentPlayer();

        Location location = null;

        Walls walls = new Walls(true, false, true, false);
        Building building = new Building(BuildingType.PAVILION, 1, walls);

        controller.placeBuilding(game.getGameID(), player.getName(), building, location);
        assertEquals(1, player.getReserve().size());

        game.changeCurrentPlayer();


    }

}
