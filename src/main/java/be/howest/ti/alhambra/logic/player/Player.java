package be.howest.ti.alhambra.logic.player;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;
import be.howest.ti.alhambra.logic.game.City;
import be.howest.ti.alhambra.logic.game.Location;
import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class Player {

    private String name;
    private boolean isReady;
    private List<Building> reserve;
    private List<Building> buildingsInHand;
    private Map<BuildingType, Integer> buildingTypesInCity;
    private City city;
    private int virtualScore;
    private int score;
    private CoinBag money;


    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.name = name;
        this.isReady = false;
        this.reserve = new LinkedList<>();
        this.buildingsInHand = new LinkedList<>();
        this.buildingTypesInCity = new HashMap<>();
        this.city = new City();
        this.virtualScore = 0;
        this.score = 0;
        this.money = new CoinBag();
        initializeBuildingTypesInCity();
    }

    public void initializeBuildingTypesInCity(){
        for (BuildingType type : BuildingType.values()){
            buildingTypesInCity.put(type, 0);
        }
    }

    public CoinBag getMoney() {
        return money;
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

    public void setScore(int score) {
        this.score = score;
    }

    public void putBuildingInHand(Building b){
            buildingsInHand.add(b);
    }

    public void placeBuildingInReserve(Building building) { reserve.add(building); }

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

        Location fountainLocation = new Location(0,0);

        int row = location.getRow();
        int col = location.getCol();

        final int CHANGE_TO_ACTUAL_POSITION = 3;

        row = row + CHANGE_TO_ACTUAL_POSITION;
        col = col + CHANGE_TO_ACTUAL_POSITION;

        if (!location.equals(fountainLocation)){
            Building building = city.getBoard()[row][col];
            reserve.add(building);
            city.getBoard()[row][col] = null;
        }else throw new AlhambraGameRuleException("It is against the rules to remove the fountain from the board.");
    }

    public void setCityBuildingTypes(Map<BuildingType, Integer> buildingTypesInCity) {
        this.buildingTypesInCity = buildingTypesInCity;
    }

    public Map<BuildingType, Integer> getBuildingTypesInCity() {
        return buildingTypesInCity;
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
