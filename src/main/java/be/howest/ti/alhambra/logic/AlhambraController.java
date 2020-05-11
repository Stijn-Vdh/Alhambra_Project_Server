package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;

import java.util.*;

public class AlhambraController {

    private static List<Game> games = new LinkedList<>();
    private static List<Player> players = new LinkedList<>();

    public String initializeGame() {
        int counter = games.size();
        Game game = new Game(counter);
        games.add(game);
        return game.toString();
    }

    public List<String> getGameIds() {
        List<String> tempList = new LinkedList<>();

        for (Game game : games) {
            tempList.add(game.toString());
        }
        return tempList;
    }

    public List<Game> getGames() {
        return games;
    }

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

    public int getTotalAmount(Coin[] coins) {
        int totalAmount = 0;
        for (Coin coin : coins) {
            totalAmount += coin.getAmount();
        }
        return totalAmount;
    }

    public List<Building> getAllBuildings() {
        return BuildingRepo.getAllBuildings();
    }

    public void clearAllGames() {
        games.clear();
    }

    public boolean setReadyState(String name) {
        Player player = searchPlayer(name);

       if (player != null){
               player.setReady(player.isReady());
           return true;
       }
       return false;
    }

    private Player searchPlayer(String name) {
            for (Player player : players) {
                if (player.getName().equals(name)) {
                    return player;
                }
        }
        return null;
    }

    public String joinGame(String gameID, String name) {
        Player player = new Player(name);
        players.add(player);
        for (Game game : games) {
            if (game.getGameID().equals(gameID)) {
                game.getPlayers().add(player);
                return gameID + '+' + name;
            }
        }
        throw new AlhambraEntityNotFoundException("This game does not exist.");
    }
}
