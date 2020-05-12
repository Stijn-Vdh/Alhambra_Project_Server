package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Bank {
    private static final int BANK_LIMIT = 4;
    private static final int MAX_COIN_VALUE = 5;
    private static final int MIN_COIN_VALUE = 0;
    private Queue<Coin> allCoins;
    private List<Coin> coinsOnBoard = new LinkedList<>();

    public Bank() {
        assert false;
        List<Coin> coinsToShuffle = generateAllCoins();
        Collections.shuffle(coinsToShuffle);
        this.allCoins.addAll(coinsToShuffle);
    }

    public static List<Coin> generateAllCoins() {

        return Stream.of(Currency.values())
                .flatMap(currency -> IntStream.rangeClosed(1, 9).mapToObj(value -> new Coin(currency, value)))
                .flatMap(coin -> Stream.of(coin, coin, coin))
                .collect(Collectors.toList());
    }

    public Queue<Coin> getAllCoins() {
        return allCoins;
    }

    public List<Coin> getCoinsOnBoard() {
        return coinsOnBoard;
    }

    public void refill() {
        while (coinsOnBoard.size() < BANK_LIMIT) {
            coinsOnBoard.add(allCoins.poll());
        }
    }

    public void takeCoins(List<Coin> selectedCoins) {

        int valueCoins = totalValueCoins((selectedCoins));

        if (validTotalValue(valueCoins)) {
            removeSelectedCoins(selectedCoins);
            refill();
        } else {
            throw new AlhambraGameRuleException("Max amount is 5!");
        }

    }

    private void removeSelectedCoins(List<Coin> selectedCoins) {

        for (Coin selectedCoin : selectedCoins) {
            coinsOnBoard.remove(selectedCoin);
        }
    }

    public int totalValueCoins(List<Coin> selectedCoins) {

        int totalValue = 0;
        for (Coin coin : selectedCoins) {
            totalValue += coin.getAmount();
        }
        return totalValue;
    }

    public boolean validTotalValue(int totalValue) {

        return totalValue <= MAX_COIN_VALUE && totalValue > MIN_COIN_VALUE;
    }

    public String coinsToString(Coin[] coins) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < BANK_LIMIT; i++) {
            res.append(coins[i]);
        }
        return res.toString();
    }

    public List<Coin> dealStartingCoins(){
        List<Coin> startingCoins = new ArrayList<>();

        while (totalValueCoins(startingCoins) < 20){
            startingCoins.add(allCoins.poll());
        }

        return startingCoins;
    }

}