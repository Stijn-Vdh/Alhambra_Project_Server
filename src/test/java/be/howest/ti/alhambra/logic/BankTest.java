package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Bank;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {

    @Test
    void Bank() {

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

        List<Coin> selectedCoins = new LinkedList<>();
        Queue<Coin> allCoins = new LinkedList<>();

        assertEquals(0, allCoins.size());

        allCoins.add(coin1);
        allCoins.add(coin2);
        allCoins.add(coin3);
        allCoins.add(coin4);
        allCoins.add(coin5);
        allCoins.add(coin6);
        allCoins.add(coin7);
        allCoins.add(coin8);
        allCoins.add(coin9);
        allCoins.add(coin10);

        assertEquals(10, allCoins.size());

        Bank bank = new Bank(allCoins);
        bank.addCoinsToBoard();
        assertEquals(6, allCoins.size());

        assertEquals(0, bank.totalValueCoins(selectedCoins));
        selectedCoins.add(coin1);
        selectedCoins.add(coin2);
        assertEquals(3, bank.totalValueCoins(selectedCoins));

        assertTrue(bank.isValidTotalValue(bank.totalValueCoins(selectedCoins)));
        selectedCoins.add(coin4);
        assertFalse(bank.isValidTotalValue(bank.totalValueCoins(selectedCoins)));
        assertEquals(11, bank.totalValueCoins(selectedCoins));

        assertThrows(AlhambraGameRuleException.class, () -> bank.takeCoins(selectedCoins));

        selectedCoins.remove(coin4);
        assertEquals(3, bank.totalValueCoins(selectedCoins));

        bank.takeCoins(selectedCoins);
        assertEquals(4, allCoins.size());
    }

    @Test
    void startingMoney() {

        Queue<Coin> allCoins = new LinkedList<>();

        Coin coin1 = new Coin(Currency.BLUE, 1);
        Coin coin2 = new Coin(Currency.YELLOW, 2);
        Coin coin3 = new Coin(Currency.BLUE, 5);
        Coin coin4 = new Coin(Currency.GREEN, 8);
        Coin coin5 = new Coin(Currency.BLUE, 5);
        Coin coin6 = new Coin(Currency.BLUE, 10);
        Coin coin7 = new Coin(Currency.BLUE, 8);
        Coin coin8 = new Coin(Currency.GREEN, 3);
        Coin coin9 = new Coin(Currency.YELLOW, 9);
        Coin coin10 = new Coin(Currency.GREEN, 4);

        allCoins.add(coin1);
        allCoins.add(coin2);
        allCoins.add(coin3);
        allCoins.add(coin4);
        allCoins.add(coin5);
        allCoins.add(coin6);
        allCoins.add(coin7);
        allCoins.add(coin8);
        allCoins.add(coin9);
        allCoins.add(coin10);

        Bank bank = new Bank(allCoins);

        Player p1 = new Player("daniel");
        Player p2 = new Player("stijn");
        assertEquals(0, p1.getMoney().getCoinsInBag().size());
        assertEquals(0, p2.getMoney().getCoinsInBag().size());

        List<Coin> startingCoinsP1 = bank.dealStartingCoins(20);
        p1.getMoney().addCoins(startingCoinsP1);

        assertEquals(5, p1.getMoney().getCoinsInBag().size());
        assertEquals(5, allCoins.size());

        List<Coin> startingCoinsP2 = bank.dealStartingCoins(20);
        p2.getMoney().addCoins(startingCoinsP2);
        assertEquals(3, p2.getMoney().getCoinsInBag().size());
        assertEquals(2, allCoins.size());

    }
}
