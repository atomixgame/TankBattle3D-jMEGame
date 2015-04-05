package sg.games.tank.stage.cam;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import sg.atom.core.AtomMain;

/**
 * ShooterCamera controls the Camera of the game.
 * 
 * @author cuong.nguyen
 */
public class ShooterCamera extends AbstractAppState{
    private AtomMain app;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        
        this.app = (AtomMain) app;
        this.app.getFlyByCamera().setMoveSpeed(0);
    }
    
}
