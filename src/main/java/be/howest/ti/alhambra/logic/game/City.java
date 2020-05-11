package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

public class City {

    static final int SIZE =6;

    Building[][] board = new Building[SIZE][SIZE];

    public City(){
         initializeCity();
    }
    public void initializeCity(){
        int center = SIZE/2;
        board[center][center] = new Building(null,0, new Walls(false,false,false,false));
        for(int r = 1; r< board.length; r++){
            for(int c = 1; c < board[r].length; c++){
                 board[r][c] = null;
            }
        }
    }

    public Building[][] getBoard() {
        return board;
    }

    public void placeBuiding(Building b, Location location){

        int row = location.getRow();
        int col = location.getCol();

        if (board[row][col] == null) {
            if (hasNeighbours(row,col)){
                board[row][col] = b;
            } else {
                throw new AlhambraGameRuleException("Deze plaats heeft geen aanliggende gebouwen!");
            }
        }
        else{
            throw new AlhambraGameRuleException("Hier staat al een gebouw!");
        }

    }


    public boolean hasNeighbours(int row , int col){
        if (board[row+1][col] != null){
             return true;
        }
        else if (board[row-1][col] != null){
            return true;
        }
        else if (board[row][col+1] != null){
            return true;
        }
        else return board[row][col - 1] != null;
    }
}
