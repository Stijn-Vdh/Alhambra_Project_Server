package be.howest.ti.alhambra.logic.player;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.ArrayList;
import java.util.List;

public class CoinBag {

    private List<Coin> coins = new ArrayList<>();
    private List<Coin> selectedCoins = new ArrayList<>();


    public void addCoin(Coin coin){
        coins.add(coin);
    }

    public void addSelectedCoin(Coin coin){
        selectedCoins.add(coin);
    }

    public void removeSelectedCoin(Coin coin){
        selectedCoins.remove(coin);
    }

    public void removeSelectedCoinsFromBag(){
        for (Coin coin: selectedCoins){
            coins.remove(coin);
        }
    }

    public int computeTotalCoinsValue(){
        int totalValue = 0;

        for (Coin coin: coins){
            totalValue += coin.getAmount();
        }

        return totalValue;
    }

    public int computeSelectedCoinsValue(){
        Currency color = selectedCoins.get(0).getCurrency();
        int selectedCoinsValue = 0;

        for (Coin coin: coins){
            if (!(coin.getCurrency().equals(color))){
                throw new AlhambraGameRuleException("Different currencies are not allowed");
            }else{
                selectedCoinsValue += coin.getAmount();
            }
        }

        return selectedCoinsValue;
    }










}
