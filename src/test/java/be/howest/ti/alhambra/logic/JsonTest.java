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

       Queue<Coin> coinsInBank = new LinkedList<>(allCoins());
       Coin[] coins = new Coin[4];
       Bank newBank = new Bank(coinsInBank);
       List<Coin> selectedCoins = new LinkedList<>();


       Coin coin1 = new Coin(Currency.BLUE, 5);
       Coin coin2 = new Coin(Currency.GREEN, 2);
       Coin coin3 = new Coin(Currency.ORANGE, 10);
       Coin coin4 = new Coin(Currency.YELLOW, 3);

       assertEquals(0, newBank.totalValueCoins(selectedCoins));
       selectedCoins.add(coin1);
       selectedCoins.add(coin2);
       assertEquals(7, newBank.totalValueCoins(selectedCoins));

       assertThrows(AlhambraGameRuleException.class, () -> newBank.takeCoins(selectedCoins));

       selectedCoins.remove(coin1);
       selectedCoins.add(coin4);

       assertEquals(5, newBank.totalValueCoins(selectedCoins));

       newBank.fillBoardWithInitialCoins(coinsInBank);
       assertEquals(4, coins.length);

       selectedCoins.add(coin3);
       int totalValue = newBank.totalValueCoins(selectedCoins);
       assertFalse(newBank.validTotalValue(totalValue));


    }



}