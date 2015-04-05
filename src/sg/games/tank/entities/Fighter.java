package sg.games.tank.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.limiters.NullLimiter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Fighter extends Unit {
//	public static final String ASSET_PATH = "graphics/anims/zealot";

    private int subType;
    BlendedSteering<Vector2> blendedSteering;

    public Fighter(int type) {
        super("fighter");
        this.subType = type;
    }

    public void init() {
        super.init();
        this.setBoundingRadius(20);
        this.updateBounding();

        setMaxLinearAcceleration(100);
        setMaxLinearSpeed(100);
        setMaxAngularAcceleration(40);
        setMaxAngularSpeed(15);
        setHealth(100);
        setMaxHealth(100);
        setAttackRange(20);
    }

    @Override
    public void activeUnit() {

        this.blendedSteering = new BlendedSteering<Vector2>(this)
                .setLimiter(NullLimiter.NEUTRAL_LIMITER);
        this.setSteeringBehavior(blendedSteering);

        super.activeUnit();
    }

    public void letMoveToTarget() {
        if (target != null) {
            final LookWhereYouAreGoing<Vector2> lookWhereYouAreGoingSB = new LookWhereYouAreGoing<Vector2>(
                    this) //
                    .setTimeToTarget(0.1f) //
                    .setAlignTolerance(0.001f) //
                    .setDecelerationRadius(MathUtils.PI);

            final Arrive<Vector2> arriveSB = new Arrive<Vector2>(this,
                    (SteeringActor) target) //
                    .setTimeToTarget(0.1f) //
                    .setArrivalTolerance(0.001f) //
                    .setDecelerationRadius(80);

            blendedSteering.add(arriveSB, 1f) //
                    .add(lookWhereYouAreGoingSB, 1f);
        } else {
        }
    }
}
