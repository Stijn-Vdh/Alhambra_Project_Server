package be.howest.ti.alhambra.logic.game;

public class Location {

    int row;
    int col;


    public Location(int row, int col){
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
