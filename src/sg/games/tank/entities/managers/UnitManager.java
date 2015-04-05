package sg.games.tank.entities.managers;

import java.util.ArrayList;
import java.util.HashSet;

import sg.games.tank.entities.ActorEntity;
import sg.games.tank.entities.SteeringActor;
import sg.games.tank.entities.Unit;
import sg.games.tank.gameplay.Country;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.jme3.texture.Texture;
import sg.atom.corex.stage.Stage;
import sg.games.tank.TankBattleMain;
import sg.games.tank.entities.factories.UnitFactory;
import sg.games.tank.entities.factories.UnitType;
import tonegod.gui.core.layouts.LayoutHint.Align;
import tonegod.gui.framework.core.TextureRegion;

/**
 * UnitManager - Creating: - Managing: - Steering: - PathFinding: - Interacting:
 * - Saving states:
 * 
 * @author cuong.nguyen
 * 
 */
public class UnitManager {
	private TankBattleMain app;
	private UnitFactory unitFactory;
//	private ActorEntity target;

	// Units
	private ArrayList<Unit> units;

	public UnitManager(TankBattleMain app) {
		this.app = app;
	}

	public void init() {
		this.unitFactory = new UnitFactory();
		this.units = new ArrayList<Unit>();
	}

	public void createEntities(Stage stage, Country country) {
	}

	public void createUnits(Country country) {

	}

	public Unit createUnit(Country country, Stage stage, UnitType type) {
		Unit newUnit = unitFactory.createUnit(type, 1);
		this.units.add(newUnit);
		stage.addActor(newUnit);

		newUnit.setUnitManager(this);
		newUnit.letActiveUnit(country, stage);
//		newUnit.setDebug(true);
		newUnit.setShowDebugInfo(false);
		return newUnit;
	}
//
//	public void createTarget(Stage stage) {
//		float w = stage.getWidth();
//		float h = stage.getHeight();
//		// Create target
//		target = new SteeringActor(new TextureRegion(new Texture(
//				Gdx.files.internal("graphics/icons/target.png"))));
//		target.setPosition(MathUtils.random(w), MathUtils.random(h),
//				Align.center);
//
//		stage.addActor(target);
//	}
//
//	public void linkTarget(Unit unit, ActorEntity target) {
//		// ((Tank) tanks.get(0)).setTarget(target);
//	}
//
//	public void setTargetPosition(int screenX, int screenY) {
//
//		Vector2 newPos = target.getStage().screenToStageCoordinates(
//				new Vector2(screenX, screenY));
//		target.setPosition(newPos.x, newPos.y, Align.center);
//	}

	public UnitFactory getUnitFactory() {
		return unitFactory;
	}

	// Filters
	public Unit getNearestUnit(Unit unit) {
		float minDis = Float.MAX_VALUE;
		Unit result = null;
		for (Unit u : units) {
			if (u == unit || u.getHealth() <= 0)
				continue;
			float dst = u.getPosition().dst(unit.getPosition());
			if (dst < minDis) {
				minDis = dst;
				result = u;
			}
		}
		return result;
	}

	public Unit getNearestUnitEnemy(Unit unit) {
		float minDis = Float.MAX_VALUE;
		Unit result = null;
		for (Unit u : units) {
			if (u == unit || u.getHealth() <= 0
					|| !unit.getCountry().isEnemy(u))
				continue;
			float dst = u.getPosition().dst(unit.getPosition());
			if (dst < minDis) {
				minDis = dst;
				result = u;
			}
		}
		return result;
	}

	public Unit getNearestUnitInRange(Unit unit) {
		float minDis = Float.MAX_VALUE;
		Unit result = null;
		for (Unit u : units) {
			if (u == unit || u.getHealth() <= 0)
				continue;
			float dst = u.getPosition().dst(unit.getPosition());
			if (dst < minDis && dst < unit.getLookRange()) {
				minDis = dst;
				result = u;
			}
		}
		return result;
	}

	public Unit getNearestUnitEnemyInRange(Unit unit) {
		float minDis = Float.MAX_VALUE;
		Unit result = null;
		for (Unit u : units) {
			if (u == unit || u.getHealth() <= 0 || unit.getCountry() != null
					&& !unit.getCountry().isEnemy(u))
				continue;
			float dst = u.getPosition().dst(unit.getPosition());
			if (dst < minDis && dst < unit.getLookRange()) {
				minDis = dst;
				result = u;
			}
		}
		return result;
	}

	public Unit getRandomUnit(Unit unit) {
		Unit result = null;
		int index = -1;
		HashSet<Unit> unitSet = new HashSet<Unit>(units);

		do {
			index = MathUtils.random(units.size() - 1);
			result = units.get(index);
			unitSet.remove(result);
		} while (index == units.indexOf(unit) || result.getHealth() <= 0
				&& !unitSet.isEmpty());

		return result;
	}
}
