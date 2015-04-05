package sg.games.tank.entities;

import com.jme3.scene.Spatial;

/**
 *
 * @author cuong.nguyen
 */
public class Soldier  extends Unit {

    public Soldier(long iid, String type, Spatial spatial) {
        super(iid, "Soldier", spatial);
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