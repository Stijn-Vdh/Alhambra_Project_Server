package be.howest.ti.alhambra.logic.game;

import java.util.Objects;


public class Game {

    private String gameID;

    public Game(int counter) {
        gameID = generateGameID(counter);
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
