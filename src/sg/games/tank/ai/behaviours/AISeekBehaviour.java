package sg.games.tank.ai.behaviours;

import sg.atom.ai.agents.Agent;
import sg.atom.ai.agents.behaviours.steering.SeekBehaviour;
import sg.atom.ai.agents.events.GameObjectSeenEvent;
import sg.atom.ai.agents.events.GameObjectSeenListener;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import sg.games.tank.entities.Unit;

/**
 * Behaviour for coming closer to enemy.
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class AISeekBehaviour extends SeekBehaviour implements GameObjectSeenListener {

    Unit model;
    
    public AISeekBehaviour(Agent agent) {
        super(agent, null);
        model = (Unit) agent.getModel();
    }

    public void handleGameObjectSeenEvent(GameObjectSeenEvent event) {
        if (event.getGameObjectSeen() instanceof Agent) {
            Agent targetAgent = (Agent) event.getGameObjectSeen();
            if (agent.isSameTeam(targetAgent)) {
                return;
            }
            setTarget(targetAgent);
            enabled = true;
        }
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f vel = calculateNewVelocity();
//        System.out.println("Update Seek" + vel);
        model.setWalkDirection(vel.mult(tpf));
        rotateAgent(tpf);
    }

    @Override
    protected void rotateAgent(float tpf) {
        Quaternion q = new Quaternion();
        q.lookAt(velocity, new Vector3f(0, 1, 0));
        agent.getLocalRotation().slerp(q, agent.getRotationSpeed());
        model.setViewDirection(agent.getLocalRotation().mult(Vector3f.UNIT_Z).normalize());
    }
}
