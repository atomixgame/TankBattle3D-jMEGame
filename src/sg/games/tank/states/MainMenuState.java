package sg.games.tank.states;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetKey;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector4f;
import com.jme3.scene.Node;
import sg.atom.corex.assets.sprite.SpriteSheet;
import sg.atom.core.execution.BaseGameState;
import sg.atom.corex.ui.tonegod.Command;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.Element;
import static sg.atom.corex.ui.tonegod.layouts.LayoutData.*;
import sg.atom.corex.ui.tonegod.layouts.QuickLayouts;
import tonegod.gui.core.Screen;

/**
 *
 * @author cuong.nguyen
 */
public class MainMenuState extends BaseGameState {

    Screen screen;
    private Node screenNode;
    private SpriteSheet commonElementsSheet;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);

        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);

        if (enabled) {
            createScreen();
            createElements();
        } else {
        }
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        screenNode.removeControl(screen);
        screenNode.removeFromParent();
        super.stateDetached(stateManager);

    }

    void createScreen() {
        screenNode = new Node("MainMenuScreen");
        screen = new Screen(app);
        app.getGuiNode().attachChild(screenNode);
        screenNode.addControl(screen);
    }

    private void createElements() {
        // create button and add to window
        ButtonAdapter btnInGame = new ButtonAdapter(screen, "btnInGame", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                toState(InGameState.class);
            }
        };
        ButtonAdapter btnCredit = new ButtonAdapter(screen, "btnCredit", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                toState(InGameState.class);
            }
        };
        ButtonAdapter btnSocial = new ButtonAdapter(screen, "btnSocial", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                toState(InGameState.class);
            }
        };
        ButtonAdapter btnOption = new ButtonAdapter(screen, "btnOption", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                toState(InGameState.class);
            }
        };
        ButtonAdapter btnShop = new ButtonAdapter(screen, "btnShop", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
                toState(InGameState.class);
            }
        };
        ButtonAdapter btnQuit = new ButtonAdapter(screen, "btnQuit", new Vector2f()) {
            @Override
            public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggled) {
//                quitGame();
            }
        };
        Element background = new Element(screen, "imgBackground", new Vector2f(0, 0), new Vector2f(100, 100), Vector4f.ZERO, "Interface/Images/Background/MainMenuBg.png");
        Element logo = new Element(screen, "imgLogo", new Vector2f(0, 0), new Vector2f(50, 20), Vector4f.ZERO, "Interface/Images/Brand/TitleText.png");

        //Now resolve the layout, IOC style!
        QuickLayouts layout = new QuickLayouts(screen);
        commonElementsSheet = assetManager.loadAsset(new AssetKey<SpriteSheet>("Interface/Images/CommonIcons.sprites"));
        layout.addSpriteSheet(commonElementsSheet);        
        layout.$(background).fill().addTo(screen).set();
        layout.$(logo).convert().unit(UNIT_PERCENT).addTo(screen).alignCenter(40).vAlignCenter(0).set();
        layout.$(btnInGame, btnCredit, btnSocial, btnOption, btnShop, btnQuit)
                .text("SingleGame", "Credit", "Social", "Option", "Shop", "Quit")
                .addTo(screen)
                .cellSize(10, 10, UNIT_PERCENT)
                .cellPos(10, 20, UNIT_PERCENT).posInc((100 - 20) / 6, 0, 2, 0, UNIT_PERCENT)
                .set();

//        layout.sprite(btnInGame, "CommonIcons", "Icon_Facebook");
        
        layout.button("btnStart", "Start")
                .onClick(new Command() {
            public void execulte(Screen screen, Element element) {
                toState(InGameState.class);
            }
        }).size(50,50).pos(100,100).set();
        
        layout.active(screen);
        
    }

    public void quitGame() {
//        app.quit();
    }

    public void toState(Class<? extends AbstractAppState> newState) {
        if (newState.isAssignableFrom(InGameState.class)) {
            app.getStateManager().detach(this);
            app.getStateManager().attach(new InGameState());
        } else {
            throw new IllegalStateException("Can not change to new state!" + newState.getName());
        }
    }
}
