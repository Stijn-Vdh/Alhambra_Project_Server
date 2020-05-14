package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

import java.util.Arrays;

public class City {

    private static final int SIZE = 6;
    private static final int ROW = 1;
    private static final int COLUMN = 1;

    Building[][] board = new Building[SIZE][SIZE];

    public City(){
         initializeCity();
    }
    public void initializeCity(){
        int center = SIZE/2;
        for (Building[] buildings : board) {
            Arrays.fill(buildings, null);
        }
        board[center][center] = new Building(null,0, new Walls(false,false,false,false));
    }

    public Building[][] getBoard() {
        return board;
    }

    public void placeBuilding(Building b, Location location){

        int row = location.getRow();
        int col = location.getCol();

        if (board[row][col] == null) {
            if (hasNeighbours(row,col)){
                board[row][col] = b;
            } else {
                throw new AlhambraGameRuleException("This place has no neighbours!");
            }
        }
        else {
            throw new AlhambraGameRuleException("There is already a building here!");
        }
    }

    // maybe change to OR functions???
    public boolean hasNeighbours(int row , int col){
        if (row + ROW<SIZE && board[row + ROW][col] != null){
             return true;
        }
        else if (row - ROW<SIZE && board[row - ROW][col] != null){
            return true;
        }
        else if (col + COLUMN<SIZE && board[row][col + COLUMN] != null){
            return true;
        }
        else return col - COLUMN <SIZE && board[row][col - COLUMN] != null;
    }
}
