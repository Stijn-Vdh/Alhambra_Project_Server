package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import be.howest.ti.alhambra.logic.player.Player;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Lobby {
    private Set<Player> players = new HashSet<>();
    private int readyAmount;
    private String gameID;

    @JsonCreator
    public Lobby(@JsonProperty("gameID") String gameID) {
        this.gameID = gameID;
        this.readyAmount = 0;
    }

    public void addPlayer(String name){
        //TODO change to values from a player and not a name
        Player player = new Player(name);
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
       throw new NotImplementedException("Oe kunde dees nau vergete");
    }

    public void setPlayerReadyState(Player player) {
        boolean readyState = player.isReady();
        if (readyState){
            player.setReady(false);
        }else {
            player.setReady(true);
        }
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
}
