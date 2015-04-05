package sg.games.tank.entities;

import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import static sg.atom.corex.stage.actions.Actions.*;

import java.util.List;

import sg.games.tank.entities.managers.UnitManager;
import sg.games.tank.gameplay.Country;
import sg.atom.corex.entity.EntityFunction;
import sg.atom.corex.stage.Action;
import sg.atom.corex.stage.Stage;
import sg.games.tank.ai.states.CommonStates;
import sg.games.tank.ai.states.FrequentStateMachine;

/**
 * Base class of unit in RTS game.
 *
 * - Use FSM for AI. This is a hybrid method. You can add custom behaviors by
 * override public methods. - Use Steering for low level movement - - Poolable
 *
 * @author cuong.nguyen
 *
 */
public class Unit extends SteeringActor {
//    RobotAttack app;
    
    String typeName;
    Country country;
    
    UnitManager unitManager;
    List<EntityFunction> functions;
//    List<Skill> skills;
    int unitType;
    int vehicleType;
    boolean floating = false;
    boolean active = false;
    // Dependencies
    int[] requiredUnitIds;
    int[] requiredSkillIds;
    int[] requiredTokens;
    // Health
    public int health;
    int maxHealth;
    int energy;
    int maxEnergy;
    // Point
    int createdUnitNum;
    int destroyedUnitNum;
    int totalPoint;
    // Resource
    int foodCarry;
    int egnergyCarry;
    // Cost
    int populationCost;
    int energyCost;
    int moneyCost;
    // Combat
    public float lookRange = 400;
    public float attackRange = 200;
    // Timing
    public float preactiveDuration = 1;
    float activeTime = 0;
    public float shootInterval = 2;
    // UI
//	private HealthBar healthBar;
    private boolean showHealthBar = false;
    // AI
    protected ActorEntity target;
    // FSM
    public FrequentStateMachine<Unit> commonFSM;
    // protected FrequentStateMachine<Unit> movementFSM;
    public FrequentStateMachine<Unit> combatFSM;
    public FrequentStateMachine<Unit> actionFSM;
    protected Stage assignStage;
    public float deltaTime;
//    private DebugInfo debugInfo;
    protected boolean showDebugInfo = true;

    public Unit(String typeName) {
        super();
        this.typeName = typeName;
    }

    public Unit(int iid, String typeName, Spatial spatial){
        
    }
    public boolean canAttack() {
        return true;
    }

    public void init() {
    }

    public void load() {
    }

    public void attack(Unit target) {
    }

    public void invoke(EntityFunction func) {
    }

    public void destroyUnit() {
    }

    public void onAttacked(Unit from) {
    }

    public void onImpact(Projectile bullet) {
        this.health -= bullet.getDamage();
        // Gdx.app.log("Unit", "damage ");
    }

    public void die() {
        deactiveUnit();
        this.addAction(sequence(fadeOut(0.1f), removeActor(), new Action() {
            @Override
            public boolean act(float delta) {
                return false;
            }
        }));
    }

    // FIXME: Move to State enter ?
    public void preactiveUnit(Country country, Stage stage) {
        this.country = country;
        this.country.changePopulation(1);
        this.assignStage = stage;
    }

    public void activeUnit() {
//		createUnitUI(this.assignStage);
        // this.movementFSM = new FrequentStateMachine<Unit>(this,
        // MovementStates.Stop);
        this.actionFSM = new FrequentStateMachine<Unit>(this);
        this.combatFSM = new FrequentStateMachine<Unit>(this);
    }

    public void activeUnit(Country country, Stage stage) {
//		createUnitUI(stage);
    }

    public void deactiveUnit() {
        // this.active = false;
        // this.activeTime = 0;
        this.country.changePopulation(-1);

        removeUnitUI();
    }

    public void createUnitUI(Stage stage) {
//		if (showHealthBar) {
//			this.healthBar = new HealthBar(this);
//			stage.addActor(healthBar);
//		}
//		if (showDebugInfo) {
//			this.debugInfo = new DebugInfo(this);
//			this.debugInfo.create(stage);
//		}
    }

    public void removeUnitUI() {
//		if (showHealthBar) {
//			this.healthBar.remove();
//		}
//		if (showDebugInfo) {
//			this.debugInfo.remove();
//		}
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        // FIXME: Timing should be inject somehow?
        // if (this.commonFSM.getCurrentState() == CommonStates.Active) {
        // this.activeTime += delta;
        // }
        // ((CommonStates) commonFSM.getCurrentState()).updateStateTime(delta);
        this.deltaTime = delta;
        commonFSM.update(delta);
    }

    // Commands pattern to expand behaviors by override (subclass)
    public void letActiveUnit(Country country, Stage stage) {
        this.preactiveUnit(country, stage);
        this.setColor(new ColorRGBA(country.getColor()));

        // Create Unit AI
        this.commonFSM = new FrequentStateMachine<Unit>(this,
                CommonStates.Preactive);
    }

    public void letMoveToTarget() {
        // Not move is default
    }

    public void letStopMoving() {
        this.setSteeringBehavior(null);
    }

    public void letFindTarget() {
        Unit nearestTarget = unitManager.getNearestUnitEnemyInRange(this);
        this.target = nearestTarget;

    }

    public void letAttack() {
    }

    public void letDefense() {
    }

    public void letMove() {
    }

    public void letGuard() {
    }

    public void letDie() {
    }

    // SETTER & GETTER
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public List<EntityFunction> getFunctions() {
        return functions;
    }

    public void setUnitManager(UnitManager unitManager) {
        this.unitManager = unitManager;
    }

    public float getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(float attackRange) {
        this.attackRange = attackRange;
    }

    public float getLookRange() {
        return lookRange;
    }

    public void setLookRange(float lookRange) {
        this.lookRange = lookRange;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setShowHealthBar(boolean showHealthBar) {
        this.showHealthBar = showHealthBar;
    }

    public void setShowDebugInfo(boolean showDebugInfo) {
        this.showDebugInfo = showDebugInfo;
    }

    public ActorEntity getTarget() {
        return target;
    }

    public void setTarget(ActorEntity target) {
        this.target = target;
    }

    public FrequentStateMachine<Unit> getCommonFSM() {
        return commonFSM;
    }

    public FrequentStateMachine<Unit> getCombatFSM() {
        return combatFSM;
    }

    public FrequentStateMachine<Unit> getActionFSM() {
        return actionFSM;
    }
}
