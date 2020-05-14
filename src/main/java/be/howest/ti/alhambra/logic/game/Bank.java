package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Bank {
    private static final int COINS_ON_BOARD_LIMIT = 4;
    private static final int MAX_COIN_VALUE = 5;
    private static final int MIN_COIN_VALUE = 0;
    private Queue<Coin> allCoins = new LinkedList<>();
    private List<Coin> coinsOnBoard = new LinkedList<>();

    public Bank() {

        List<Coin> coinsToShuffle = generateAllCoins();
        Collections.shuffle(coinsToShuffle);

        this.allCoins.addAll(coinsToShuffle);

    }
    // for testing
    public Bank(Queue<Coin> allCoins){
        this.allCoins = allCoins;
    }

    public static List<Coin> generateAllCoins() {

        return Stream.of(Currency.values())
                .flatMap(currency -> IntStream.rangeClosed(1, 9).mapToObj(value -> new Coin(currency, value)))
                .flatMap(coin -> Stream.of(coin, coin, coin))
                .collect(Collectors.toList());
    }

    public List<Coin> getCoinsOnBoard() {
        return coinsOnBoard;
    }

    public Queue<Coin> getAllCoins() {
        return allCoins;
    }

    public void addCoinsToBoard() {
        while (coinsOnBoard.size() < COINS_ON_BOARD_LIMIT) {
            coinsOnBoard.add(allCoins.poll());
        }
    }

    private boolean areValidCoins(List<Coin> selectedCoins){
        for (Coin coin: selectedCoins){
            if (!coinsOnBoard.contains(coin)){
                return false;
            }
        }
        return true;
    }

    public void takeCoins(List<Coin> selectedCoins) {

        if (!areValidCoins(selectedCoins)){
            throw new AlhambraGameRuleException("These coins are not on board!");
        }

        int valueCoins = totalValueCoins((selectedCoins));

        if (selectedCoins.size() == 1 || isValidTotalValue(valueCoins)) {
            removeSelectedCoins(selectedCoins);
            addCoinsToBoard();
        } else {
            throw new AlhambraGameRuleException("Max amount is 5!");
        }

    }

    private void removeSelectedCoins(List<Coin> selectedCoins) {

        for (Coin selectedCoin : selectedCoins) {
            coinsOnBoard.remove(selectedCoin);
        }
    }

    public int totalValueCoins(List<Coin> coins) {

        int totalValue = 0;
        for (Coin coin : coins) {
            totalValue += coin.getAmount();
        }
        return totalValue;
    }

    public boolean isValidTotalValue(int totalValue) {

        return totalValue <= MAX_COIN_VALUE && totalValue > MIN_COIN_VALUE;
    }

    public List<Coin> dealStartingCoins(){
        List<Coin> startingCoins = new ArrayList<>();

        while (totalValueCoins(startingCoins) < 20){
            startingCoins.add(allCoins.poll());
        }

        return startingCoins;
    }
}
