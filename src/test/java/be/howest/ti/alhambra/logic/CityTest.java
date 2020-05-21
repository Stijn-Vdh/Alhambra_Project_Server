package be.howest.ti.alhambra.logic;


import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.City;
import be.howest.ti.alhambra.logic.game.Location;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CityTest {

    @Test
    void city(){

        City c = new City();
        Building a = new Building(BuildingType.ARCADES, 2, new Walls(true,false,true,false));
        Building p = new Building(BuildingType.PAVILION, 2, new Walls(true,false,true,false));

        assertNull(c.getBoard()[1][1]);
        assertEquals(0, c.getBoard()[3][3].getCost());

        assertThrows(AlhambraGameRuleException.class, () -> c.placeBuilding(a, new Location(0,0)));

        assertNotNull(c.getAvailableLocations(a.getWalls()));

       c.placeBuilding(a, new Location(0,1));
       assertEquals(BuildingType.ARCADES,c.getBoard()[3][4].getType());
       c.placeBuilding(p, new Location(-1,1));
        assertEquals(BuildingType.PAVILION,c.getBoard()[2][4].getType());

        assertThrows(AlhambraGameRuleException.class, () -> c.placeBuilding(p, new Location(0,1)));

        assertTrue(c.hasNeighbours(3,5));
        assertFalse(c.hasNeighbours(5,4));
    }
}
