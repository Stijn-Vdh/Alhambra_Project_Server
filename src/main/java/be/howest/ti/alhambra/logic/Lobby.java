package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void addPlayer(){
        //TODO change test code to actual code
        Player player = new Player("john");
        players.add(player);
    }

    public void removePlayer(String name){
        for (Player player: players) {
            if (player.getName().equals(name)){
                players.remove(player);
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
        //code for a new alhambra game
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
