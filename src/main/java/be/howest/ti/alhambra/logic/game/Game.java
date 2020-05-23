package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.BuildingType;
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
    private Map<BuildingType, ArrayList<Player>> scoringTable;

    public Market getMarket() {
        return market;
    }

    public Game(List<Player> players, String gameID) {
        this.gameID = gameID;
        this.players = players;
        this.started = true;
        this.ended = false;
        this.scoringTable = new HashMap<>();
        bank = new Bank();
        market = new Market();

        for (Player player : players) {
            int maxValueCoinsInHand;
            if (player.getName().equals("smellyellie")) {
                maxValueCoinsInHand = 30;
            } else {
                maxValueCoinsInHand = 20;
            }
            List<Coin> startingCoins = bank.dealStartingCoins(maxValueCoinsInHand);
            player.getMoney().addCoins(startingCoins);
        }
        bank.addCoinsToBoard();

        calculateCoinsPerStack();

        this.turnCounter = getStartingPlayerIndex();

        changeCurrentPlayer();
    }

    private void calculateCoinsPerStack() {
        coinsRemaining = bank.getAmountOfCoins();
        int coinsPileSize = coinsRemaining / 5;
        coinsRemainingForScoringRound1 = coinsRemaining - coinsPileSize;
        coinsRemainingForScoringRound2 = coinsRemaining - 3 * coinsPileSize;
    }

    private void checkScoringRounds() {
        if (coinsRemaining <= coinsRemainingForScoringRound1 && !scoringRound1) {
            scoringRound1 = true;
            calcMVPRound1perType();
            calcScoringRound1();
        } else if (coinsRemaining <= coinsRemainingForScoringRound2 && !scoringRound2) {
            scoringRound2 = true;
            //TODO --> write function for calculating scoring round score
        }
    }

    private void calcMVPRound1perType() {
        initScoringTable();

        for (BuildingType type : scoringTable.keySet()) {
            for (Player player : players) {

                ArrayList<Player> tempList = new ArrayList<>();
                if (player.getBuildingTypesInCity().get(type) != 0) {
                    if (scoringTable.get(type).isEmpty() || scoringTable.get(type).get(0).getBuildingTypesInCity().get(type) < player.getBuildingTypesInCity().get(type)) {
                        tempList.add(player);
                        scoringTable.put(type, tempList);
                    } else if (scoringTable.get(type).get(0).getBuildingTypesInCity().get(type).equals(player.getBuildingTypesInCity().get(type))) {
                        tempList = scoringTable.get(type);
                        tempList.add(player);
                        scoringTable.put(type, tempList);
                    }
                    tempList.clear();
                }

            }
        }
    }

    private void calcScoringRound1(){
        for (BuildingType type : scoringTable.keySet()){
            int maxValue = getMaxValueRound1(type);
            if (scoringTable.get(type).size() > 1){
                maxValue = maxValue / scoringTable.get(type).size();
                for (Player player : scoringTable.get(type)){
                    int playerScore = player.getScore() + maxValue;
                    player.setScore(playerScore);
                }
            }else{
                scoringTable.get(type).get(0).setScore(maxValue);
            }
        }
    }

    private int getMaxValueRound1(BuildingType type){
        switch(type){
            case PAVILION:
                return  1;
            case SERAGLIO:
                return  2;
            case ARCADES:
                return  3;
            case CHAMBERS:
                return  4;
            case GARDEN:
                return 5;
            case TOWER:
                return 6;
            default:
                return 0;
        }

    }

    private void initScoringTable() {
        for (BuildingType type : BuildingType.values()) {
            scoringTable.put(type, null);
        }
    }

    public void changeCurrentPlayer() {
        if (turnCounter == players.size()) {
            turnCounter = 0;
        }
        this.currentPlayer = players.get(turnCounter);
        turnCounter++;
        coinsRemaining = bank.getAmountOfCoins();
        checkScoringRounds();
        market.addBuildingsToBoard();
    }

    public int getSmallestCoinBagSize() {
        int smallestBag = players.get(0).getMoney().getCoinsInBag().size();

        for (Player player : players) {
            int playerBagSize = player.getMoney().getCoinsInBag().size();
            if (playerBagSize < smallestBag) {
                smallestBag = player.getMoney().getCoinsInBag().size();
            }
        }
        return smallestBag;
    }

    private Player getPlayerWithLeastStartingCoinBagValue(List<Player> players) {

        int lowestValue = players.get(0).getMoney().calculateTotalCoinBagValue();

        Player playerWithLeastValue = null;

        for (Player player : players) {
            if (player.getMoney().calculateTotalCoinBagValue() <= lowestValue) {
                playerWithLeastValue = player;
            }
        }

        return playerWithLeastValue;
    }

    public int getStartingPlayerIndex() {
        List<Player> tempList = new ArrayList<>();
        int smallestCoinBagSize = getSmallestCoinBagSize();

        for (Player player : players) {
            if (player.getMoney().getCoinsInBag().size() == smallestCoinBagSize) {
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
        state.put("scoringRound1", scoringRound1);
        state.put("scoringRound2", scoringRound2);

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
