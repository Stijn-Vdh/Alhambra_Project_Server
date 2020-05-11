package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.*;

public class AlhambraController {

    private static List<Game> games = new LinkedList<>();

    public Game initializeGame(){
        int counter = games.size();
        Game game = new Game(counter);
        games.add(game);
        return game;
    }

    public List<String> getGames() {
        List<String> tempList = new LinkedList<>();

        for (Game game: games) {
            tempList.add(game.toString());
        }
        return tempList;
    }

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

    public void clearAllGames(){
        games.clear();
    }

    public String joinGame(String gameID, String name) {
        if (!this.getGameIds.contains(gameID)) {
            throw new AlhambraEntityNotFoundException("this game does not exist.");
        }
    }
}
