package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bank {
    private static final int BANK_LIMIT = 4;
    private static final int MAX_COIN_VALUE = 5;
    private static final int MIN_COIN_VALUE = 0;

    private Queue<Coin> allCoins;
    private List<Coin> coinsOnBoard = new LinkedList<>();

    public Bank(Queue<Coin> allCoins) {
        this.allCoins = allCoins;
        refill();
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
}