package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Building {

    private final BuildingType type;
    private final int cost;
    private final Walls walls;

    @JsonCreator
    public Building(@JsonProperty("type") BuildingType type, @JsonProperty("cost") int cost, @JsonProperty("walls") Walls walls) {
        this.type = type;
        this.cost = cost;
        this.walls = walls;
    }

    public BuildingType getType() {
        return type;
    }

    public int getAmount() {
        return cost;
    }

    public Walls getWalls() {
        return walls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return cost == building.cost &&
                type == building.type &&
                walls.equals(building.walls);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, cost, walls);
    }
}