package sg.games.tank.gameplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import com.badlogic.gdx.math.MathUtils;
import com.jme3.math.ColorRGBA;
import sg.atom.ai.agents.Team;
import sg.games.tank.TankBattleMain;

public class Match {

    public static enum Difficult {

        Easy(0), Normal(1), Hard(2), Insane(3);
        private int order;

        private Difficult(int order) {
            this.order = order;
        }
    }

    public static class MatchPlayerSetting {

        ColorRGBA color;
        Race race;
        public static ColorRGBA[] COLORS = new ColorRGBA[]{ColorRGBA.Blue, ColorRGBA.Cyan,
            ColorRGBA.Gray, ColorRGBA.Green, ColorRGBA.Orange, ColorRGBA.Red, ColorRGBA.Pink,
            ColorRGBA.Magenta};

        public ColorRGBA getColor() {
            return color;
        }

        public Race getRace() {
            return race;
        }
    }
    // public class MatchSetting{
    //
    // }
    int id;
    Player host;
    Map map;
    int status;
    float speed;
    private ArrayList<Player> players;
    // Settings
    Difficult difficult;
    int maxPlayer;
    int maxTeam;
    ArrayList<Team> teams;
    // Each player settings
    HashMap<Player, MatchPlayerSetting> playerSettings;

    // Startup settings for all players
    public Match() {
    }

    public void start() {
    }

    public void init(Player... players) {
        // map
        this.map = new Map();
        this.map.init();
        // players
        this.players = new ArrayList<Player>();
        this.players.addAll(Arrays.asList(players));
        this.playerSettings = new HashMap<Player, MatchPlayerSetting>();
        // countries
        ArrayList<ColorRGBA> avaiableColors = new ArrayList<ColorRGBA>(
                Arrays.asList(MatchPlayerSetting.COLORS));
        for (Player p : this.players) {
            MatchPlayerSetting playerSetting = new MatchPlayerSetting();

            playerSetting.color = avaiableColors.remove(MathUtils
                    .random(avaiableColors.size() - 1));

            playerSetting.race = Race.values()[MathUtils
                    .random(Race.values().length - 1)];
            this.playerSettings.put(p, playerSetting);
        }
    }

    public int getId() {
        return id;
    }

    public Difficult getDifficult() {
        return difficult;
    }

    public TankBattleMain getGame() {
        return TankBattleMain.getInstance();
    }

    public Player getHost() {
        return host;
    }

    public Map getMap() {
        return map;
    }

    public int getStatus() {
        return status;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public HashMap<Player, MatchPlayerSetting> getPlayerSettings() {
        return playerSettings;
    }

    public boolean isEnemy(Player player1, Player player2) {
        // FIXME : Get policy and team
        return player1 != player2;
    }

    public boolean isAlly(Player player1, Player player2) {
        // FIXME : Get policy and team
        return player1 == player2;
    }
}
