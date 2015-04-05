/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.tank.gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.file.Json;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import sg.games.tank.TankBattleMain;

/**
 *
 * @author cuongnguyen
 */
public class LevelManager {

    private TankBattleMain game;
    private List<Map> maps;
    private List<GameLevel> levels;
    private int currentLevelIndex;

    /**
     * Creates the level manager.
     */
    public LevelManager(TankBattleMain game) {
        this.game = game;
    }

    public void init() {
        // register the levels
        levels = new ArrayList<GameLevel>(10);
        loadJson();
    }

    public void load() {
//		GameLevel level1 = new GameLevel(game);
//		level1.setName("Mars mission");
//		level1.setDescription("Mars mission");
//		level1.setIcon("graphics/levels/Level01.png");
//
//		GameLevel level2 = new GameLevel(game);
//		level2.setName("Planet Surious");
//		level2.setDescription("Planet Surious");
//		level2.setIcon("graphics/levels/Level02.png");
//
//		GameLevel level3 = new GameLevel(game);
//		level3.setName("Homeland");
//		level3.setDescription("Homeland");
//		level3.setIcon("graphics/levels/Level03.png");
//
//		//
//		levels.add(level1);
//		levels.add(level2);
//		levels.add(level3);
    }

    public static class LevelArray {

        Array<GameLevel> levels;
    }

    void loadJson() {
        Reader jsonReader = Gdx.files.internal("data/levels/levels.json").reader();
        Json json = new Json();
        LevelArray levelArray = json.fromJson(LevelArray.class, jsonReader);

        for (int i = 0; i < levelArray.levels.size; i++) {
            GameLevel level = levelArray.levels.get(i);
            levels.add(level);
        }
    }

    public List<GameLevel> getLevels() {
        return levels;
    }

    /**
     * Retrieve all the available levels.
     */
    public List<Map> getMaps() {
        return maps;
    }

    // /**
    // * Retrieve the level with the given id, or <code>null</code> if no such
    // * level exist.
    // */
    // public Map findMapById(int id) {
    // if (id < 0 || id >= levels.size()) {
    // return null;
    // }
    // return levels.get(id);
    // }
    public int getCurrentLevelIndex() {
        return currentLevelIndex;
    }

    public GameLevel getCurrentLevel() {
        return levels.get(currentLevelIndex);
    }

    public void setCurrentLevelIndex(int currentLevelIndex) {
        this.currentLevelIndex = currentLevelIndex;
    }
}
