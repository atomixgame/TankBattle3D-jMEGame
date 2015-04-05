package sg.games.tank.entities;

import sg.atom.corex.entity.SpatialEntity;

/**
 *
 * @author cuong.nguyen
 */
public class Explosion extends SpatialEntity {
    float duration;
    float activeTime = 0;
    @Override
    public void update(float tpf) {
        super.update(tpf);
        activeTime += tpf;
    }

    public void explode(){
        
    }

    public void reset(){
        activeTime = 0;
    }
    
}
