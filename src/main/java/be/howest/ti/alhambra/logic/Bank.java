package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import java.util.List;
import java.util.Queue;

public class Bank {

    private Queue<Coin> coinsInBank;
    private Coin[] coins = new Coin[4];

    public Bank(Queue<Coin> allCoins) {
        this.coinsInBank = allCoins;
        fillBoardWithInitialCoins(allCoins);
    }

    public Queue<Coin> getCoinsInBank() {
        return coinsInBank;
    }

    public Coin[] getCoins() {
        return coins;
    }

    public void fillBoardWithInitialCoins(Queue<Coin> allCoins){
        for (int i = 0; i < 4; i++){
            coins[i] = allCoins.poll();
        }
    }

    public void refillBank(Coin[] coins, Queue<Coin> allCoins) {

        for (int i = 0; i < 4; i++){
            if (coins[i] == null){
                coins[i] = allCoins.poll();
            }
        }
    }


    public List<Coin> takeCoins(List<Coin> selectedCoins){

        int valueCoins = totalValueCoins((selectedCoins));

        if (selectedCoins.size() == 1 && valueCoins >= 5){
            return selectedCoins;
        }else {
            if (validTotalValue(valueCoins)){
                return selectedCoins;
            }
            throw new AlhambraGameRuleException("Max amount is 5!");
        }


    }

    public int totalValueCoins(List<Coin> selectedCoins){

        int totalValue = 0;
        for (Coin coin: selectedCoins){
            totalValue += coin.getAmount();
        }
        return totalValue;
    }

    public boolean validTotalValue(int totalValue){
        return totalValue <= 5 && totalValue > 0;
    }



    public String coinsToString(Coin[] coins){
        StringBuilder res = new StringBuilder();
        for (int i = 0 ; i < 4; i++){
            res.append(coins[i]);
        }
        return res.toString();
    }



}
