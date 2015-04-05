package sg.games.tank.entities;

import sg.atom.ai.agents.Agent;
import sg.atom.ai.agents.entities.AbstractBullet;
import sg.atom.ai.agents.entities.AbstractWeapon;
import com.jme3.math.Vector3f;

/**
 *
 * @author Tihomir Radosavljevic
 * @version 1.0
 */
public class Gun extends AbstractWeapon {

    public Gun(Agent agent) {
        this.agent = agent;
        name = "Gun";
        cooldown = 0.4f;
        attackDamage = 20f;
        numberOfBullets = -1;
        maxAttackRange = 1000f;
        minAttackRange = 3f;
    }

    @Override
    protected AbstractBullet controlAttack(Vector3f direction, float tpf) {
//        List<AnimControl> animationList = ((AIModel) agent.getModel()).getAnimationList();
//        //get animation for fired gun
//        for (AnimControl animation : animationList) {
//            if (!animation.getChannel(0).getAnimationName().equals("shoot")) {
//                animation.getChannel(0).setAnim("shoot", 0.1f);
//                animation.getChannel(0).setSpeed(1.5f);
//                animation.getChannel(0).setLoopMode(LoopMode.DontLoop);
//            }
//        }
        return new Bullet(this, agent.getLocalTranslation());
    }
}
