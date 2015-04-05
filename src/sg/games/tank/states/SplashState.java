package sg.games.tank.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.ui.Picture;
import java.util.logging.Logger;
import sg.atom.core.execution.BaseGameState;

/**
 * Simple SplashState use guiNode and picture instead of Screen.
 *
 * @author CuongNguyen
 */
public class SplashState extends BaseGameState {

    private static final Logger logger = Logger.getLogger(SplashState.class.getName());
    float activeTime = 0, lastTime = 0, stepTime = 3;
    private Picture logo;

    public SplashState() {
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        createElements();
    }

    public void bindUI() {
//        this.guiManager.getInputManager().setCursorVisible(true);
    }

    void createElements() {
        this.app.getViewPort().setBackgroundColor(ColorRGBA.LightGray);
        createLogo();
    }

    void createLogo() {
        logo = new Picture("Logo");
        logo.setImage(assetManager, "Interface/Images/Brand/logo SGGame.png", true);
        this.guiNode.attachChild(logo);

    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        logo.removeFromParent();
        super.stateDetached(stateManager);

    }

    @Override
    public void update(float tpf) {
        super.update(tpf);

        activeTime += tpf;

        if (activeTime - lastTime > stepTime) {
            lastTime = activeTime;
            toState(MainMenuState.class);
        } else {
            float logoScale = 200 * (1 + 0.5f * FastMath.sin(activeTime));
            logo.setLocalScale(logoScale);
            float sw = app.getSettings().getWidth();
            float sh = app.getSettings().getHeight();
            logo.setLocalTranslation(sw / 2 - logoScale/2, sh / 2 - logoScale/2, 0);
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
