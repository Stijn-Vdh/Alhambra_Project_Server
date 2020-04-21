package be.howest.ti.alhambra.logic;

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
}
