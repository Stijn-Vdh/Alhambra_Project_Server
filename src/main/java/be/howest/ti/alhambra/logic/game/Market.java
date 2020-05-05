package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.*;

public class Market {
    private Queue<Building> buildings;
    private Map<be.howest.ti.alhambra.logic.money.Currency,Building> buildingsOnBoard = new HashMap<>();
    private be.howest.ti.alhambra.logic.money.Currency lastBoughtCurrency;

    public Market(Queue<Building> buildings) {
        this.buildings = buildings;
        fillBuildingToBoard();
    }

    public Map<be.howest.ti.alhambra.logic.money.Currency, Building> getBuildingsOnBoard() {
        return buildingsOnBoard;
    }

    private void takeBuilding( be.howest.ti.alhambra.logic.money.Currency currency){

        buildingsOnBoard.remove(currency);
    }

    // verplaatsen naar player class???????????????

    public void buyBuilding(be.howest.ti.alhambra.logic.money.Currency currency, List<Coin> coins){
        int givenCoinAmount = 0;

        for(Coin coin : coins){
            if (coin.getCurrency() != currency){
                throw new AlhambraGameRuleException("This is against the rules");
            }
            givenCoinAmount += coin.getAmount();
        }

        if (givenCoinAmount < buildingsOnBoard.get(currency).getCost()){
            throw new AlhambraGameRuleException("This is against the rules");
        }
        lastBoughtCurrency = currency;
        takeBuilding(currency);
    }

    public void fillBuildingToBoard(){
        if (buildingsOnBoard.size() == 3) {
            buildingsOnBoard.put(lastBoughtCurrency, buildings.poll());
        } else {
            for (be.howest.ti.alhambra.logic.money.Currency currency : Currency.values()) {
                buildingsOnBoard.put(currency, buildings.poll());
            }
        }
    }

    public int getAmountOfBuildings(){
        return buildings.size();
    }


}
