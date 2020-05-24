package be.howest.ti.alhambra.logic.game;

import be.howest.ti.alhambra.logic.building.BuildingType;
import be.howest.ti.alhambra.logic.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoringRound {
    private Map<BuildingType, ArrayList<Player>> scoringTable;
    private List<Player> players;

    public ScoringRound(List<Player> players) {
        this.scoringTable = new HashMap<>();
        this.players = players;
        initScoringTable();
    }

    private void initScoringTable() {
        for (BuildingType type : BuildingType.values()) {
                scoringTable.put(type, new ArrayList<>());

        }
    }

    public void calcMVPRound1perType() {

        for (BuildingType type : scoringTable.keySet()) {
            for (Player player : players) {
                ArrayList<Player> firstPlaceList = scoringTable.get(type);

                if (player.getBuildingTypesInCity().get(type) != 0) {
                    if (firstPlaceList.isEmpty() || firstPlaceList.get(0).getBuildingTypesInCity().get(type) < player.getBuildingTypesInCity().get(type)) {
                        firstPlaceList.clear();
                        firstPlaceList.add(player);

                    } else if (firstPlaceList.get(0).getBuildingTypesInCity().get(type).equals(player.getBuildingTypesInCity().get(type))) {
                        firstPlaceList.add(player);
                    }
                }
            }
        }
    }

    public void calcScoringRound1(int incr) {
        for (BuildingType type : scoringTable.keySet()) {
            if (!scoringTable.get(type).isEmpty()) {
                ArrayList<Player> firstPlaceList = scoringTable.get(type);

                int maxValue = getMaxValueRound(type, incr);

                if (firstPlaceList.size() > 1) {
                    maxValue = maxValue / firstPlaceList.size();
                    for (Player player : firstPlaceList) {
                        int playerScore = player.getScore() + maxValue;
                        int playerVirtualScore = player.getVirtualScore() + maxValue;

                        player.setScore(playerScore);
                        player.setVirtualScore(playerVirtualScore);
                    }

                } else {

                    int playerVirtualScore = firstPlaceList.get(0).getVirtualScore() + maxValue;
                    int playerScore = firstPlaceList.get(0).getScore() + maxValue;

                    firstPlaceList.get(0).setScore(playerScore);
                    firstPlaceList.get(0).setVirtualScore(playerVirtualScore);
                }
            }
        }
    }


    private int getMaxValueRound(BuildingType type, int incr) {
        switch (type) {
            case PAVILION:
                return 1 + incr;
            case SERAGLIO:
                return 2 + incr;
            case ARCADES:
                return 3 + incr;
            case CHAMBERS:
                return 4 + incr;
            case GARDEN:
                return 5 + incr;
            case TOWER:
                return 6 + incr;
            default:
                return 0;
        }
    }

}
