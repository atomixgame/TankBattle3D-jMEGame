package sg.games.tank.entities;

import java.util.LinkedList;

public class Building extends Unit {

    public Building(String typeName) {
        super(typeName);

        this.movable = false;
    }
    LinkedList<BuildSlot> slots;

    public class BuildFunction {
    }

    public class BuildSlot {

        Unit unit;
        float time;
        float percent;
        boolean status;
    }

    public void act(float delta) {
    }

    public void addToQueue() {
    }

    public void addUnit(Unit unit) {
    }

    public void publish(Unit unit) {
    }
}
