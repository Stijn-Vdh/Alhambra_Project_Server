package be.howest.ti.alhambra.logic;

import java.util.HashSet;
import java.util.Set;

public class GameRepo {

    private Set<Game> games = new HashSet<>();


    public Set<Game> getGames() {
        return games;
    }

    public GameRepo() {
        newGame();
    }

    public String generateGameID(){
        String group = "group01-";
        int counter;
        if (games.isEmpty()){
            counter = 1;
        } else{
            counter = games.size() + 1;
        }

        return group + counter;
    }

    public void newGame(){
        Game game = new Game(generateGameID(), getPlayerName());
        games.add(game);
    }

    private String getPlayerName() {
        return null;
    }

}
