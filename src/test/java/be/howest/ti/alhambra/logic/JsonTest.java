package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
}