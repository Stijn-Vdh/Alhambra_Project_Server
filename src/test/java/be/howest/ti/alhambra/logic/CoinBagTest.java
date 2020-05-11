package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.CoinBag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CoinBagTest {

        Coin coin1 = new Coin(Currency.YELLOW, 5);
        Coin coin2 = new Coin(Currency.ORANGE, 8);
        Coin coin3 = new Coin(Currency.BLUE, 7);
        Coin coin4 = new Coin(Currency.YELLOW, 10);
        CoinBag bag = new CoinBag();

    void init(){
        bag.addCoin(coin1);
        bag.addCoin(coin2);
        bag.addCoin(coin3);
        bag.addCoin(coin4);
    }


    @Test
    void totalValue(){

        assertEquals(0, bag.computeTotalCoinsValue());
        init();
        assertEquals(30, bag.computeTotalCoinsValue());
        bag.removeCoin(coin4);
        assertEquals(20, bag.computeTotalCoinsValue());
        bag.addCoin(coin4);
        assertEquals(30, bag.computeTotalCoinsValue());

    }

    @Test
    void computeSelectedCoins(){
        init();

        bag.addSelectedCoin(coin1);
        bag.addSelectedCoin(coin4);
        assertEquals(15, bag.computeSelectedCoinsValue());

        bag.addSelectedCoin(coin2);
        assertThrows(AlhambraGameRuleException.class, () -> bag.computeSelectedCoinsValue());

        bag.removeSelectedCoin(coin2);
        assertEquals(15, bag.computeSelectedCoinsValue());
    }

    @Test
    void validateBagSize(){
        assertEquals(0, bag.getCoins().size());
        init();
        assertEquals(4, bag.getCoins().size());

        bag.addSelectedCoin(coin1);
        bag.addSelectedCoin(coin4);
        assertEquals(2, bag.getSelectedCoins().size());

        bag.removeSelectedCoinsFromBag();
        assertEquals(2, bag.getCoins().size());

   }


}
