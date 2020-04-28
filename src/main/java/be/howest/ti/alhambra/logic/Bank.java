package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Bank {

    private Queue<Coin> coinsInBank;
    private List<Coin> coinsOnBoard = new LinkedList<>();

    public Bank(Queue<Coin> allCoins) {
        this.coinsInBank = allCoins;
        fillBoardWithInitialCoins();
    }

    public List<Coin> getCoinsOnBoard() {
        return coinsOnBoard;
    }

    public void fillBoardWithInitialCoins(){
        for (int i = 0; i < 4; i++){
            coinsOnBoard.add(coinsInBank.poll());
        }

    }

    public void refillBank() {

        while (coinsOnBoard.size() < 4){
            coinsOnBoard.add(coinsInBank.poll());
        }

    }


    public void takeCoins(List<Coin> selectedCoins){

        int valueCoins = totalValueCoins((selectedCoins));

        if (selectedCoins.size() == 1){
            removeSelectedCoins(selectedCoins);
            refillBank();
        }else {
            if (validTotalValue(valueCoins)){
                removeSelectedCoins(selectedCoins);
                refillBank();
            }else{
                throw new AlhambraGameRuleException("Max amount is 5!");
            }

        }


    }

    private void removeSelectedCoins(List<Coin> selectedCoins) {

        for (Coin selectedCoin: selectedCoins){
            for (Coin coinOnBoard: coinsOnBoard){
                if (selectedCoin.equals(coinOnBoard)){
                    coinsOnBoard.remove(selectedCoin);
                    break;
                }
            }
        }
        refillBank();
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
