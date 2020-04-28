package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

import java.util.*;

public class Market {
    private Queue<Building> buildings = new LinkedList<>();
    private Map<Currency,Building> buildingsOnBoard = new HashMap<Currency, Building>();

    public Market(Queue<Building> buildings) {
        this.buildings = buildings;

    }

    public Map<Currency, Building> getBuildingsOnBoard() {
        return buildingsOnBoard;
    }

    public Building takeBuilding(Building building){

        player.hand += building;

        return null;

    }
    public void buyBuilding(Building building, List<Coin> coins){
        Currency currency = coins.get(0).getCurrency();
        int givenCoinAmount = 0;

        for(Coin coin : coins){
            if (coin.getCurrency() != currency){
                throw new AlhambraGameRuleException("This is against the rules");
            }
            givenCoinAmount += coin.getAmount();
        }

        if (givenCoinAmount < building.getAmount()){
            throw new AlhambraGameRuleException("This is against the rules");
        }


    }

    public void fillBuildingToBoard(){
       for (Currency currency : Currency.values()){
           buildingsOnBoard.put(currency,buildings.poll());
       }
    }

    public int getAmountOfBuildings(){
        return buildings.size();
    }
}
