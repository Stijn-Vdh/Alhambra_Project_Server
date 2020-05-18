package be.howest.ti.alhambra.logic.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {

    private final int row;
    private final int col;

    @JsonCreator
    public Location(@JsonProperty("row") int row, @JsonProperty("col") int col){
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return "{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }
}
