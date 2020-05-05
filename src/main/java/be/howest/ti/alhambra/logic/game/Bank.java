package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bank {
    private int coinAmount = 4;

    private Queue<Coin> coinsInBank;
    private List<Coin> coinsOnBoard = new LinkedList<>();

    public Bank(Queue<Coin> allCoins) {
        this.coinsInBank = allCoins;
        fillBoardWithInitialCoins();
    }

    public List<Coin> getCoinsOnBoard() {
        return coinsOnBoard;
    }

    public void fillBoardWithInitialCoins() {
        for (int i = 0; i < coinAmount; i++) {
            coinsOnBoard.add(coinsInBank.poll());
        }

    }

    public void refillBank() {
        while (coinsOnBoard.size() < coinAmount) {
            coinsOnBoard.add(coinsInBank.poll());
        }

    }

    public void takeCoins(List<Coin> selectedCoins) {

        int valueCoins = totalValueCoins((selectedCoins));

        if (validTotalValue(valueCoins)) {
            removeSelectedCoins(selectedCoins);
            refillBank();
        } else {
            throw new AlhambraGameRuleException("Max amount is 5!");
        }

    }

    private void removeSelectedCoins(List<Coin> selectedCoins) {

        for (int i = 0; i < selectedCoins.size(); i++) {
            coinsOnBoard.remove(selectedCoins.get(i));
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
        int maxCoinValue = 5;
        int minCoinValue = 0;
        return totalValue <= maxCoinValue && totalValue > minCoinValue;
    }

    public String coinsToString(Coin[] coins) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < coinAmount; i++) {
            res.append(coins[i]);
        }
        return res.toString();
    }
}
