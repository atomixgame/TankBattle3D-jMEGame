package sg.games.tank.ai.behaviours;

import sg.atom.ai.agents.Agent;
import sg.atom.ai.agents.behaviours.npc.SimpleAttackBehaviour;
import sg.atom.ai.agents.events.GameObjectSeenEvent;
import java.util.Random;
import sg.games.tank.entities.Unit;

/**
 * Behaviour for attacking opponent if opponnet is seen.
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class AIAttackBehaviour extends SimpleAttackBehaviour {

    /**
     * Bigger value means easier game, if it is 1, then agent will never miss.
     * Must be greater or equal to 1.
     */
    private final int simplicity = 60;
    /**
     * To add some randomness to game.
     */
    private Random random;

    public AIAttackBehaviour(Agent agent) {
        super(agent);
        random = new Random();
    }

    @Override
    protected void controlUpdate(float tpf) {
        Unit weapons= (Unit) agent.getModel();
        if (targetPosition != null) {
            weapons.getGun().attack(targetedObject, tpf);
            targetPosition = null;
            //is he supossed to miss next time
            missOrNot((Agent) targetedObject);
        } else {
            //if target is seen
            if (targetedObject != null && targetedObject.isEnabled()) {
                //attack with all weapon at disposal

                //if target is in range of gun, fire him
                if (weapons.getGun().isInRange(targetedObject)) {
                    System.out.println("Attack!");
//                    weapons.getGun().attack(targetedObject, tpf);
                }
                //is he supossed to miss next time
                missOrNot((Agent) targetedObject);
            }
        }
        //update cooldown for weapons
//        weapons.getGun().update(tpf);
    }

    @Override
    public void handleGameObjectSeenEvent(GameObjectSeenEvent event) {
        if (event.getGameObjectSeen() instanceof Agent) {
            Agent targetAgent = (Agent) event.getGameObjectSeen();
            if (agent.isSameTeam(targetAgent)) {
                return;
            }
            targetedObject = targetAgent;
            missOrNot(targetAgent);
            enabled = true;
        }
    }

    private void missOrNot(Agent agent) {
        if (simplicity > 1) {
            int number = random.nextInt(simplicity);
            if (number > 1) {
                targetPosition = agent.getLocalTranslation().clone().mult(1.1f);
            }
        }
    }
}
