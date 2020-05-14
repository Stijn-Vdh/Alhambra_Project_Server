package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;

import java.util.*;

public class Market {
    private Queue<Building> buildings = new LinkedList<>();
    private Map<Currency,Building> buildingsOnBoard = new HashMap<>();

    public Market() {
        List<Building> buildingsToShuffle = new ArrayList<>(BuildingRepo.getAllBuildings());
        Collections.shuffle(buildingsToShuffle);

        this.buildings.addAll(buildingsToShuffle);
        fillBuildingToBoard();
    }
    // for testing
    public Market(Queue<Building> buildings){
        this.buildings = buildings;
        fillBuildingToBoard();
    }

    public Map<Currency, Building> getBuildingsOnBoard() {
        return buildingsOnBoard;
    }

    //will be used to inform client on how much buildings are left
    public int getAmountOfBuildings(){
        return buildings.size();
    }

    private void removeBuildingFromBoard(Currency currency){
        buildingsOnBoard.put(currency,null);
    }

    public void buyBuilding(Player player, Currency currency, List<Coin> coins){
        player.getBag().addSelectedCoins(coins);
        int givenCoinAmount = player.getBag().computeSelectedCoinsValue();

        if (givenCoinAmount < buildingsOnBoard.get(currency).getCost()){
            throw new AlhambraGameRuleException("Not enough coins!");
        }

        //TODO implement else if for paying with the exact amount

        player.putBuildingInHand(buildingsOnBoard.get(currency));
        removeBuildingFromBoard(currency);
    }

    public void fillBuildingToBoard(){

        for (Currency currency : Currency.values()){
            buildingsOnBoard.computeIfAbsent(currency, k -> buildings.poll());
        }
    }
}
