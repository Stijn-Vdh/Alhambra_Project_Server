package be.howest.ti.alhambra.logic.player;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.ArrayList;
import java.util.List;

public class CoinBag {

    private List<Coin> coinsInBag = new ArrayList<>();
    private List<Coin> selectedCoins = new ArrayList<>();

    public void addCoin(Coin coin){
        coinsInBag.add(coin);
    }

    public void addSelectedCoin(Coin coin){
        selectedCoins.add(coin);
    }

    public int computeTotalValue(){
        int totalValue = 0;

        for (Coin coin: coinsInBag){
            totalValue += coin.getAmount();
        }

        return totalValue;
    }
    

    public int computeCurrencyValue(){
        Currency color = selectedCoins.get(0).getCurrency();
        int value = 0;

        for (Coin coin: coinsInBag){
            if (!(coin.getCurrency().equals(color))){
                throw new AlhambraGameRuleException("Different currencies are now allowed");
            }else{
                value += coin.getAmount();
            }
        }

        return value;
    }










}
