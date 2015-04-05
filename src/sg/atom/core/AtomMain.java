/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.ServiceManager;
import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;
import com.jme3.system.AppSettings;
import java.util.concurrent.Executors;
import org.apache.commons.configuration.Configuration;
import sg.atom.corex.assets.sprite.SpriteSheetLoader;
import sg.atom.corex.config.DeviceInfo;
import sg.atom.corex.entity.EntityManager;
import sg.atom.core.lifecycle.IGameCycle;
import sg.atom.corex.managers.EffectManager;
import sg.atom.corex.managers.GUIManager;
import sg.games.tank.gameplay.GamePlayManager;
import sg.atom.corex.managers.MaterialManager;
import sg.atom.corex.managers.SoundManager;
import sg.atom.corex.managers.StageManager;
import sg.atom.corex.managers.WorldManager;
import sg.games.tank.gameplay.PlayerManager;

/**
 *
 * @author cuong.nguyen
 */
public class AtomMain extends SimpleApplication implements IGameCycle {

    protected StageManager stageManager;
    protected GUIManager guiManager;
    protected PlayerManager playerManager;
    protected GamePlayManager gamePlayManager;
    protected WorldManager worldManager;
    protected SoundManager soundManager;
    protected MaterialManager materialManager;
    protected EffectManager effectManager;
    protected EntityManager entityManager;
//    protected NetworkManager networkManager;
//    protected GameStateManager gameStateManager;
    protected DeviceInfo deviceInfo;
    protected boolean customCycle = false;
    protected ServiceManager serviceManager;
    protected ListeningExecutorService executorService = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));

    @Override
    public void simpleInitApp() {
        init();
        load();
    }

    public void init() {
        initConfigurations();
        initServices();
        initManagers();
        initStates();
    }

    public void initManagers() {
        this.assetManager.registerLoader(SpriteSheetLoader.class, "sprites");

        this.entityManager = new EntityManager(this);
        this.materialManager = new MaterialManager(this);
        this.soundManager = new SoundManager(this);
        this.worldManager = new WorldManager(this);
        this.stageManager = new StageManager(this);
        this.guiManager = new GUIManager(this);
        this.gamePlayManager = new GamePlayManager(this);
        this.effectManager = new EffectManager(this);

        entityManager.init();
        materialManager.init();
        soundManager.init();
        stageManager.init();
        worldManager.init();
        gamePlayManager.init();
        guiManager.init();
        effectManager.init();

        entityManager.getEventBus().register(worldManager);
    }

    public void initServices(){
        
    }
    public void initStates() {
    }

    protected void initConfigurations() {
    }

    public void load() {
        materialManager.load();
        soundManager.load();
        guiManager.load();
    }

    public void config(Configuration props) {
    }

    public void update(float tpf) {
    }

    @Override
    public void simpleUpdate(float tpf) {
    }

    @Override
    public void simpleRender(RenderManager rm) {
    }

    public void finish() {
        guiManager.finish();
        stageManager.finish();
        worldManager.finish();
        gamePlayManager.finish();
        soundManager.finish();
        materialManager.finish();
    }

    @Override
    public void destroy() {
        finish();
        super.destroy();
    }

    public void setupInput() {
//        inputManager.addMapping("Help",
//                new KeyTrigger(KeyInput.KEY_F1));
//        inputManager.addListener(actionListener, "Help");
    }
//    private ActionListener actionListener = new ActionListener() {
//        public void onAction(String name, boolean pressed, float tpf) {
//        }
//    };
//    private AnalogListener analogListener = new AnalogListener() {
//        public void onAnalog(String name, float intensity, float tpf) {
//        }
//    };

    public AppSettings getSettings() {
        return settings;
    }

    public MaterialManager getMaterialManager() {
        return materialManager;
    }

    public SoundManager getSoundManager() {
        return soundManager;
    }

    public GamePlayManager getGamePlayManager() {
        return gamePlayManager;
    }

    public StageManager getStageManager() {
        return stageManager;
    }

    public GUIManager getGUIManager() {
        return guiManager;
    }

    public WorldManager getWorldManager() {
        return worldManager;
    }

    public DeviceInfo getDeviceInfo() {
        return deviceInfo;
    }

    public void setCustomCycle(boolean customCycle) {
        this.customCycle = customCycle;
    }

    public EffectManager getEffectManager() {
        return effectManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public ServiceManager getServiceManager() {
        return serviceManager;
    }

    public ListeningExecutorService getExecutorService() {
        return executorService;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
