package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Location;
import be.howest.ti.alhambra.logic.player.Player;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BuildingTest {
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
    void equals() {
        Walls walls = new Walls(true, false, true, false);
        Building testBuilding =  new Building(BuildingType.TOWER, 2, walls);
        assertEquals(new Building(BuildingType.TOWER, 2, walls), testBuilding);
        assertNotEquals(new Building(BuildingType.TOWER, 3, walls), testBuilding);
    }
    @Test
    void hashCodeTest() {
        Walls walls = new Walls(true, false, true, false);
        Building testBuilding1 = new Building(BuildingType.TOWER, 2, walls);
        Building testBuilding2 = new Building(BuildingType.TOWER, 2, walls);
        Building testBuilding3 = new Building(BuildingType.SERAGLIO, 2, walls);
        assertNotSame(testBuilding1, testBuilding2);
        assertNotSame(testBuilding1, testBuilding3);
        assertEquals(testBuilding1.hashCode(), testBuilding2.hashCode());
        assertNotEquals(testBuilding3.hashCode(), testBuilding1.hashCode());
    }
}
