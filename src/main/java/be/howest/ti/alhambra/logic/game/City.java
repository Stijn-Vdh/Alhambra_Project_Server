package be.howest.ti.alhambra.logic.game;


import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class City {

    private static final int SIZE = 7;
    private static final int OFFSET = 1;

    Building[][] board = new Building[SIZE][SIZE];

    public City(){
         initializeCity();
    }
    public void initializeCity(){
        int center = (int)(SIZE/2);
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

        final int CHANGE_TO_ACTUAL_POSITION = 3;

        row = row + CHANGE_TO_ACTUAL_POSITION;
        col = col + CHANGE_TO_ACTUAL_POSITION;

        List<Location> availableLocations = getAvailableLocations(b.getWalls());

        if (board[row][col] == null) {

                for (Location l : availableLocations){
                    if (location.equals(l)){
                        board[row][col] = b;
                    }
                }

        }
        else {
            throw new AlhambraGameRuleException("This move is against the rules!");
        }
    }

    public boolean hasNeighbours(int row , int col){
        if ((row + OFFSET) < SIZE && board[row + OFFSET][col] != null){
             return true;
        }
        else if ((row - OFFSET) >= 0 && board[row - OFFSET][col] != null){
            return true;
        }
        else if ((col + OFFSET) < SIZE && board[row][col + OFFSET] != null){
            return true;
        }
        else return (col - OFFSET) >= 0 && board[row][col - OFFSET] != null;
    }

    public List<Location> getAvailableLocations(Walls walls){
        List<Location> locations = new ArrayList<>();

        final int CHANGE_TO_LOGIC_POSITION = 3;
        

        for (int row = 0; row < board.length;row++){
            for (int col = 0; col < board[row].length; col++){
                if (board[row][col] == null && hasNeighbours(row, col) ){

                    boolean availableLocation = true;

                    if ((row + OFFSET) < SIZE && board[row + OFFSET][col] != null){
                        if (walls.isSouth() != board[row + OFFSET][col].getWalls().isNorth()){
                             availableLocation = false;
                        }
                    }
                    if ((row - OFFSET) >= 0 && board[row - OFFSET][col] != null){
                        if (walls.isNorth() != board[row - OFFSET][col].getWalls().isSouth()){
                            availableLocation = false;
                        }
                    }
                    if ((col + OFFSET) < SIZE && board[row][col + OFFSET] != null){
                        if (walls.isEast() != board[row][col + OFFSET].getWalls().isWest()){
                            availableLocation = false;
                        }
                    }
                    if ((col - OFFSET) > 0 && board[row][col - OFFSET] != null){
                        if (walls.isWest() != board[row][col - OFFSET].getWalls().isEast()){
                            availableLocation = false;
                        }
                    }

                    if (availableLocation){
                        locations.add(new Location(row - CHANGE_TO_LOGIC_POSITION, col - CHANGE_TO_LOGIC_POSITION));
                    }

                }
            }
        }
        return locations;
    }
}
