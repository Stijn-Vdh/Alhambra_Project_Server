package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Lobby;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;

import java.util.*;

public class AlhambraController {

    private Map<String, Game> ongoingGames = new HashMap<>();
    private Map<String, Lobby> lobbies = new HashMap<>();
    private List<Player> players = new LinkedList<>();
    private int gameIdCounter = 0;

    public String initializeLobby() {
        Lobby lobby = new Lobby("group01-" + gameIdCounter);
        lobbies.put(lobby.getGameID(), lobby);
        incrID();
        return lobby.toString();
    }

    private void incrID() {
        gameIdCounter++;
    }

    public List<String> getGameIds() {
        List<String> tempList = new LinkedList<>();

        for (Lobby lobby : lobbies.values()) {
            tempList.add(lobby.toString());
        }
        return tempList;
    }

    public Map<String, Game> getOngoingGames() {
        return ongoingGames;
    }

    public Map<String, Lobby> getLobbies() {
        return lobbies;
    }

    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

    public boolean takeMoney(String name, String gameID, List<Coin> coins){
        Game currentGame = ongoingGames.get(gameID);

        if (currentGame.getCurrentPlayer().getName().equals(name)){
            currentGame.getBank().takeCoins(coins);
            currentGame.getCurrentPlayer().getBag().addCoins(coins);
            return true;
        }else{
            throw new AlhambraGameRuleException("It's not your turn!");
        }

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

    public boolean setReadyState(String name, String gameID) {
        Player player = searchPlayer(name);
        Lobby currentLobby = lobbies.get(gameID);
        if (player != null) {
            player.setReady(player.isReady());

            if (currentLobby.getPlayers().size() > 1 && currentLobby.checkReadyStateForStartGame()) {
                startGame(lobbies.get(gameID).getPlayers(), gameID);
            }

            return true;
        }

        return false;
    }

    private void startGame(List<Player> players, String gameID) {
        Game game = new Game(players, gameID);
        ongoingGames.put(gameID, game);
        lobbies.remove(gameID);
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
        for (Lobby lobby : lobbies.values()) {
            if (lobby.getGameID().equals(gameID)) {
                lobby.addPlayer(player);
                return gameID + '+' + name;
            }
        }
        throw new AlhambraEntityNotFoundException("This game does not exist.");
    }


    public Object getGameState(String gameID) {

        Lobby lobby = lobbies.get(gameID);
        Game game = ongoingGames.get(gameID);

        if (lobby == null) {
            return game.getState();
        } else {
            return lobby.getState();
        }

    }
}
