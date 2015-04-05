package sg.atom.corex.ui.tonegod;

import com.jme3.app.Application;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.renderer.RenderManager;
import tonegod.gui.core.Screen;

/**
 *
 * @author cuong.nguyen
 */
public class TUIBaseScreen extends Screen implements AppState {

    public TUIBaseScreen(Application app) {
        super(app);
    }
    
    public void initialize(AppStateManager stateManager, Application app) {
    }

    public boolean isInitialized() {
        return false;
    }

    public void setEnabled(boolean active) {
    }

    public boolean isEnabled() {
        return false;
    }

    public void stateAttached(AppStateManager stateManager) {
    }

    public void stateDetached(AppStateManager stateManager) {
    }

    public void render(RenderManager rm) {
    }

    public void postRender() {
    }

    public void cleanup() {
    }
}
