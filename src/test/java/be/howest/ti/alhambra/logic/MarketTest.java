package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Market;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class MarketTest {

    @Test
    void market()
    {
        List<Coin> coins = new LinkedList<>();
        Queue<Building> buildings = new LinkedList<>();
        Walls walls = new Walls(true, false, true, false);
        Player player = new Player("john");

        for (int i = 0; i < 6; i++){
            buildings.add(new Building(BuildingType.PAVILION, i, walls));
        }

        for (int i = 0; i < 6; i++) {
            coins.add(new Coin(Currency.GREEN, i + 2));
        }

        Market market = new Market(buildings);
        assertEquals(4,market.getBuildingsOnBoard().size());

        market.buyBuilding(player,Currency.GREEN, coins);
        assertNull(market.getBuildingsOnBoard().get(Currency.GREEN));
        assertFalse(player.getBuildingsInHand().isEmpty());

        market.fillBuildingToBoard();
        assertEquals(4,market.getBuildingsOnBoard().size());

        coins.add(new Coin(Currency.BLUE, 5));
        coins.add(new Coin(Currency.BLUE, 4));
        coins.add(new Coin(Currency.BLUE, 4));

        assertThrows(AlhambraGameRuleException.class, ()-> market.buyBuilding(player, Currency.BLUE, coins));

        List<Coin> coins2 = new LinkedList<>();
        for (int i = 0; i < 2; i++) {
            coins.add(new Coin(Currency.GREEN, i));
        }
        assertThrows(AlhambraGameRuleException.class, ()-> market.buyBuilding(player, Currency.GREEN, coins2));
    }
}
