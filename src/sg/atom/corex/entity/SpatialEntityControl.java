/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.entity;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import sg.atom.corex.managers.WorldManager;

/**
 * <code>SpatialEntityControl</code> (Common Implementation) keep the link from
 * the
 * <code>Spatial</code> to the appropriate
 * <code>Entity</code>.</br>
 *
 * It's basiclly the low level control in the Atom architecture.
 *
 * <p>It uses Animation and Character control and should be use by
 * <code>SpatialSelectControl</code>,
 * <code>AIControl</code> etc...
 * @author atomix
 */
public class SpatialEntityControl extends AbstractControl {

    protected EntityManager entityManager;
    protected SpatialEntity spatialEntity;

    public SpatialEntityControl(EntityManager worldManager, SpatialEntity spatialEntity) {
        this.entityManager = worldManager;
        this.spatialEntity = spatialEntity;
        this.spatial = spatialEntity.getSpatial();
    }

    @Override
    protected void controlUpdate(float tpf) {
        this.spatialEntity.update(tpf);
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    public Control cloneForSpatial(Spatial spatial) {
        return null;
    }

    public SpatialEntity getEntity() {
        return spatialEntity;
    }
}
