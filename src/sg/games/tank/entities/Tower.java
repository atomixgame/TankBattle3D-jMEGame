package sg.games.tank.entities;

import com.jme3.scene.Spatial;

/**
 * Tower. Get built within a duration. After build done auto operate with AI:
 * Detect nearby enemy. Shoot them with Projectile.
 *
 *
 * @author cuong.nguyen
 */
public class Tower extends Unit {

    public Tower(int iid, String type, Spatial spatial) {
        super(iid, "Tower", spatial);
    }

    public static class Builder {
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

    }
}
