package sg.games.tank.states;

import sg.atom.core.execution.BaseGameState;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.controls.ActionListener;
import java.util.logging.Logger;
import sg.games.tank.stage.cam.RTSCamera;
import sg.games.tank.ui.common.UIInGameScreen;
import sg.games.tank.ui.hud.HUDState;

/**
 *
 * @author cuong.nguyen
 */
public class InGameState extends BaseGameState {

    private static final Logger logger = Logger.getLogger(InGameState.class.getName());
    protected UIInGameScreen screenController;
    protected boolean gamePause;
    protected boolean firstRun;
    protected ActionListener actionListener;
    protected RTSCamera camState;
    private HUDState hudState;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        setEnabled(true);
    }

    @Override
    public void goToState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(CreditState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new CreditState());
        } else if (newState.isAssignableFrom(MainMenuState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new MainMenuState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }
    }

    public void setupInput() {
        actionListener = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) {
//                if (!guiManager.isCurrentScreen("InGameScreen")) {
//                    guiManager.goToScreen("InGameScreen");
//                }
            }
        };
//
//        inputManager.addMapping("dialogue", new KeyTrigger(KeyInput.KEY_K));
//        inputManager.addListener(actionListener, "dialogue");
//
//        inputManager.addMapping("fight", new KeyTrigger(KeyInput.KEY_J));
//        inputManager.addListener(actionListener, "fight");
    }

//    public void bindUI(GUIManager manager, Object ui) {
//        if (guiManager.isCurrentScreen("InGameScreen")) {
//            this.guiManager.getInputManager().setCursorVisible(true);
//        } else {
//        }
//    }
//
//    public void unbindUI(GUIManager manager, Object ui) {
//        if (guiManager.isCurrentScreen("InGameScreen")) {
//            //app.getGuiNode().detachAllChildren();
//        }
//    }

    public void createUI(){
        
    }
    
    @Override
    public void update(float tpf) {
        super.update(tpf);

        if (!gamePause) {
        }
    }

    public void load() {
        app.getEntityManager().load();
        app.getStageManager().load();
        app.getWorldManager().load();
        app.getGamePlayManager().load();
    }

    public void pauseGame() {
    }

    public void startGame() {
        setupInput();
        app.getStageManager().onStageReady();
        
        this.camState = new RTSCamera();
        stateManager.attach(camState);
        
        stateManager.attach(app.getGamePlayManager());
        app.getGamePlayManager().startGame();
        
//        app.getGUIManager().goToScreen("InGameScreen");
        
        this.hudState = new HUDState();
        stateManager.attach(hudState);
    }

    public void restartGame() {
    }

    public void resumeGame() {
    }

    //GETTER & SETTER
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            load();
            startGame();
        } else {
        }
    }

//    public UIInGameScreen getScreenController() {
//        return screenController;
//    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        super.stateDetached(stateManager);
//        stateManager.detach(camState);
    }
}