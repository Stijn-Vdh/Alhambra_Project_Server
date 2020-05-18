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
    private int coinsRemainingForScoringRound1;
    private int coinsRemainingForScoringRound2;
    private boolean scoringRound1 = false;
    private boolean scoringRound2 = false;

    public Market getMarket() {
        return market;
    }

    public Game(List<Player> players, String gameID) {
        this.gameID = gameID;
        this.players = players;
        this.started = true;
        this.ended = false;
        bank = new Bank();

        for (Player player: players){
            List<Coin> startingCoins = bank.dealStartingCoins();
            player.getBag().addCoins(startingCoins);
        }
        bank.addCoinsToBoard();

        calculateCoinsPerStack();

        this.turnCounter = getStartingPlayerIndex();
        changeCurrentPlayer();

        market = new Market();
    }

    private void calculateCoinsPerStack() {
        int coinsPerStack = coinsRemaining / 5;
        coinsRemainingForScoringRound1 = coinsRemaining - coinsPerStack;
        coinsRemainingForScoringRound2 = coinsRemaining - 3* coinsPerStack;
    }

    private void checkScoringRounds() {
        if (coinsRemaining <= coinsRemainingForScoringRound1 && !scoringRound1) {
            scoringRound1 = true;
            //TODO --> write function for calculating scoring round score
        } else if (coinsRemaining <= coinsRemainingForScoringRound2 && !scoringRound2) {
            scoringRound2 = true;
            //TODO --> write function for calculating scoring round score
        }
    }

    public int getAmountOfCoinsLeft(){
        return bank.getAllCoins().size();
    }

    public void changeCurrentPlayer(){
        if (turnCounter == players.size()){
            turnCounter = 0;
        }
        this.currentPlayer = players.get(turnCounter);
        updateCoinsRemaining();
        turnCounter++;
        checkScoringRounds();
    }

    public void updateCoinsRemaining(){
        this.coinsRemaining = getAmountOfCoinsLeft();
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
        int smallestCoinBagSize = getSmallestCoinBagSize();

        for (Player player : players) {
            if (player.getBag().getCoinsInBag().size() == smallestCoinBagSize) {
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
