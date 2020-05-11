package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Lobby;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;

import java.util.*;

public class AlhambraController {

    private List<Game> ongoingGames = new LinkedList<>();
    private List<Lobby> lobbies = new LinkedList<>();
    private List<Player> players = new LinkedList<>();

    public String initializeLobby() {
        int counter = lobbies.size();
        Lobby lobby = new Lobby("group01-" + counter);
        lobbies.add(lobby);
        return lobby.toString();
    }

    public List<String> getGameIds() {
        List<String> tempList = new LinkedList<>();

        for (Lobby lobby : lobbies) {
            tempList.add(lobby.toString());
        }
        return tempList;
    }

    public List<Game> getOngoingGames() {
        return ongoingGames;
    }

    public List<Lobby> getLobbies() {
        return lobbies;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void clearAllGames() {
        ongoingGames.clear();
        lobbies.clear();
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
        for (Lobby lobby : lobbies) {
            if (lobby.getGameID().equals(gameID)) {
                lobby.addPlayer(player);
                return gameID + '+' + name;
            }
        }
        throw new AlhambraEntityNotFoundException("This game does not exist.");
    }


}
