package sg.games.tank.entities;

import static sg.atom.corex.stage.actions.Actions.*;
import static sg.atom.corex.stage.actions.Actions.sequence;

import java.util.ArrayList;


import com.badlogic.gdx.math.Vector2;
import com.jme3.animation.Animation;
import sg.atom.corex.managers.EffectManager;
import sg.atom.corex.stage.actions.MoveToAction;

/**
 * An Entity represent attack of units FIXME: Fix to extends SteeringActor
 * instead.
 *
 * @author cuong.nguyen
 *
 */
public class Projectile extends ActorEntity {

//    Sprite bulletSprite, impactSprite, explosionSprite;
    Animation explosionAnim;
    private EffectManager effectManager;
    int bulletType;
    int damage;
    boolean followTarget = false;
    Unit target;
    // Trajectory
    // Trail
    int trailNum = 4;
    ArrayList<Vector2> trailPos;
    private float activeTime;
    private float trailTime = 0.05f;
    private float lastTrailTime = 0;
    private float duration = 0.4f;

    public Projectile(int type) {
        this.bulletType = type;
    }

    public void init() {
//        this.effectManager = app.getEffectManager();

//        this.bulletSprite = effectManager.createSprite("bullet"
//                + this.bulletType);
//        this.impactSprite = effectManager.createSprite("explosion2");
//        this.explosionSprite = effectManager.createSprite("explosion1");

        this.trailPos = new ArrayList<Vector2>();

        this.activeTime = 0;
        this.damage = 20;
    }

    public void impact() {
//        bulletSprite = null;
        addAction(sequence(scaleTo(0.2f, 0.2f, 0), scaleTo(1, 1, 0.1f), scaleTo(0.2f, 0.2f, 0.1f),
                removeActor()));
    }

    public void miss() {
    }

    public void explode() {
        // explosionAnim.
//        bulletSprite = null;
//        impactSprite = null;
        addAction(sequence(scaleTo(0.2f, 0.2f, 0), scaleTo(1, 1, 0.6f), scaleTo(0.2f, 0.2f, 0.1f),
                removeActor()));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        activeTime += delta;
    }

    public void shoot(final Vector2 from, final Vector2 target) {

        MoveToAction moveToTargetAction;
        this.setPosition(from.x, from.y);
        moveToTargetAction = new MoveToAction() {
            @Override
            protected void end() {
                super.end();
                impact();
            }
        };
        moveToTargetAction.setPosition(target.x, target.y);
        moveToTargetAction.setDuration(duration);
        addAction(moveToTargetAction);
        face(target);
    }

    public void shoot(final Unit from, final Unit target) {
        final Projectile bullet = this;
        MoveToAction moveToTargetAction;

        moveToTargetAction = new MoveToAction() {
            @Override
            protected void end() {
                super.end();
//				Gdx.app.log("Projectile", "end");
                bullet.impact();
                target.onImpact(bullet);
            }
        };
        moveToTargetAction.setPosition(target.getX(), target.getY());
        moveToTargetAction.setDuration(duration);
        bullet.addAction(moveToTargetAction);
        bullet.face(target.getPosition());
    }

    public void face(Vector2 target) {
        Vector2 currentPos = new Vector2(this.getX(), this.getY());
        float angle = currentPos.sub(target).angle();
        this.setRotation(angle);
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//
//        if (bulletSprite != null) {
//            Vector2 newPos = new Vector2(this.getX() - bulletSprite.getWidth()
//                    / 2, this.getY() - bulletSprite.getHeight() / 2);
//            bulletSprite.setPosition(newPos.x, newPos.y);
//            bulletSprite.setRotation(this.getRotation());
//            bulletSprite.draw(batch);
//
//            // Draw the trail
//            if (activeTime - lastTrailTime > trailTime) {
//                lastTrailTime = activeTime;
//                trailPos.add(newPos);
//            } else {
//            }
//            if (trailPos.size() > trailNum) {
//                trailPos.remove(0);
//            }
//            float alpha = 0;
//            for (Vector2 v : trailPos) {
//                alpha += 1.0 / trailNum;
//                bulletSprite.setPosition(v.x, v.y);
//                bulletSprite.draw(batch, alpha);
//            }
//        } else if (impactSprite != null) {
//            impactSprite.setPosition(this.getX() - impactSprite.getWidth() / 2,
//                    this.getY() - impactSprite.getHeight() / 2);
//            impactSprite.setScale(this.getScaleX(), this.getScaleY());
//            impactSprite.draw(batch, 0.8f);
//        } else {
//            explosionSprite.setPosition(
//                    this.getX() - explosionSprite.getWidth() / 2, this.getY()
//                    - explosionSprite.getHeight() / 2);
//            explosionSprite.setScale(this.getScaleX(), this.getScaleY());
//            explosionSprite.draw(batch, 0.8f);
//        }
//    }
    public int getDamage() {
        return damage;
    }

    public float getDuration() {
        return duration;
    }

    @Override
    public void update(float delta) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
