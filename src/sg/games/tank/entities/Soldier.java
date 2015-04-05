package sg.games.tank.entities;

import com.jme3.scene.Spatial;

/**
 *
 * @author cuong.nguyen
 */
public class Soldier  extends Unit {

    public Soldier(int iid, String type, Spatial spatial) {
        super(iid, "Soldier", spatial);
    }

    public static class Builder {
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);


    }
}