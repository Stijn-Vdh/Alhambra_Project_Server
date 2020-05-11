package be.howest.ti.alhambra.logic;


import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.game.City;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class CityTest {

    @Test
    void city(){

        City c = new City();

        assertNull(c.getBoard()[1][1]);
        assertEquals(0, c.getBoard()[0][0].getCost());

        assertTrue(c.hasNeighbours(0,1));
        assertFalse(c.hasNeighbours(1,1));
    }

}
