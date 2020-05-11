package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.player.Player;

import java.util.*;


public class Game {

    private String gameID;
    private List<Player> players = new LinkedList<>();
    private boolean started;
    private boolean ended;
    private Player currentPlayer;

    public Game(int counter) {
        gameID = generateGameID(counter);
        started = true;
        ended = false;
    }

    public String generateGameID(int counter) {
        String prefix = "group01-";
        if (counter == 0) {
            counter = 1;
        } else {
            counter++;
        }

        return prefix + counter;
    }

    public String getGameID() {
        return gameID;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Object getState() {
        Map<String, Object> state = new HashMap<>();
        List<String> playerNames = new LinkedList<>();

        for (Player player: players) {
            playerNames.add(player.getName());
        }

        state.put("bank", gameID);
        state.put("market", playerNames);
        state.put("players", players);
        state.put("started", started);
        state.put("ended", ended);
        state.put("currentPlayer", currentPlayer);

        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return gameID.equals(game.gameID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID);
    }

    @Override
    public String toString() {
        return gameID;
    }
}
