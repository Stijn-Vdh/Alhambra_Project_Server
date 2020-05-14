package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.money.Coin;
import be.howest.ti.alhambra.logic.player.Player;

import java.util.*;


public class Game {

    private String gameID;
    private List<Player> players;
    private boolean started;
    private boolean ended;
    private Player currentPlayer;
    private Bank bank;
    private Market market;
    private int turnCounter;
    private int coinsRemaining;

    public Market getMarket() {
        return market;
    }

    public Game(List<Player> players, String gameID) {
        this.gameID = gameID;
        this.players = players;
        this.started = true;
        this.ended = false;
        changeCurrentPlayer();
        bank = new Bank();

        for (Player player: players){
            List<Coin> startingCoins = bank.dealStartingCoins();
            player.getBag().addCoins(startingCoins);
        }

        bank.addCoinsToBoard();

        this.coinsRemaining = getAmountOfCoinsLeft();

        market = new Market();
    }

    public int getAmountOfCoinsLeft(){
        return bank.getAllCoins().size();
    }

    public void changeCurrentPlayer(){
        if (turnCounter == players.size()){
            turnCounter = 0;
        }
        this.currentPlayer = players.get(turnCounter);

        turnCounter++;
    }

   public int getSmallestCoinBagSize(){
        int smallestBag = players.get(0).getBag().getCoinsInBag().size();

        for (Player player: players){
            int playerBagSize = player.getBag().getCoinsInBag().size();
            if (playerBagSize < smallestBag){
                smallestBag = player.getBag().getCoinsInBag().size();
            }
        }
        return smallestBag;
    }

    private Player getPlayerWithLeastStartingCoinBagValue(List<Player> players) {

        int lowestValue = players.get(0).getBag().calculateTotalCoinBagValue();

        Player playerWithLeastValue = null;

        for (Player player : players) {
            if (player.getBag().calculateTotalCoinBagValue() <= lowestValue) {
                playerWithLeastValue = player;
            }
        }

        return playerWithLeastValue;
    }

    public int getStartingPlayerIndex() {
        List<Player> tempList = new ArrayList<>();

        for (Player player : players) {
            if (player.getBag().getCoinsInBag().size() == getSmallestCoinBagSize()) {
                tempList.add(player);
            }
        }

        Player startingPlayer = getPlayerWithLeastStartingCoinBagValue(tempList);
        return players.indexOf(startingPlayer);

    }

    public String getGameID() {
        return gameID;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Bank getBank() {
        return bank;
    }

    public Object getState() {
        Map<String, Object> state = new HashMap<>();

        state.put("bank", bank);
        state.put("market", market);
        state.put("players", players);
        state.put("started", started);
        state.put("ended", ended);
        state.put("currentPlayer", currentPlayer.getName());
        state.put("coinsRemaining", coinsRemaining);

        return state;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
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
}
