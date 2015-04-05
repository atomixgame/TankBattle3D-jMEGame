package sg.games.tank.stage.intro;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.cinematic.Cinematic;
import java.util.HashMap;
import sg.games.tank.stage.tutorials.Tutorial;
import sg.games.tank.stage.tutorials.Tutorial_FirstGame;

/**
 * Show tutorials.
 *
 * @author cuong.nguyenmanh2
 */
public class InstructionManager extends AbstractAppState {

    Cinematic cinematic;
    //    LoadingCache<String, Tutorial> tutorials =
    HashMap<String, Tutorial> tutorialMap;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        tutorialMap = new HashMap<String, Tutorial>();
        tutorialMap.put("InGameState", Tutorial_FirstGame.create());
    }

    public void highlight(Object object) {
    }

    public void focus(Object object) {
    }

    public void showDescription(String text, int position) {
    }

    public void popUp(String text) {
    }

    //FIXME: Write a builder.
    public Tutorial getCurrentTutorial(AppState state) {
        String className = state.getClass().getSimpleName();
        return getCurrentTutorial(className);
    }

    public Tutorial getCurrentTutorial(String className) {
        return tutorialMap.get(className);
    }
}
