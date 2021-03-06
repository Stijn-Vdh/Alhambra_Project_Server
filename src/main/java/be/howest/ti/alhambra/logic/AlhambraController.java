package be.howest.ti.alhambra.logic;

import be.howest.ti.alhambra.exceptions.AlhambraException;
import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingRepo;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.Game;
import be.howest.ti.alhambra.logic.game.Lobby;
import be.howest.ti.alhambra.logic.game.Location;
import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.money.Currency;
import be.howest.ti.alhambra.logic.player.Player;
import java.util.*;

public class AlhambraController {

    private boolean exactAmount = false;
    private Map<String, Game> ongoingGames = new HashMap<>();
    private Map<String, Lobby> lobbies = new HashMap<>();
    private List<Player> players = new LinkedList<>();
    private int gameIdCounter = 0;


    //Money functions
    public Currency[] getCurrencies() {
        return Currency.values();
    }

    public boolean takeMoney(String name, String gameID, List<Coin> coins){
        Game currentGame = ongoingGames.get(gameID);

        if (currentGame.getCurrentPlayer().getName().equals(name)){
            currentGame.getBank().takeCoins(coins);
            currentGame.getCurrentPlayer().getMoney().addCoins(coins);
            currentGame.changeCurrentPlayer();
            return true;
        }else{
            throw new AlhambraGameRuleException("It's not your turn!");
        }
    }

    //Building functions
    public boolean buyBuilding(String gameId, String name, Currency currency, List<Coin> coins){
        Game currentGame = ongoingGames.get(gameId);
        if (currentGame.getCurrentPlayer().getName().equals(name)){
            Player player = searchPlayer(name);
            exactAmount = currentGame.getMarket().buyBuilding(Objects.requireNonNull(player), currency, coins);

            player.getMoney().removeSelectedCoinsFromBag();
            return true;
        }
        throw new AlhambraGameRuleException("it's not your turn!!!!");
    }

    public boolean placeBuilding(String gameId, String name, Building building, Location location){
        Game currentGame = ongoingGames.get(gameId);
        Player player = searchPlayer(name);
        if (currentGame.getCurrentPlayer() == player) {
            if (location == null){
                Objects.requireNonNull(player).placeBuildingInReserve(building);
            } else {
                Objects.requireNonNull(player).getCity().placeBuilding(building, location);
                increaseBuildingTypesInCity(player, building);
            }
            player.removeBuildingInHand(building);
            if (!exactAmount){
                currentGame.changeCurrentPlayer();
            }

        }else throw new AlhambraGameRuleException("It's not your turn!");

        return true;
    }

    private void increaseBuildingTypesInCity(Player player, Building building){
        int typeCounter = player.getBuildingTypesInCity().get(building.getType());
        player.getBuildingTypesInCity().put(building.getType(), ++typeCounter);
    }

    private void decreaseBuildingTypesInCity(Player player, Building building){
        int typeCounter = player.getBuildingTypesInCity().get(building.getType());
        if (typeCounter > 0) {
            player.getBuildingTypesInCity().put(building.getType(), --typeCounter);
        }else throw new AlhambraException("Counter is 0");

    }

    public boolean redesignCity(String gameId, String name, Building building, Location location){
        Game currentGame = ongoingGames.get(gameId);
        Player player = searchPlayer(name);

        if (currentGame.getCurrentPlayer() == player) {
            if (building == null){
                int CHANGE_TO_ACTUAL_POSITION = 3;
                
                Building buildingFromCity = Objects.requireNonNull(player).getCity().getBoard()
                        [location.getRow()+CHANGE_TO_ACTUAL_POSITION]
                        [location.getCol()+CHANGE_TO_ACTUAL_POSITION];
                decreaseBuildingTypesInCity(player, buildingFromCity);
                Objects.requireNonNull(player).putBuildingFromBoardInReserve(location);

            } else {
                Objects.requireNonNull(player).getCity().placeBuilding(building, location);
                increaseBuildingTypesInCity(player, building);
                player.removeBuildingFromReserve(building);
            }
            currentGame.changeCurrentPlayer();
        }else throw new AlhambraGameRuleException("It's not your turn!");

        return true;
    }

    public List<Location> getAvailableLocations(String gameId, String name, Walls walls){

        Game currentGame = ongoingGames.get(gameId);
        Player player = searchPlayer(name);

        if (currentGame.getCurrentPlayer() == player) {
            return Objects.requireNonNull(player).getCity().getAvailableLocations(walls);
        }else throw new AlhambraGameRuleException("It's not your turn!");

    }

    public BuildingType[] getBuildingTypes() {
        return BuildingType.values();
    }

    public List<Building> getAllBuildings() {

        return BuildingRepo.getAllBuildings();
    }

    //Player functions
    private Player searchPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

    public boolean setReady(String name, String gameID) {
        Lobby currentLobby = lobbies.get(gameID);
        for (Player player: currentLobby.getPlayers()
        ) {
            if (player.getName().equals(name)){
                player.setReady(true);
            }
        }

        if (currentLobby.getPlayers().size() > 1 && currentLobby.checkReadyStateForStartGame()) {
            startGame(lobbies.get(gameID).getPlayers(), gameID);
        }
        return true;


    }

    public boolean setNotReady(String name, String gameID) {
        Lobby currentLobby = lobbies.get(gameID);
        for (Player player: currentLobby.getPlayers()
        ) {
            if (player.getName().equals(name)){
                player.setReady(false);
            }
        }
        return true;
    }

    public List<Player> getPlayers() {
        return players;
    }

    //Game functions
    private void startGame(List<Player> players, String gameID) {
        Game game = new Game(players, gameID);
        ongoingGames.put(gameID, game);
        lobbies.remove(gameID);
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

    public Object getGameState(String gameID) {

        Lobby lobby = lobbies.get(gameID);
        Game game = ongoingGames.get(gameID);

        if (lobby == null) {
            return game.getState();
        } else {
            return lobby.getState();
        }
    }

    public List<String> getGameIds() {
        List<String> tempList = new LinkedList<>();

        for (Lobby lobby : lobbies.values()) {
            if (lobby.getPlayers().size() < 6){
                tempList.add(lobby.toString());
            }
        }
        Collections.sort(tempList);
        return tempList;
    }

    public Map<String, Game> getOngoingGames() {
        return ongoingGames;
    }

    private void incrID() {
        gameIdCounter++;
    }

    //Lobby functions
    public Map<String, Lobby> getLobbies() {
        return lobbies;
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

    public void removeLobbyIfEmpty(String gameID) {
        Lobby lobby = lobbies.get(gameID);
        if (lobby.getPlayers().isEmpty()) {
            lobbies.remove(gameID);
        }
    }

    //Server functie
    public void clearServer() {
        ongoingGames.clear();
        players.clear();
        lobbies.clear();
    }
}
