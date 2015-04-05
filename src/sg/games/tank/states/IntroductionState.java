package sg.games.tank.states;

import com.jme3.app.Application;
import com.jme3.app.state.AppStateManager;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.scene.Node;
import sg.atom.core.execution.BaseGameState;
import sg.games.tank.stage.intro.InstructionManager;
import sg.games.tank.stage.tutorials.Tutorial;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.text.Label;
import tonegod.gui.controls.windows.Panel;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 * Show tutorials.
 *
 * @author cuong.nguyen
 */
public class IntroductionState extends BaseGameState {

    private ButtonAdapter btnStart;
    private Label lblIntroText;
    private InstructionManager manager;
    private Tutorial tutorial;
    private Element instructorAvatar;
    private Node screenNode;
    private Screen screen;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        createUI();
        setupInput();
        this.manager = stateManager.getState(InstructionManager.class);
        this.tutorial = manager.getCurrentTutorial("InGameState");
    }

    @Override
    public void createUI() {
        super.createUI();

        screenNode = new Node();
        screen = new Screen(this.app, "Interface/Styles/Scifi/atlasdef/style_map.gui.xml");
        screen.setUseTextureAtlas(true, "Interface/Styles/Scifi/atlasdef/atlas.png");
        screenNode.addControl(screen);

//        VerticalLayout layout = new VerticalLayout(screen);
//        layout.setPadding(20);
//        layout.setMargins(30,30);

        int sw = app.getSettings().getWidth();
        int sh = app.getSettings().getHeight();
        
        Panel mainMenuPanel = new Panel(screen, new Vector2f(0, sh - 100), new Vector2f(500, 100));
//        p.setLayout(layout);

        btnStart = new ButtonAdapter(screen, Vector2f.ZERO, new Vector2f(200, 60)) {
            @Override
            public void onMouseLeftPressed(MouseButtonEvent evt) {
                super.onMouseLeftPressed(evt);
            }
        };

        ButtonAdapter btnLeaderboard = new ButtonAdapter(screen, new Vector2f(400, 50), new Vector2f(100, 50));
        btnLeaderboard.setText("Next");
        mainMenuPanel.addChild(btnLeaderboard);

        lblIntroText = new Label(screen, new Vector2f(110, 10), new Vector2f(500, 50));
        lblIntroText.setText("");
        mainMenuPanel.addChild(lblIntroText);
        
//        instructorAvatar = new Element(screen, "InstructorAvatar", new Vector2f(5, 10), new Vector2f(100,100), Vector4f.ZERO, "Interface/Images/Avatars/captain.jpg");
//        mainMenuPanel.addChild(instructorAvatar);
        
        screen.addElement(mainMenuPanel);
        mainMenuPanel.centerToParent();
//        mainMenuPanel.setAsContainerOnly();
        mainMenuPanel.setIsMovable(false);
        mainMenuPanel.setIsResizable(false);

//        p.getLayout().layoutChildren();
//        p.sizeToContent();

        guiNode.attachChild(screenNode);
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public void setupInput() {
        inputManager.addMapping("Next",
                new KeyTrigger(KeyInput.KEY_SPACE),
                new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        inputManager.addListener(actionListener, "Next");
    }
    private ActionListener actionListener = new ActionListener() {
        public void onAction(String name, boolean pressed, float tpf) {
            if (name.equalsIgnoreCase("Next") && !pressed) {
                next();
            }
        }
    };

    public void removeInput() {
        inputManager.deleteMapping("Shoot");
    }

    @Override
    public void dispose() {
        removeInput();
        super.dispose();
    }

    public void next() {
        //FIXME: Add text animation.
        tutorial.next();
    }

    public void update(float tpf) {
        tutorial.update(tpf);
        lblIntroText.setText(tutorial.getCurrent().getText());
    }
}
