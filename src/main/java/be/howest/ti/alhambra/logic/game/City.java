package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walls;
import be.howest.ti.alhambra.logic.exceptions.AlhambraGameRuleException;

public class City {

    Building[][] board = new Building[6][6];

    public City(){
         initializeCity();
    }
    public void initializeCity(){
        board[0][0] = new Building(null,0, new Walls(false,false,false,false));
        for(int r = 1; r< board.length; r++){
            for(int c = 1; c< board[r].length; c++){
                 board[r][c] = null;
            }
        }
    }

    public Building[][] getBoard() {
        return board;
    }

    public void placeBuiding(Building b, int row , int col){
        if (board[row][col] == null)
        {
            board[row][col] = b;
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
