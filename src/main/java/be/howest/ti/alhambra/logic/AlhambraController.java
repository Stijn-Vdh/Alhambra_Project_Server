package be.howest.ti.alhambra.logic;

import java.util.*;

public class AlhambraController {

    private static Set<Game> games = new HashSet<>();

    public String createGame(){
        int counter = games.size();
        Game game = new Game(counter);
        games.add(game);
        return game.getGameID();
    }

    public Set<Game> getGames() { return games; }

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
