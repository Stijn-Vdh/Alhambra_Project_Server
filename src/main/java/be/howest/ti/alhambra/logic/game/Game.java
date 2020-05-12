package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.player.Player;
import be.howest.ti.alhambra.logic.money.Currency;

import java.util.*;


public class Game {

    private String gameID;
    private List<Player> players;
    private boolean started;
    private boolean ended;
    private Player currentPlayer;
    private Bank bank;
    private Market market;

    public Game(List<Player> players, String gameID) {
        this.gameID = gameID;
        this.players = players;
        this.started = true;
        this.ended = false;
        this.currentPlayer = players.get(new Random().nextInt(players.size()));
        bank = new Bank();
        for (Player player: players){
            List<Coin> startingCoins = bank.dealStartingCoins();
            player.getBag().addCoins(startingCoins);
        }
        bank.refill();
        market = new Market();
    }

    public void buyBuilding(String name, Currency currency, Coin[] coins){
        for (Player player : players){
            if (player.getName().equals(name)){
                market.buyBuilding(player, currency, Arrays.asList(coins));
            }
        }
    }


    public String getGameID() {
        return gameID;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Object getState() {
        Map<String, Object> state = new HashMap<>();

        state.put("bank", bank);
        state.put("market", market);
        state.put("players", players);
        state.put("started", started);
        state.put("ended", ended);
        state.put("currentPlayer", currentPlayer.getName());

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
