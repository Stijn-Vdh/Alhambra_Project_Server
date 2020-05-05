package be.howest.ti.alhambra.logic.building;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Walls {
    private boolean north;
    private boolean east;
    private boolean south;
    private boolean west;

    @JsonCreator
    public Walls(@JsonProperty("north") boolean north, @JsonProperty("east") boolean east, @JsonProperty("south") boolean south, @JsonProperty("west") boolean west) {
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    public boolean isNorth() {
        return north;
    }

    public boolean isEast() {
        return east;
    }

    public boolean isSouth() {
        return south;
    }

    public boolean isWest() {
        return west;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Walls walls = (Walls) o;
        return north == walls.north &&
                east == walls.east &&
                south == walls.south &&
                west == walls.west;
    }

    @Override
    public int hashCode() {
        return Objects.hash(north, east, south, west);
    }
}
