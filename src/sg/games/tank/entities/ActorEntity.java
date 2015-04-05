package sg.games.tank.entities;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.corex.stage.Actor;

/**
 * Entity with simpliest spatial infos: texture, position, rotation, boundbox,
 * radius.
 *
 * @author cuong.nguyen
 *
 */
public abstract class ActorEntity extends Actor {

    SpatialEntity entity;
    protected Vector2 position;
    protected float boundingRadius = Float.MIN_VALUE;
    protected boolean tagged;
    protected boolean independentFacing;

    public ActorEntity() {
        super();
    }

//	public TextureRegion getTextureRegion() {
//		return textureRegion;
//	}
//
//	public void setTextureRegion(TextureRegion region) {
//		this.textureRegion = region;
//	}
    public Vector2 getPosition() {
        return position;
    }

    public float getOrientation() {
        return getRotation() * MathUtils.degreesToRadians;
    }

    protected void updateBounding() {
    }

    public void setBoundingRadius(float boundingRadius) {
        this.boundingRadius = boundingRadius;
    }

    public float getBoundingRadius() {
        return boundingRadius;
    }

    public boolean isTagged() {
        return tagged;
    }

    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    public Vector2 newVector() {
        return new Vector2();
    }

    public float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);
        return outVector;
    }

    public boolean isIndependentFacing() {
        return independentFacing;
    }

    public void setIndependentFacing(boolean independentFacing) {
        this.independentFacing = independentFacing;
    }

    @Override
    public void act(float delta) {
        //FIXME: Let entity and actor sync
        //		position.set(getX(Align.center), getY(Align.center));
        update(delta);
        super.act(delta);

    }

    abstract public void update(float delta);

    public SpatialEntity getEntity() {
        return entity;
    }

    public int getId() {
        return (int) entity.getIid();
    }

    protected void setId(int id) {
        entity.setIid(id);
    }

    public int getType() {
        return (int) entity.getIid();
    }
//	public int getStatus() {
//		return status;
//	}
//
//	public void setStatus(int status) {
//		this.status = status;
//	}
}