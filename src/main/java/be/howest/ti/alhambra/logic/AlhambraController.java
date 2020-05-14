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

    public List<String> getGameIds() {
        List<String> tempList = new LinkedList<>();

        for (Lobby lobby : lobbies.values()) {
            tempList.add(lobby.toString());
        }
        Collections.sort(tempList);
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

    public List<Building> getAllBuildings() {

        return BuildingRepo.getAllBuildings();
    }

    public List<Player> getPlayers() {
        return players;
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



    public boolean setReady(String name, String gameID) {
        Player player = searchPlayer(name);
        Lobby currentLobby = lobbies.get(gameID);
        if (player != null) {
            player.setReady(true);

            if (currentLobby.getPlayers().size() > 1 && currentLobby.checkReadyStateForStartGame()) {
                startGame(lobbies.get(gameID).getPlayers(), gameID);
            }
            return true;
        }
        return false;
    }

    public boolean setNotReady(String name) {
        Player player = searchPlayer(name);

        if (player != null) {
            player.setReady(false);
            return true;
        }
        return false;
    }



    public String initializeLobby() {
        Lobby lobby = new Lobby("group01-" + gameIdCounter);
        lobbies.put(lobby.getGameID(), lobby);
        incrID();
        return lobby.getGameID();
    }

    public String joinLobby(String gameID, String name) {
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

    public boolean takeMoney(String name, String gameID, List<Coin> coins){
        Game currentGame = ongoingGames.get(gameID);

        if (currentGame.getCurrentPlayer().getName().equals(name)){
            currentGame.getBank().takeCoins(coins);
            currentGame.getCurrentPlayer().getBag().addCoins(coins);
            currentGame.changeCurrentPlayer();
            return true;
        }else{
            throw new AlhambraGameRuleException("It's not your turn!");
        }

    }

    public boolean buyBuilding(String gameId, String name, Currency currency, List<Coin> coins){
        Game currentGame = ongoingGames.get(gameId);
        if (currentGame.getCurrentPlayer().getName().equals(name)){
            Player player = searchPlayer(name);
            currentGame.getMarket().buyBuilding(Objects.requireNonNull(player), currency, coins);

            player.getBag().removeSelectedCoinsFromBag();
            currentGame.changeCurrentPlayer();
            return true;
        }
        throw new AlhambraGameRuleException("it's not your turn!!!!");

    }

    public boolean leaveGame(String gameID, String name) {

        if (!ongoingGames.containsKey(gameID)) {
            lobbies.get(gameID).removePlayer(name);
            removeLobbyIfEmpty(gameID);
        } else {
            ongoingGames.get(gameID).getPlayers().remove(searchPlayer(name));
            removeGameIfEmpty(gameID);
        }
        players.removeIf(player -> player.getName().equals(name));
        return true;

    }

    public void removeGameIfEmpty(String gameID) {
        Game game = ongoingGames.get(gameID);
        if (game.getPlayers().isEmpty()) {
            ongoingGames.remove(gameID);
        }
    }

    public void removeLobbyIfEmpty(String gameID) {
        Lobby lobby = lobbies.get(gameID);
        if (lobby.getPlayers().isEmpty()) {
            lobbies.remove(gameID);
        }
    }

    public void clearAllGames() {
        ongoingGames.clear();
        lobbies.clear();
    }



    private void incrID() {
        gameIdCounter++;
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
}
