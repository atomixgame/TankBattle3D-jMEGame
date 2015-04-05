package sg.games.tank.entities;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * A SteeringActor is a scene2d {@link Actor} implementing the {@link Steerable}
 * interface.
 *
 * @autor davebaol
 */
public class SteeringActor extends ActorEntity implements Steerable<Vector2> {

    static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(
            new Vector2());
    // Movement
    Vector2 linearVelocity;
    float angularVelocity;
    float maxLinearSpeed = 100;
    float maxLinearAcceleration = 200;
    float maxAngularSpeed = 5;
    float maxAngularAcceleration = 10;
    boolean movable = true;
    SteeringBehavior<Vector2> steeringBehavior;

    public SteeringActor() {
        this(1, false);
    }

    public SteeringActor(float radius) {
        this(radius, false);
    }

    public SteeringActor(float radius, boolean independentFacing) {
        this.independentFacing = independentFacing;
        this.position = new Vector2();

        this.linearVelocity = new Vector2();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    public SteeringBehavior<Vector2> getSteeringBehavior() {
        return steeringBehavior;
    }

    public void setSteeringBehavior(SteeringBehavior<Vector2> steeringBehavior) {
        this.steeringBehavior = steeringBehavior;
    }

    // the display area is considered to wrap around from top to bottom
    // and from left to right
    protected static void wrapAround(Vector2 pos, float maxX, float maxY) {
        if (pos.x > maxX) {
            pos.x = 0.0f;
        }

        if (pos.x < 0) {
            pos.x = maxX;
        }

        if (pos.y < 0) {
            pos.y = maxY;
        }

        if (pos.y > maxY) {
            pos.y = 0.0f;
        }
    }

    public void act(float delta) {

        if (steeringBehavior != null && this.boundingRadius > 0) {

            // Calculate steering acceleration
            steeringBehavior.calculateSteering(steeringOutput);

            /*
             * Here you might want to add a motor control layer filtering
             * steering accelerations.
             * 
             * For instance, a car in a driving game has physical constraints on
             * its movement: it cannot turn while stationary; the faster it
             * moves, the slower it can turn (without going into a skid); it can
             * brake much more quickly than it can accelerate; and it only moves
             * in the direction it is facing (ignoring power slides).
             */

            // Apply steering acceleration
            applySteering(steeringOutput, delta);

            // wrapAround(position, getParent().getWidth(), getParent()
            // .getHeight());
            setPosition(position.x, position.y);
        }
        super.act(delta);
    }

    void applySteering(SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum
        // speed
        position.mulAdd(linearVelocity, time);
        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());

        // Update orientation and angular velocity
        if (independentFacing) {

            setRotation(getRotation() + (angularVelocity * time)
                    * MathUtils.radiansToDegrees);
            angularVelocity += steering.angular * time;
        } else {
            // If we haven't got any velocity, then we can do nothing.
            if (!linearVelocity.isZero(MathUtils.FLOAT_ROUNDING_ERROR)) {
                float newOrientation = vectorToAngle(linearVelocity);
                angularVelocity = (newOrientation - getRotation()
                        * MathUtils.degreesToRadians)
                        * time; // this is superfluous if independentFacing is
                // always true
                setRotation(newOrientation * MathUtils.radiansToDegrees);
                setViewDirection(newOrientation);
            }
        }
    }

    public void setViewDirection(float newOrientation) {
//		if (usingAnimation){
//			this.animations.setDirection(Directions.fromOrientation(newOrientation));
//		}
    }

    // public void separateSteer() {
    //
    // }
    public boolean isMovable() {
        return movable;
    }

    public void setOrientation(float orientation) {
    }

    public Location<Vector2> newLocation() {
        return new Location<Vector2>() {
            public Vector2 getPosition() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public float getOrientation() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public void setOrientation(float orientation) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public float vectorToAngle(Vector2 vector) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public Vector2 angleToVector(Vector2 outVector, float angle) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            public Location<Vector2> newLocation() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
    }

    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    public void setZeroLinearSpeedThreshold(float value) {
    }

    @Override
    public void update(float delta) {
    }
}
