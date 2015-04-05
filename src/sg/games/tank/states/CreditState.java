/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.games.tank.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import java.util.logging.Logger;
import sg.games.tank.TankBattleMain;
import sg.atom.corex.managers.GUIManager;
import sg.atom.corex.managers.StageManager;

/**
 *
 * @author CuongNguyen
 */
public class CreditState extends AbstractAppState {

    private TankBattleMain app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private static final Logger logger = Logger.getLogger(CreditState.class.getName());
    private GUIManager guiManager;
    private StageManager stageManager;
    private UICreditScreen creditScreenController;
    float thisTime = 0, lastTime = 0, stepTime = 0.2f;
    
    public static interface UICreditScreen {

        void scroll();

        void in();

        void out();
    }
    
    public CreditState() {
    }



    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        this.app = (TankBattleMain) app; // can cast Application to something more specific

        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        //this.gameStateManager = this.app.getGameStateManager();

        this.guiManager = this.app.getGUIManager();
        this.stageManager = this.app.getStageManager();
        setEnabled(true);

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            guiManager.goToScreen("CreditScreen");
        } else {
            //unbindUI();
        }
    }

    public void bindUI() {

        this.app.getInputManager().setCursorVisible(true);
        this.thisTime = 0;
        lastTime = 0;
    }

    public void unbindUI() {
        this.creditScreenController = null;
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        if (this.creditScreenController != null) {
            thisTime += tpf;
            if (thisTime - lastTime > stepTime) {
                this.creditScreenController.scroll();
                lastTime = thisTime;
            }

        }
    }

    public void toState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(MainMenuState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new MainMenuState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }
    }
}
