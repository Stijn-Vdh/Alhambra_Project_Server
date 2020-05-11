package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.player.Player;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.NotImplementedException;

import java.util.*;


public class Lobby {
    private Set<Player> players = new HashSet<>();
    private int readyAmount;
    private String gameID;
    private boolean started;

    @JsonCreator
    public Lobby(@JsonProperty("gameID") String id) {
        this.gameID = id;
        this.readyAmount = 0;
        this.started = false;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void removePlayer(String name){
        for (Player player: players) {
            if (player.getName().equals(name)){
                players.remove(player);
            } else {
                throw new AlhambraEntityNotFoundException("name of player doesn't exist");
            }
        }
    }

    public Set<Player> getPlayers() {return players;}

    public String getGameID() {return gameID;}

    public int getReadyAmount() {
        int counter = 0;
        for (Player player: players) {
            if (player.isReady()){
                counter++;
            }
        }
        readyAmount = counter;
        return readyAmount;
    }

    public String checkReadyStateForStartGame(){
        int playerCount = players.size();
        if (getReadyAmount() == playerCount){
            startGame();
            return "alhambra started";
        }
        else {
            return "waiting for players to ready up";
        }
    }

    public void startGame(){
        started = true;
       throw new NotImplementedException("Oe kunde dees nau vergete");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lobby)) return false;
        Lobby lobby = (Lobby) o;
        return getGameID().equals(lobby.getGameID()) &&
                getPlayers().equals(lobby.getPlayers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameID(), getPlayers());
    }

    @Override
    public String toString() {
        return gameID;
    }

    public Map<String, Object> getState() {
        Map<String, Object> state = new HashMap<>();
        List<String> playerNames = new LinkedList<>();

        for (Player player: players) {
            playerNames.add(player.getName());
        }


        state.put("id", gameID);
        state.put("players", playerNames);
        state.put("started", started);
        state.put("playerCount", players.size());
        state.put("readyCount", getReadyAmount());
        return state;
    }
}
