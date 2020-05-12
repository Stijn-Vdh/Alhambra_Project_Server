package be.howest.ti.alhambra.logic.player;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.City;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;
import java.util.Objects;

public class Player {

    private String name;
    private boolean isReady;
    private List<Building> reserve;
    private List<Building> buildingsInHand;
    private List<City> city;
    private int virtualScore;
    private int score;
    private CoinBag bag;

    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.name = name;
        this.isReady = false;
        this.reserve = new LinkedList<>();
        this.buildingsInHand = new LinkedList<>();
        this.city = new LinkedList<>();
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

    public List<City> getCity() {
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

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void putBuildingInHand(Building b){
        if (buildingsInHand.isEmpty()){
            buildingsInHand.add(b);
        }else throw new AlhambraGameRuleException("Hand already contains a building!");
        
    }

    public void setReady(boolean ready) {
        isReady = !ready;
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
