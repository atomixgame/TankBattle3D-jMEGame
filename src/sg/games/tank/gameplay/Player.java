package sg.games.tank.gameplay;

import sg.games.tank.TankBattleMain;

public class Player {

    String name;
    int id;
    int status;
    boolean actived;
    int level;
    int score;
    int point;
    int winGameNum;
    int loseGameNum;
    boolean isAI = false;
    // Game
    TankBattleMain game;
    GamePlayManager gamePlay;
    Country country;
    PlayerProfile profile;

    public Player(TankBattleMain game) {
        this.game = game;
    }

    // Input
    // Inputs & Actions
    public void setTargetPosition(int screenX, int screenY) {
    }

    // SETTER & GETTERS
    public GamePlayManager getGamePlay() {
        return gamePlay;
    }

    public void setGamePlay(GamePlayManager gamePlay) {
        this.gamePlay = gamePlay;
    }

    public void setAI(boolean isAI) {
        this.isAI = isAI;
    }

    public Country getCountry() {
        return country;
    }

    public TankBattleMain getGame() {
        return game;
    }

    public boolean isAI() {
        return isAI;
    }

    public int getLevel() {
        return level;
    }

    public int getPoint() {
        return point;
    }

    public PlayerProfile getProfile() {
        return profile;
    }

    public int getScore() {
        return score;
    }

    public int getStatus() {
        return status;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
