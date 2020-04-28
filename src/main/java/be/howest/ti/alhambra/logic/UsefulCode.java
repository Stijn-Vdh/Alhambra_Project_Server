package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Set;

public class UsefulCode {
/*    @JsonCreator
    public UsefulCode(){
        lobbies = new HashSet<>();
    }

    private Set<Lobby> lobbies;

    @JsonProperty("lobbies")
    public Set<Lobby> getLobbies() {
        return lobbies;
    }



    @JsonProperty("gameID")
    public String generateGameID(){
        String group = "group01-";
        int counter;
        if (lobbies.isEmpty()){
            counter = 1;
        } else{
            counter = lobbies.size() + 1;
        }

        return group + counter;
    }



    public void joinLobby(String gameID, String name){
        Player player = new Player(name, false);
        for (Lobby lobby: lobbies
             ) {
            if (lobby.getGameID().equals(gameID)){
                lobby.addPlayer(player);
            }
        }
    }

    public void setReadyState(String gameID, Player player){
        for (Lobby lobby: lobbies
        ) {
            if (lobby.getGameID().equals(gameID)){
                lobby.setReadyState(player);
            }
        }
    }

    public void deleteLobby(Lobby lobby){
        lobbies.remove(lobby);
    }

    @JsonProperty("name")
    private String getPlayerName(String player) {
        // test code. String moet aangepast worden naar Player
        return player;
    }*/


}
