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

    public Tower(long iid, String type, Spatial spatial) {
        super(iid, "Tower", spatial);
//        speed = 1;
    }

    public static class Builder {
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
//        Vector3f localTranslation = this.spatial.getLocalTranslation().clone();
//        this.spatial.setLocalTranslation(localTranslation.add(0, 0, speed * tpf));

    }
}
