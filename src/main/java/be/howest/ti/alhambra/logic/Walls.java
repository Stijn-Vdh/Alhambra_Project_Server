package be.howest.ti.alhambra.logic;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
