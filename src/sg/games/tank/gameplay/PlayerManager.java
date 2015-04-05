/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.tank.gameplay;

import java.util.ArrayList;
import java.util.List;
import sg.games.tank.TankBattleMain;

/**
 *
 * @author cuongnguyen
 */
public class PlayerManager {

    private TankBattleMain app;

    public PlayerManager(TankBattleMain app) {
        this.app = app;
    }

    public Player createMockPlayer(boolean isAI) {
        Player newPlayer = new Player(app);
        newPlayer.setAI(isAI);
        return newPlayer;
    }

    public Player getLocalPlayer() {
        return new Player(app);
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>();
    }
}
