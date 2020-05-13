package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.CoinBag;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CoinBagTest {

        List<Coin> coins = new LinkedList<>();
        Coin coin1 = new Coin(Currency.YELLOW, 5);
        Coin coin2 = new Coin(Currency.ORANGE, 8);
        Coin coin3 = new Coin(Currency.YELLOW, 5);
        Coin coin4 = new Coin(Currency.YELLOW, 10);
        CoinBag bag = new CoinBag();

    void init(){
        coins.add(coin1);
        coins.add(coin2);
        coins.add(coin3);
        coins.add(coin4);
        bag.addCoins(coins);
    }

    @Test
    void addCoin() {
        assertEquals(0, bag.getCoinsInBag().size());
        init();
        assertEquals(4, bag.getCoinsInBag().size());
    }

    @Test
    void removeCoin() {
        init();
        assertEquals(4, bag.getCoinsInBag().size());
        bag.removeCoin(coin1);
        bag.removeCoin(coin2);
        assertEquals(2, bag.getCoinsInBag().size());
    }

    @Test
    void checkValidSelection(){

        List<Coin> selectedCoins = new LinkedList<>();

        selectedCoins.add(coin1);
        selectedCoins.add(coin4);
        bag.addSelectedCoins(selectedCoins);
        assertEquals(15, bag.computeSelectedCoinsValue());

        selectedCoins.clear();
        selectedCoins.add(coin2);
        bag.addSelectedCoins(selectedCoins);
        assertThrows(AlhambraGameRuleException.class, () -> bag.computeSelectedCoinsValue());

        bag.removeSelectedCoin(coin2);
        assertEquals(15, bag.computeSelectedCoinsValue());
    }

    @Test
    void validateBagSize(){
        List<Coin> selectedCoins = new LinkedList<>();

        assertEquals(0, bag.getCoinsInBag().size());
        init();
        assertEquals(4, bag.getCoinsInBag().size());

        selectedCoins.add(coin1);
        selectedCoins.add(coin4);
        bag.addSelectedCoins(selectedCoins);
        assertEquals(2, bag.getSelectedCoins().size());

        bag.removeSelectedCoinsFromBag();
        assertEquals(2, bag.getCoinsInBag().size());

   }


}
