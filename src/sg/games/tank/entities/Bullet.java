package sg.games.tank.entities;

import sg.games.tank.stage.fx.ExplosionControl;
import sg.atom.ai.agents.entities.AbstractBullet;
import sg.atom.ai.agents.entities.AbstractWeapon;
import sg.atom.ai.agents.control.AgentsAppState;
import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class Bullet extends AbstractBullet {

    private Vector3f vecMove;
    /**
     * Position where bullet appears.
     */
    private Vector3f bornPlace;
    /**
     * Position where bullet disapears and explosion starts.
     */
    private Vector3f contactPoint;
    private float bulletLength;
    private float bulletPathLength;
    private Spatial targetedSpatial;
    private Application app;
    private Geometry geoRay;

    public Bullet(AbstractWeapon weapon, Vector3f origin) {
        super(weapon, null);
        this.app = AgentsAppState.getInstance().getApp();
        
        /* A colored lit cube. Needs light source! */
        Box boxMesh = new Box(0.4f, 0.4f, 0.4f);
        Geometry boxGeo = new Geometry("Colored Box", boxMesh);
        Material boxMat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        boxMat.setBoolean("UseMaterialColors", true);
        boxMat.setColor("Ambient", ColorRGBA.Green);
        boxMat.setColor("Diffuse", ColorRGBA.Green);
        boxGeo.setMaterial(boxMat);
        this.spatial = boxGeo;

        Spatial agentSpatial = weapon.getAgent().getSpatial();
        spatial.setLocalRotation(agentSpatial.getLocalRotation().clone());
//        spatial.setLocalTranslation(((AIModel) weapon.getAgent().getModel()).getSpatialTranslation((Geometry) spatial).clone());
        spatial.addControl(this);

        bornPlace = new Vector3f(origin.setY(spatial.getLocalTranslation().getY()));

        bulletPathLength = weapon.getMaxAttackRange();

        vecMove = spatial.getLocalRotation().mult(Vector3f.UNIT_Z).normalizeLocal().mult(4f);
        bulletLength = 70f;

        Vector3f vecStart = bornPlace;
        Vector3f vecEnd = bornPlace.add(spatial.getLocalRotation().mult(Vector3f.UNIT_Z).normalizeLocal().mult(bulletLength));

//        BulletAppState bulletState = this.app.getStateManager().getState(BulletAppState.class);


        // testRay forDebug
//        if (((AIGameControl) Game.getInstance().getGameControl()).isGameDebug()) {
//            Vector3f endVecToDebug = vecEnd;
//            if (contactPoint != null) {
//                endVecToDebug = contactPoint;
//            }
//            geoRay = new Geometry("line", new Line(vecStart, endVecToDebug));
//            Material mat_bullet = new Material(app.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
//            mat_bullet.setColor("Color", ColorRGBA.Cyan);
//            geoRay.setMaterial(mat_bullet);
//            Node root = (Node) this.app.getViewPort().getScenes().get(0);
//            root.attachChild(geoRay);
//        }
        AgentsAppState.getInstance().getRootNode().attachChild(spatial);
    }

    @Override
    protected void controlUpdate(float tpf) {
        //update only for fixed rate

        //calculate distance
        float distance = bornPlace.distance(spatial.getLocalTranslation());
        //is this the place of destrucion defined
        if (contactPoint != null) {
            float contactPointDistance = bornPlace.distance(contactPoint);
            if (distance >= contactPointDistance || distance + vecMove.length() > contactPointDistance) {
                //now is time for explosion
                Node nd = new Node("expl");
                nd.addControl(new ExplosionControl(contactPoint, nd, app));
                AgentsAppState.getInstance().getRootNode().attachChild(nd);
                if (geoRay != null) {
                    geoRay.removeFromParent();
                    geoRay = null;
                }
                AgentsAppState.getInstance().removeGameObject(this);
                return;
            }
        }

        //is it time to remove bullet
        if (distance >= bulletLength || distance + vecMove.length() > bulletLength) {
            if (geoRay != null) {
                geoRay.removeFromParent();
                geoRay = null;
            }
            AgentsAppState.getInstance().removeGameObject(this);
            return;
        }
        spatial.move(vecMove.mult(tpf));

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }
//    boolean isHitted(){
//        //what has bullet hit
//        List<PhysicsRayTestResult> rayTest = bulletState.getPhysicsSpace().rayTest(vecStart, vecEnd);
//        if (rayTest.size() > 0) {
//            for (PhysicsRayTestResult getObject : rayTest) {
//                //distance to next collision
//                float fl = getObject.getHitFraction();
//                PhysicsCollisionObject collisionObject = getObject.getCollisionObject();
//                Spatial thisSpatial = (Spatial) collisionObject.getUserObject();
//                //i don't to shoot myself while running forward
//                if (thisSpatial.equals(weapon.getAgent().getSpatial())) {
//                    continue;
//                }
//                // Get the Enemy to kill
//                if (fl < bulletPathLength && thisSpatial.getControl(GhostControl.class) == null) {
//                    bulletPathLength = fl;
//                    targetedSpatial = thisSpatial;
//                }
//            }
//
//            //if there is targeted spatial
//            if (targetedSpatial != null) {
//                contactPoint = vecStart.clone().interpolateLocal(vecEnd, bulletPathLength);
//                //if bullet hit agent
//                if (targetedSpatial.getControl(AIModel.class) != null) {
//                    AIModel model = targetedSpatial.getControl(AIModel.class);
//                    Game.getInstance().agentAttack(weapon.getAgent(), model.getAgent(), weapon);
//                    if (!model.getAgent().isEnabled()) {
//                        //remove agent from physic space
//                        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(model);
//                        //remove agent's sword from physics space
//                        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(model.getSword().getSpatial());
//                    }
//
//                }
//            }
//        }
//    }
}
