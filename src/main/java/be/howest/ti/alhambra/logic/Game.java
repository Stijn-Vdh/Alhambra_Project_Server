package be.howest.ti.alhambra.logic;

import java.util.HashSet;
import java.util.Set;


public class Game {
    private String gameID;
    private Set<Player> players = new HashSet<>();
    private int playerCount;
    private int readyCount;
    private boolean gameStarted;



    public Game(String gameID, String name) {
        this.gameID = gameID;
        Player player = new Player(name, false);
        addPlayer(player);
        this.playerCount = getPlayerCount();
        this.readyCount = getReadyCount();
        this.gameStarted = isGameStarted();
    }

    public int getPlayerCount() {
        playerCount = players.size();
        return playerCount;
    }

    public int getReadyCount() {
        int counter = 0;
        for (Player player: players) {
            if (player.getReady()){
                counter++;
            }
        }
        readyCount = counter;
        return readyCount;
    }

    public void addPlayer(Player player) {players.add(player);}

    public Set<Player> getPlayers() {return players;}


    public boolean isGameStarted() {
        gameStarted = readyCount == playerCount;
        return gameStarted;
    }

    public String getGameID() {
        return gameID;
    }
}
