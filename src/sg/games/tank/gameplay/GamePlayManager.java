package sg.games.tank.gameplay;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import sg.atom.ai.agents.Agent;
import sg.atom.ai.agents.Team;
import sg.atom.ai.agents.control.AgentsAppState;
import sg.atom.core.lifecycle.AbstractManager;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.core.AtomMain;
import sg.atom.corex.entity.EntityFactory;
import sg.atom.corex.entity.EntityManager;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.corex.managers.WorldManager;
import static sg.atom.corex.managers.WorldManager.createRandomPos;
import sg.games.tank.ai.behaviours.AIAttackBehaviour;
import sg.games.tank.ai.behaviours.AIMainBehaviour;
import sg.games.tank.entities.Unit;
import sg.games.tank.stage.cam.RTSCamera;

/**
 * GamePlayManager represent "GamePlay" and "Player" because we only have one
 * Mode!
 *
 * FIXME:
 *
 * @author cuong.nguyen
 */
public class GamePlayManager extends AbstractManager {

    public static final Logger logger = LoggerFactory.getLogger("GamePlayManager");
    //Wave
    int waveCount = 0;
    int waveIndex = 0;
    int waveNum = 4;
    int waveEach = 10;
    float waveTime = 1;
    //Gameplay
    int mode;
    int health = 100, maxHP = 100;
    int life = 5;
    int score = 0;
    int scoreKilled = 0;
    float timePassed = 0, totalTime = 100;
    //Gun
    int ammo = 100, maxAmmo = 100;
    int gunType;
    int gunStatus;
    float gunRange;
    ArrayList<GameLevel> levels;
    GameLevel currentLevel;
    Wave currentWave;
    AgentsAppState agentsAppState;
    private Team team1;
    private Team team2;

    public GamePlayManager(AtomMain app) {
        super(app);
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);
    }

    public void setupInput() {
        //Mouse
        //Key
        inputManager = app.getInputManager();
        inputManager.addMapping("Shoot",
                new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        inputManager.addMapping("Reload",
                new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(actionListener, "Shoot", "Reload");
//        inputManager.addListener(analogListener, "Shoot");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equals("Shoot") && !pressed) {
                shoot();
//                logger.info("Shoot action");
            } else if (name.equals("Reload") && !pressed) {
                reload();
            }
        }
    };
    private AnalogListener analogListener = new AnalogListener() {
        public void onAnalog(String name, float intensity, float tpf) {
            if (name.equals("Shoot")) {
            }
        }
    };

    public void load() {
    }

    public void startGame() {
        agentsAppState = AgentsAppState.getInstance();
        agentsAppState.setApp(app);
        RTSCamera camControler = app.getStateManager().getState(RTSCamera.class);
        camControler.setCenter(new Vector3f(10, 0, 10));
        team1 = new Team("Team1");
        team2 = new Team("Team2");
        startWave();
        setupInput();
        agentsAppState.start();
    }

    void restartGame() {
    }

    boolean checkEndGame() {
        return false;
    }

    void loadLevels() {
    }

    void loadLevel(GameLevel level) {
    }

    void createEntities(GameLevel level) {
    }

    //Collision checking
    void shoot() {
        ammo--;
//        logger.info("Shoot!");
        CollisionResults results = new CollisionResults();
        Camera cam = app.getCamera();
        Node boxes = app.getWorldManager().getCollisionNode();
        Ray ray = new Ray(cam.getLocation(), cam.getDirection());
        boxes.collideWith(ray, results);
//        for (int i = 0; i < results.size(); i++) {
//            float dist = results.getCollision(i).getDistance();
//            Vector3f pt = results.getCollision(i).getContactPoint();
//            logger.info("Collision: " + results.getCollision(i).getGeometry().getName());
//        }

        if (results.size() > 0) {
            Geometry target = results.getClosestCollision().getGeometry();
            Vector3f contactPoint = results.getClosestCollision().getContactPoint();
            SpatialEntity entity = app.getEntityManager().getEntityFrom(target);
            if (entity != null) {
                hit(entity);
                hit(target, contactPoint);
            } else {
            }

        }
    }

    void hit(Geometry target, Vector3f contactPoint) {
        if (target != null) {

            ParticleEmitter explosion = getApp().getEffectManager().createExplosion();
            getApp().getWorldManager().putSpatial(explosion, contactPoint);
            explosion.emitAllParticles();
            score++;
//            logger.info("Hit target!");
            //            target.removeFromParent();
        }
    }

    void hit(SpatialEntity entity) {
//        logger.info("Hit entity" + entity.getIid());
        app.getEntityManager().removeEntity(entity);
    }

//    void hit()
    void shootCheck() {
    }

    void reload() {
        ammo = maxAmmo;
    }
//
//    void updateEntities() {
//    }
//    void updateWave() {
//    }

//    void nextWave() {
//    }
    public SpatialEntity spawn(String type, Vector2f pos) {
        WorldManager worldManager = app.getWorldManager();
        EntityManager entityManager = app.getEntityManager();
        EntityFactory factory = entityManager.getEntityFactory();

        SpatialEntity newEntity = (SpatialEntity) factory.create(type);
        worldManager.putEntity(worldManager.getGroupNode(type), newEntity, pos);
        entityManager.addEntity(newEntity);
        newEntity.init(app);
        return newEntity;
    }

    void startWave() {

        for (int c = 0; c < 20; c++) {
            Unit soldier = (Unit) spawn("Soldier", createRandomPos(100, 3));

            Agent<Unit> unitAgent = soldier.getAgent();
            agentsAppState.addAgent(unitAgent);
            unitAgent.setTeam(team1);
            unitAgent.setVisibilityRange(100f);

            unitAgent.setMass(1);
            unitAgent.setMoveSpeed(2.0f);
            unitAgent.setRotationSpeed(1.0f);
            unitAgent.setMaxForce(3);
//            unitAgent.setAcceleration(Vector3f.ZERO);
            unitAgent.setMainBehaviour(new AIMainBehaviour(unitAgent));

        }

        for (int c = 0; c < 10; c++) {
            Unit tower = (Unit) spawn("Tower", createRandomPos(100, 100));
            Agent<Unit> unitAgent = tower.getAgent();
            agentsAppState.addAgent(unitAgent);
            unitAgent.setTeam(team2);
            unitAgent.setAcceleration(Vector3f.ZERO);
            unitAgent.setVisibilityRange(20f);
            unitAgent.setMainBehaviour(new AIAttackBehaviour(unitAgent));
        }
    }

    void endWave() {
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
        agentsAppState.update(tpf);
//        logger.info("Gameplay update");
//        System.out.println("Gameplay update");
    }

    //GETTER & SETTER
    public int getAmmo() {
        return ammo;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }
}
