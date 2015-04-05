package sg.games.tank;

import com.jme3.system.AppSettings;
import sg.atom.core.AtomMain;
import sg.atom.corex.config.DeviceSettings;
import sg.games.tank.states.InGameState;

/**
 * Main class
 */
public class TankBattleMain extends AtomMain {

    public static DeviceSettings deviceSettings = DeviceSettings.Phone;
    /**
     * Singleton reference of DragonDefenseMain.
     */
    private static TankBattleMain instance;

    /**
     * Constructs singleton instance of DragonDefenseMain.
     */
    private TankBattleMain() {
    }

    /**
     * Provides reference to singleton object of DragonDefenseMain.
     *
     * @return Singleton instance of DragonDefenseMain.
     */
    public static synchronized final TankBattleMain getInstance() {
        if (instance == null) {
            instance = new TankBattleMain();
        }
        return instance;
    }

    public static void main(String[] args) {
        TankBattleMain app = new TankBattleMain();
        AppSettings cfg = new AppSettings(true);
//cfg.setFrameRate(60); // set to less than or equal screen refresh rate
//cfg.setVSync(true);   // prevents page tearing
//cfg.setFrequency(60); // set to screen refresh rate
        cfg.setResolution(deviceSettings.width, deviceSettings.height);
//        cfg.setFullscreen(true);
        cfg.setTitle("Dragon Defense");
        app.setDisplayStatView(false);
        app.setShowSettings(false);
        app.setSettings(cfg);
        app.start();
    }

    @Override
    public void initStates() {
//        stateManager.attach(new SplashState());
//        stateManager.attach(new MainMenuState());
        stateManager.attach(new InGameState());

    }
}
