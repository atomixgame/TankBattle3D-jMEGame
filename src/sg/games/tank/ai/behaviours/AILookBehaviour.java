package sg.games.tank.ai.behaviours;

import sg.atom.ai.agents.Agent;
import sg.atom.ai.agents.behaviours.npc.SimpleLookBehaviour;
import sg.atom.ai.agents.control.GameObjectControl;
import sg.atom.ai.agents.control.AgentsAppState;
import java.util.LinkedList;
import java.util.List;

/**
 * Behaviour for scanning environment.
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class AILookBehaviour extends SimpleLookBehaviour {

    AgentsAppState game;

    public AILookBehaviour(Agent agent) {
        super(agent);
        game = AgentsAppState.getInstance();
    }

    @Override
    protected List<GameObjectControl> look(Agent agent, float viewAngle) {
        List<GameObjectControl> temp = new LinkedList<GameObjectControl>();
        //are there agents in seeing angle
        for (Agent agentInGame : game.getAgents()) {
            if (agentInGame.isEnabled()) {
                if (!agentInGame.equals(agent) && !agent.isSameTeam(agentInGame) && game.lookable(agent, agentInGame, viewAngle)) {
                    temp.add(agentInGame);
                }
            }
        }
       
        return temp;
    }
//    
//    List<GameObject> checkPhysicObstacle(List<GameObject> temp){
//        //is there obstacle between agent and observer
//        Vector3f vecStart = agent.getLocalTranslation().clone().setY(1);
//        BulletAppState bulletState = game.getApp().getStateManager().getState(BulletAppState.class);
//        for (int i = 0; i < temp.size(); i++) {
//            GameObject agentInRange = temp.get(i);
//            Vector3f vecEnd = agentInRange.getLocalTranslation().clone().setY(1);
//            //what has bullet hit
//            List<PhysicsRayTestResult> rayTest = bulletState.getPhysicsSpace().rayTest(vecStart, vecEnd);
//
//            float distance = vecEnd.length();
//            PhysicsCollisionObject o = null;
//            if (rayTest.size() > 0) {
//                for (PhysicsRayTestResult getObject : rayTest) {
//                    //distance to next collision
//                    float fl = getObject.getHitFraction();
//                    PhysicsCollisionObject collisionObject = getObject.getCollisionObject();
//                    //bullet does not is not supposed to be seen
//                    if (collisionObject instanceof GhostControl) {
//                        continue;
//                    }
//                    Spatial thisSpatial = (Spatial) collisionObject.getUserObject();
//                    // Get the Enemy to kill
//                    if (fl < distance && !thisSpatial.equals(agentInRange.getSpatial())) {
//                        temp.remove(agentInRange);
//                        o = collisionObject;
//                    }
//                    
//                }
//            }
//        }
//        
//        return temp;
//    }
}