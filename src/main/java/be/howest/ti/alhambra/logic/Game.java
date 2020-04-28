package be.howest.ti.alhambra.logic;

import java.util.HashSet;
import java.util.Set;


public class Game {
    private String gameID;
    private Set<Player> players = new HashSet<>();
    private int playerCount;
    private int readyCount;
    private boolean gameStarted = false;

    public void add(Player player) {players.add(player);}




    public Game(String gameID, Set<Player> players) {
        this.gameID = gameID;
        this.players = players;

    }
}
