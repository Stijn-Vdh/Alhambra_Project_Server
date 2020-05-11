package be.howest.ti.alhambra.logic.player;

import be.howest.ti.alhambra.logic.AlhambraController;
import be.howest.ti.alhambra.logic.exceptions.AlhambraEntityNotFoundException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class Player {

    private String name;
    private boolean isReady;

    @JsonCreator
    public Player(@JsonProperty("name") String name) {
        this.name = name;
        this.isReady = false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String joinGame(String gameID) {
        return gameID + '+' + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
