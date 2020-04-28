package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

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



}