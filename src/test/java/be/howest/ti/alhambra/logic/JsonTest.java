package be.howest.ti.alhambra.logic;

import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

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
}