package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.*;

public class Market {
    private Queue<Building> buildings;
    private Map<Currency,Building> buildingsOnBoard = new HashMap<>();

    public Market(Queue<Building> buildings) {
        this.buildings = buildings;
        fillBuildingToBoard();
    }

    public Map<Currency, Building> getBuildingsOnBoard() {
        return buildingsOnBoard;
    }

    private void takeBuilding(Currency currency){

        buildingsOnBoard.put(currency,null);
    }

    public void buyBuilding(Currency currency, List<Coin> coins){
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
        takeBuilding(currency);
    }

    public void fillBuildingToBoard(){

        if (buildingsOnBoard.isEmpty()) {
            for (Currency currency : Currency.values()){
                buildingsOnBoard.put(currency, buildings.poll());
            }
        }else{
            for (Currency currency : buildingsOnBoard.keySet()){
                if (buildingsOnBoard.get(currency) == null){
                    buildingsOnBoard.put(currency, buildings.poll());
                }
            }
        }
    }

    public int getAmountOfBuildings(){
        return buildings.size();
    }


}