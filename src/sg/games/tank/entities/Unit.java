package sg.games.tank.entities;

import sg.atom.ai.agents.Agent;
import sg.atom.ai.agents.control.AgentsAppState;
import com.jme3.animation.AnimControl;
import com.jme3.app.Application;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;
import java.util.List;
import org.customsoft.stateless4j.StateMachine;
import sg.atom.corex.entity.SpatialEntity;
import sg.games.tank.ai.AIEvent;
import sg.games.tank.ai.states.AIState;

/**
 * Unit is the basic Entity which has Spatial and Agent.
 *
 * Sketch of AI.
 *
 * -find the target
 *
 * - go straight to the target if no particular danger
 *
 * - go in zigzag to the target if there is danger
 *
 * - shoot if close enough (in range)
 *
 * - die or explode if out of health
 *
 * @author cuong.nguyen
 */
public class Unit extends SpatialEntity {

    protected StateMachine<AIState, AIEvent> brain;
    protected Agent<Unit> agent;
    protected Gun gun;
    protected List<AnimControl> animationList;
    protected String[] animationNames = {"run"};

    public Unit(long iid, String type, Spatial spatial) {
        super(iid, type, spatial);
    }

    @Override
    public void init(Application app) {
        this.agent = new Agent(type, spatial);
        this.agent.setModel(this);
        this.gun = new Gun(agent);
    }

    void addArrow() {

        // add arrow
        Mesh arrow = new Arrow(Vector3f.UNIT_Z);
        Geometry geoArrow = new Geometry("arrow", arrow);
        Material matArrow = new Material(AgentsAppState.getInstance().getApp().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
        matArrow.setColor("Color", ColorRGBA.White);
        geoArrow.setMaterial(matArrow);
        geoArrow.setLocalTranslation(0f, 0.1f, 0f);
        ((Node) agent.getSpatial()).attachChild(geoArrow);
    }

    public void onStage() {
//        this.spatial.addControl(agent);
    }

    public Agent<Unit> getAgent() {
        return agent;
    }

    public void setAgent(Agent<Unit> agent) {
        this.agent = agent;
    }

    public Gun getGun() {
        return gun;
    }

    public void setGun(Gun gun) {
        this.gun = gun;
    }

    public List<AnimControl> getAnimationList() {
        return animationList;
    }

    public void setAnimationList(List<AnimControl> animationList) {
        this.animationList = animationList;
    }

    public String[] getAnimationNames() {
        return animationNames;
    }

    public void setAnimationNames(String[] animationNames) {
        this.animationNames = animationNames;
    }

    public void setWalkDirection(Vector3f vel) {

        
        Vector3f newPos = getSpatial().getLocalTranslation().add(vel);
        getSpatial().setLocalTranslation(newPos);
    }

    public void setViewDirection(Vector3f normalize) {
        
    }
}
