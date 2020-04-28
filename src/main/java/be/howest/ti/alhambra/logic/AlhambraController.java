package be.howest.ti.alhambra.logic;

import java.util.LinkedList;
import java.util.List;

public class AlhambraController {
    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public BuildingType[] getBuildingTypes() {return  BuildingType.values();}

    public int getTotalAmount(Coin[] coins) {
        int totalAmount = 0;
        for(Coin coin: coins){
            totalAmount += coin.getAmount();
        }
        return totalAmount;
    }

    public List<Building> getAllBuildings() {
        return BuildingRepo.getAllBuildings();
    }
}
