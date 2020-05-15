package be.howest.ti.alhambra.logic.player;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.game.City;
import be.howest.ti.alhambra.logic.game.Location;
import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class Player {

    private String name;
    private boolean isReady;
    private List<Building> reserve;
    private List<Building> buildingsInHand;
    private City city;
    private int virtualScore;
    private int score;
    private CoinBag bag;

    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.name = name;
        this.isReady = false;
        this.reserve = new LinkedList<>();
        this.buildingsInHand = new LinkedList<>();
        this.city = new City();
        this.virtualScore = 0;
        this.score = 0;
        this.bag = new CoinBag();

    }

    public CoinBag getBag() {
        return bag;
    }

    public List<Building> getReserve() {
        return reserve;
    }

    public List<Building> getBuildingsInHand() {
        return buildingsInHand;
    }

    public City getCity() {
        return city;
    }

    public int getVirtualScore() {
        return virtualScore;
    }

    public int getScore() {
        return score;
    }

    public String getName() {
        return name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void putBuildingInHand(Building b){
            buildingsInHand.add(b);
    }

    public void setReady(boolean readyState) {
        isReady = readyState;
    }

    public void removeBuildingInHand(Building b){
        buildingsInHand.remove(b);
    }

    public void removeBuildingFromReserve(Building building) {
        reserve.remove(building);
    }

    public void putBuildingFromBoardInReserve(Location location) {

        int row = location.getRow();
        int col = location.getCol();

        final int CHANGE_TO_ACTUAL_POSITION = 3;

        row = row + CHANGE_TO_ACTUAL_POSITION;
        col = col + CHANGE_TO_ACTUAL_POSITION;

        Building building = city.getBoard()[row][col];
        reserve.add(building);
        city.getBoard()[row][col] = null;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
