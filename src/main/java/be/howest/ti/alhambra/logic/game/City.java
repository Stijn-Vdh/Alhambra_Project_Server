package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.Building;
import be.howest.ti.alhambra.logic.building.Walls;

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

}
