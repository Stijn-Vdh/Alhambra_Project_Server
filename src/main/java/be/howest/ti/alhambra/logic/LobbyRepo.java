package be.howest.ti.alhambra.logic;

import java.util.HashSet;
import java.util.Set;

public class LobbyRepo {

    private Set<Lobby> lobbies;


    public Set<Lobby> getLobbies() {
        return lobbies;
    }

    public LobbyRepo(){
        lobbies = new HashSet<>();
    }

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

    public void newLobby(){
        Lobby lobby = new Lobby(generateGameID(), getPlayerName("stav"));
        this.lobbies.add(lobby);
    }

    public void joinLobby(String gameID, Player player){
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

    private String getPlayerName(String player) {
        // test code. String moet aangepast worden naar Player
        return player;
    }

}
