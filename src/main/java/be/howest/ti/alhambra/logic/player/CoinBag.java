package be.howest.ti.alhambra.logic.player;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.ArrayList;
import java.util.List;

public class CoinBag {

    private List<Coin> coinsInBag = new ArrayList<>();
    private List<Coin> selectedCoins = new ArrayList<>();

    public List<Coin> getCoinsInBag() {
        return coinsInBag;
    }

    public List<Coin> getSelectedCoins() {
        return selectedCoins;
    }

    public void addCoins(List<Coin> coins){ this.coinsInBag.addAll(coins);}

    public void removeCoin(Coin coin){
        coinsInBag.remove(coin);
    }

    public void addSelectedCoins(List<Coin> coins) {
        selectedCoins.addAll(coins);
    }
    //for testing purposes
    public void removeSelectedCoin(Coin coin){
        selectedCoins.remove(coin);
    }

    public void removeSelectedCoinsFromBag(){
        for (Coin coin: selectedCoins){
            coinsInBag.remove(coin);
        }
    }

    public int computeSelectedCoinsValue(){
        Currency color = selectedCoins.get(0).getCurrency();
        int selectedCoinsValue = 0;

        for (Coin coin: selectedCoins){
            if (!(coin.getCurrency().equals(color))){
                throw new AlhambraGameRuleException("Different currencies are not allowed");
            }else{
                selectedCoinsValue += coin.getAmount();
            }
        }

        return selectedCoinsValue;
    }










}
