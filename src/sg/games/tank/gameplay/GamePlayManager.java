package sg.games.tank.gameplay;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
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
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sg.atom.core.AtomMain;
import sg.atom.corex.entity.EntityFactory;
import sg.atom.corex.entity.EntityManager;
import sg.atom.corex.entity.SpatialEntity;
import sg.atom.corex.managers.WorldManager;
import static sg.atom.corex.managers.WorldManager.createRandomPos;
import sg.atom.corex.stage.Stage;
import sg.games.tank.TankBattleMain;
import sg.games.tank.entities.Unit;
import sg.games.tank.entities.factories.UnitType;
import sg.games.tank.entities.managers.UnitManager;
import sg.games.tank.stage.cam.RTSCamera;

/**
 * GamePlayManager represent "GamePlay" and "Player" because we only have one
 * Mode!
 *
 * FIXME:
 *
 * @author cuong.nguyen
 */
public class GamePlayManager extends AbstractManager implements Disposable {

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
    private TankBattleMain game;
    private ArrayList<Player> players;
    private Match currentMatch;
    private ArrayList<GameLevel> levels;
    private int currentLevelIndex;
    private ArrayList selection;
    private UnitManager unitManager;
    private Player localPlayer;
//    private int mode;
    private Player player1;
    private Player player2;
    private GameLevel currentLevel;
    private LevelManager levelManager;
    private Wave currentWave;
    private AgentsAppState agentsAppState;
    private Team team1;
    private Team team2;

    public GamePlayManager(AtomMain app) {
        super(app);

        game = TankBattleMain.getInstance();
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);
    }

    public void init() {
        setupPlayers();

        setupLevels();

        // managers
        this.unitManager = new UnitManager(game);
        this.unitManager.init();
    }

    void setupPlayers() {
        // create random players
        player1 = game.getPlayerManager().createMockPlayer(false);
        player2 = game.getPlayerManager().createMockPlayer(true);
        player1.setGamePlay(this);
        player2.setGamePlay(this);
        this.localPlayer = player1;
    }

    public void createSampleMatch() {

        createMatch(player1, player2);
    }

    public Match createMatch(Player... players) {

        // create a sample match
        currentMatch = new Match();
        currentMatch.init(players);
        this.players = new ArrayList<Player>();
        this.players.addAll(Arrays.asList(players));
        return currentMatch;
    }

    public void startMatch(Stage stage) {
        currentMatch.start();

        for (Player player : currentMatch.getPlayers()) {
            Country country = new Country();
            country.init(player, currentMatch);

            unitManager.createEntities(stage, country);
            // unitManager.linkTarget(stage);
            country.onStartMatch();
        }

        startWave();
        setupInput();
    }

    public void endMatch() {
    }

    public void spawn(UnitType type, Stage stage, int x, int y) {
        // Player player = game.getGamePlay().getCurrentPlayer();
        List<Player> players = getPlayers();
        Player player = players.get(MathUtils.random(players.size() - 1));
        player.getCountry().createUnit(type, stage, x, y);
    }

    void setupLevels() {
        this.levelManager = new LevelManager(game);
        this.levelManager.init();
        initLevel(levelManager.getCurrentLevel());

    }

    void initLevel(GameLevel newLevel) {
    }

    void loadLevel() {
    }

    void startLevel() {
    }

    void endLevel() {
    }

    public boolean checkGameState() {
        return false;
    }

    public boolean isEnd() {
        return false;
    }

    public void createCountry(Player player) {
    }

    public void finishLoading() {
        unitManager.getUnitFactory().loadAssets();
//		game.getAssetManager().finishLoading();

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
        RTSCamera camControler = app.getStateManager().getState(RTSCamera.class);
        camControler.setCenter(new Vector3f(10, 0, 10));
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
    }

    void endWave() {
    }

    void sampleWave() {
//        for (int c = 0; c < 20; c++) {
//            Unit soldier = (Unit) spawn("Soldier", createRandomPos(100, 3));
//
//            Agent<Unit> unitAgent = soldier.getAgent();
//            agentsAppState.addAgent(unitAgent);
//            unitAgent.setTeam(team1);
//            unitAgent.setVisibilityRange(100f);
//
//            unitAgent.setMass(1);
//            unitAgent.setMoveSpeed(2.0f);
//            unitAgent.setRotationSpeed(1.0f);
//            unitAgent.setMaxForce(3);
////            unitAgent.setAcceleration(Vector3f.ZERO);
//            unitAgent.setMainBehaviour(new AIMainBehaviour(unitAgent));
//
//        }
//
//        for (int c = 0; c < 10; c++) {
//            Unit tower = (Unit) spawn("Tower", createRandomPos(100, 100));
//            Agent<Unit> unitAgent = tower.getAgent();
//            agentsAppState.addAgent(unitAgent);
//            unitAgent.setTeam(team2);
//            unitAgent.setAcceleration(Vector3f.ZERO);
//            unitAgent.setVisibilityRange(20f);
//            unitAgent.setMainBehaviour(new AIAttackBehaviour(unitAgent));
//        }
    }

//
//    void updateEntities() {
//    }
//    void updateWave() {
//    }
//    void nextWave() {
//    }
    @Override
    public void update(float tpf) {
        super.update(tpf);
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

    public Player getCurrentPlayer() {
        return localPlayer;
    }

    public Match getCurrentMatch() {
        return currentMatch;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public UnitManager getUnitManager() {
        return unitManager;
    }

    public void dispose() {
    }
}
