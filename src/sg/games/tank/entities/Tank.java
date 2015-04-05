package sg.games.tank.entities;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.limiters.NullLimiter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jme3.scene.Spatial;
import sg.games.tank.TankBattleMain;

public class Tank extends Unit {

    public static final String ASSET_PATH = "graphics/units/tank/";
    private Spatial bodySprite, gunSprite;
    BlendedSteering<Vector2> blendedSteering;
    private int subType;

    public Tank(int type) {
        super("tank");
        this.subType = type;
    }

    public void init() {
        super.init();


        // FIXME: Make sprites as Data and load from external
//        String assetPathSub = ASSET_PATH + typeName + subType;
//        this.bodyImg = new TextureRegion(app.getAssetManager().get(assetPathSub + "_body.png", Texture.class));
//        this.gunImg = new TextureRegion(app.getAssetManager().get(assetPathSub + "_gun.png", Texture.class));
//        this.bodySprite = new Sprite(bodyImg);
//        this.gunSprite = new Sprite(gunImg);
//        this.setTextureRegion(this.bodyImg);
        this.updateBounding();

        setMaxLinearAcceleration(100);
        setMaxLinearSpeed(100);
        setMaxAngularAcceleration(40);
        setMaxAngularSpeed(15);
        setHealth(200);
        setMaxHealth(200);
        setAttackRange(150);
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

    public void shoot(Unit target) {
        this.pointGunTo(target.getPosition());
        final Projectile bullet = this.createNewProjectile();
        this.getStage().addActor(bullet);
        bullet.shoot(this, target);
    }

    public void shoot(ActorEntity target) {
        this.pointGunTo(target.getPosition());
        this.shoot(target.getPosition());
    }

    public void shoot(Vector2 target) {
        this.pointGunTo(target);
        final Projectile bullet = this.createNewProjectile();
        this.getStage().addActor(bullet);
        bullet.shoot(this.getPosition(), target);
    }

    public void pointGunTo(Vector2 target) {
        float angle = this.getPosition().cpy().sub(target).angle() + 90;
//        this.gunSprite.setRotation(angle);
    }

    public void explode() {
        final Projectile bullet = this.createNewProjectile();
        this.getStage().addActor(bullet);
        bullet.explode();
//        this.gunSprite = null;
    }

    // Create extra products & entities
    Projectile createNewProjectile() {
        int bulletType = MathUtils.random(1);
        final Projectile bullet = TankBattleMain.getInstance().getGamePlayManager().getUnitManager()
                .getUnitFactory().createProjectile(1 + bulletType);
        bullet.setPosition(this.getX(), this.getY());
        return bullet;
    }

    void createMark() {
    }

    // Command
    @Override
    public void letFindTarget() {
        super.letFindTarget();

        if (target != null) {
            pointGunTo(target.getPosition());
        }
    }

    public void letAttack() {
        if (target != null) {
            if (target instanceof Unit) {
                shoot((Unit) target);
            } else {
                shoot(target);
            }
        }
    }

    public void letDie() {
        explode();
        die();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
