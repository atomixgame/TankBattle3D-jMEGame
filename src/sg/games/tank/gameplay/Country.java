package sg.games.tank.gameplay;

import java.util.ArrayList;

import sg.games.tank.entities.Building;
import sg.games.tank.entities.Unit;
import sg.games.tank.entities.UnitGroup;

import com.badlogic.gdx.math.Vector2;
import com.jme3.math.ColorRGBA;
import sg.atom.corex.stage.Stage;
import sg.games.tank.entities.factories.UnitType;


public class Country {
	
	int id;
	String name;
	Player player;
	GamePlayManager gamePlay;
	Race race;
	ArrayList<Building> buildingList;
	ArrayList<Unit> units;
	ArrayList<UnitGroup> group;

	int level;
	int status;
	ColorRGBA color;

	// Resources
	int population = 0;
	int maxPopulation = 200;
	int totalPopulation;
	int food;
	int maxFood;
	int totalFood;
	int money;
	int totalMoney;
	int energy;
	
	
	public void init(Player player, Match currentMatch) {
		this.player = player;
		this.population = 0;
		this.maxPopulation = 200;

		// FIXME: Player only has one country and can not change it till match
		// end!
		this.gamePlay = player.getGamePlay();
		player.setCountry(this);
		this.color = currentMatch.getPlayerSettings().get(player).getColor();
	}

	public void create() {
		
		// player settings for country

		// create country initial info
	}

	public void createUnit(UnitType unitType, Stage stage, int screenX, int screenY) {
		Unit unit = gamePlay.getUnitManager().createUnit(this, stage, unitType);
		
		Vector2 newPos = stage.screenToStageCoordinates(
				new Vector2(screenX, screenY));
		unit.setPosition(newPos.x, newPos.y);
	}
	
	public void onStartMatch() {
		// create the main house

	}

	public void buyUnit() {

	}

	public void buildUnit() {

	}

	public void buyBuilding() {

	}

	public void buildBuilding() {

	}

	public boolean isEnemy(Unit unit) {
		Player opponent = unit.getCountry().getPlayer();
		return this.player.getGamePlay().getCurrentMatch()
				.isEnemy(this.getPlayer(), opponent);
	}

	public int getPopulation() {
		return population;
	}

	public int getMaxPopulation() {
		return maxPopulation;
	}

	public void changePopulation(int i) {
		population += i;
	}

	public ColorRGBA getColor() {
		return color;
	}

	public Player getPlayer() {
		return player;
	}
}
