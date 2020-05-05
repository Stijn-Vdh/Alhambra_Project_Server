package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    @Test
    void coin() {
        // Create a coin ...
        Coin coin = new Coin(Currency.ORANGE, 10);

        // Turn it into a JsonObject
        JsonObject coinAsJsonObject = JsonObject.mapFrom(coin);

        // Assert that this object has the expected properties
        assertTrue(coinAsJsonObject.containsKey("currency"));
        assertTrue(coinAsJsonObject.containsKey("amount"));

        // Assert that you can convert it back to the same coin.
        assertEquals(coin, coinAsJsonObject.mapTo(Coin.class));

        // Assert that you can go back and forth between Java-objects and Json (strings)
        assertEquals(coin, Json.decodeValue(Json.encode(coin), Coin.class));
    }

    static List<Coin> allCoins() {
        return Stream.of(Currency.values())
                .flatMap(currency -> IntStream.rangeClosed(1, 9).mapToObj(value -> new Coin(currency, value)))
                .flatMap(coin -> Stream.of(coin, coin, coin))
                .collect(Collectors.toList());
    }

    @Test

    void Bank(){

        Coin coin1 = new Coin(Currency.BLUE, 1);
        Coin coin2 = new Coin(Currency.YELLOW, 2);
        Coin coin3 = new Coin(Currency.BLUE, 1);
        Coin coin4 = new Coin(Currency.GREEN, 8);
        Coin coin5 = new Coin(Currency.BLUE, 1);
        Coin coin6 = new Coin(Currency.BLUE, 3);
        Coin coin7 = new Coin(Currency.BLUE, 5);
        Coin coin8 = new Coin(Currency.GREEN, 6);
        Coin coin9 = new Coin(Currency.YELLOW, 7);
        Coin coin10 = new Coin(Currency.GREEN, 1);

       Queue<Coin> coinsInBank = new LinkedList<>();
        assertEquals(0, coinsInBank.size());

       coinsInBank.add(coin1);
       coinsInBank.add(coin2);
       coinsInBank.add(coin3);
       coinsInBank.add(coin4);
       coinsInBank.add(coin5);
       coinsInBank.add(coin6);
       coinsInBank.add(coin7);
       coinsInBank.add(coin8);
       coinsInBank.add(coin9);
       coinsInBank.add(coin10);

       assertEquals(10, coinsInBank.size());

       Bank bank = new Bank(coinsInBank);

        assertEquals(6, coinsInBank.size());

       List<Coin> selectedCoins = new LinkedList<>();

       assertEquals(0, bank.totalValueCoins(selectedCoins));
       selectedCoins.add(coin1);
       selectedCoins.add(coin2);
       assertEquals(3, bank.totalValueCoins(selectedCoins));
       selectedCoins.add(coin4);
        assertEquals(11, bank.totalValueCoins(selectedCoins));
       assertThrows(AlhambraGameRuleException.class, () -> bank.takeCoins(selectedCoins));
       selectedCoins.remove(coin4);
        assertEquals(3, bank.totalValueCoins(selectedCoins));
       bank.takeCoins(selectedCoins);
       assertEquals(4, coinsInBank.size());

    }

    @Test
    void building() {

        Walls walls = new Walls(true, false, true, false);

        Building building = new Building(BuildingType.PAVILION, 1, walls);

        JsonObject buildingAsJsonObject = JsonObject.mapFrom(building);

        assertTrue(buildingAsJsonObject.containsKey("type"));
        assertTrue(buildingAsJsonObject.containsKey("cost"));
        assertTrue(buildingAsJsonObject.containsKey("walls"));

        assertEquals(building, buildingAsJsonObject.mapTo(Building.class));

        assertEquals(building, Json.decodeValue(Json.encode(building), Building.class));
    }

    @Test
    void lobby() {
        String gameID;
        Lobby lobby = new Lobby("group01-1");
            gameID = lobby.getGameID();
            lobby.addPlayer("stav");
            //Assert gameID creation
            assertEquals("group01-1", gameID);
            //Assert get the name of a player
            assertEquals("stav", lobby.getPlayers().stream().findFirst().get().getName());
            //Assert checkReadyAmount
            assertEquals(0, lobby.getReadyAmount());
            // add a player to the lobby
            lobby.addPlayer("john");
            //Assert checkReadyAmount
            lobby.setPlayerReadyState(lobby.getPlayers().stream().findFirst().get());
            assertEquals(1, lobby.getReadyAmount());
            //Assert checkReadyState to start the alhambra
            assertEquals("waiting for players to ready up", lobby.checkReadyStateForStartGame() );

    }

    @Test
    void LobbyJson(){
        Lobby lobby = new Lobby("group01-2");

        JsonObject lobbyAsJsonObject = JsonObject.mapFrom(lobby);

        assertTrue(lobbyAsJsonObject.containsKey("gameID"));

         assertEquals(lobby, lobbyAsJsonObject.mapTo(Lobby.class));

         assertEquals(lobby, Json.decodeValue(Json.encode(lobby), Lobby.class));
    }     

    void market()
    {
        List<Coin> coins = new LinkedList<>();
        Queue<Building> buildings = new LinkedList<>();
        Walls walls = new Walls(true, false, true, false);

        for (int i = 0; i < 6; i++){
            buildings.add(new Building(BuildingType.PAVILION, i, walls));
        }

        for (int i = 0; i < 6; i++) {
            coins.add(new Coin(Currency.GREEN, i + 2));
        }

        Market market = new Market(buildings);
        assertEquals(4,market.getBuildingsOnBoard().size());

        market.buyBuilding(Currency.GREEN, coins);
        assertEquals(3,market.getBuildingsOnBoard().size());

        market.fillBuildingToBoard();
        assertEquals(4,market.getBuildingsOnBoard().size());

        assertThrows(AlhambraGameRuleException.class, ()-> market.buyBuilding(Currency.BLUE, coins));

        List<Coin> coins2 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            coins.add(new Coin(Currency.GREEN, i));
        }
        assertThrows(AlhambraGameRuleException.class, ()-> market.buyBuilding(Currency.GREEN, coins2));
    }

    @Test
    void createGame(){

        AlhambraController controller = new AlhambraController();
        assertEquals("group01-1", controller.createGame());
        assertEquals("group01-2", controller.createGame());

    }



}