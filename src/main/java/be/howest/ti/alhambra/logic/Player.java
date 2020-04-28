package be.howest.ti.alhambra.logic;

public class Player {

    private String name;
    private Boolean ready;

    public Player(String name, Boolean ready) {
        this.name = name;
        this.ready = ready;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isReady() {
        return ready;
    }

    public void setReady(Boolean ready) {
        this.ready = ready;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", ready=" + ready +
                '}';
    }
}
