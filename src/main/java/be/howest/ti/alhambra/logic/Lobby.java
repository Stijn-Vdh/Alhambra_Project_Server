package be.howest.ti.alhambra.logic;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Lobby {
    private String gameID;
    private Set<Player> players = new HashSet<>();
    private int readyAmount;

    public Lobby(String gameID, String name) {
        this.gameID = gameID;
        Player player = new Player(name, false);
        addPlayer(player);

        this.readyAmount = getReadyAmount();

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

    public void addPlayer(Player player) {players.add(player);}

    public void removePlayer(Player player) {players.remove(player);}

    public void checkReadyState(){
        int playerCount = players.size();

        if (getReadyAmount() == playerCount){
            startGame();

        }
    }

    public void startGame(){
        //code for a new game
    }


    public void setReadyState(Player player) {
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
        return Objects.equals(getGameID(), lobby.getGameID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameID());
    }
}
