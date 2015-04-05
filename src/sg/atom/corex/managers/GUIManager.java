package sg.atom.corex.managers;

import sg.atom.core.lifecycle.AbstractManager;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.TreeTraverser;
import com.jme3.font.BitmapFont;
import com.jme3.scene.Node;
import sg.atom.core.AtomMain;
import sg.atom.corex.ui.tonegod.TUIBaseScreen;
import tonegod.gui.core.Element;

/**
 *
 * @author cuong.nguyen
 */
public class GUIManager extends AbstractManager {

    protected TUIBaseScreen currentScreen;
    protected BitmapFont guiFont;
    protected static GUIManager instance;
    protected BiMap<String, Object> commonScreens;
    Node screenNode;
    
    public GUIManager(AtomMain app) {
        super(app);
    }

    public void init() {
//        guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
//        guiFont = assetManager.loadFont("Interface/Fonts/SciFied.fnt");
    }


    public void update(float tpf) {
        if (currentScreen != null) {
            currentScreen.update(tpf);
        }
    }

    @Override
    public void load() {
        createScreens();
    }

    public void createScreens() {
        commonScreens = HashBiMap.create();
//        commonScreens.put("OptionsScreen", new LUIOptionsScreen(this));
//        commonScreens.put("InGameScreen", new LUIInGameScreen(this));
        
    }

public void registerScreen(String screenName, Object screen) {
        commonScreens.put(screenName, (TUIBaseScreen) screen);
    }

    public void goToScreen(String screenName) {
        TUIBaseScreen nextScreen = (TUIBaseScreen) commonScreens.get(screenName);
        if (nextScreen != null) {
            if (nextScreen != currentScreen) {
                screenNode = new Node(screenName);
                guiNode.attachChild(screenNode);
                screenNode.addControl(nextScreen);
                this.currentScreen = nextScreen;
//                this.currentScreen.onStartScreen();
                System.out.println("GUIManager Goto :" + screenName);
            } else {
                System.out.println("GUIManager Try to goto current screen! Do nothing!");
            }
        } else {
            throw new IllegalArgumentException("No screen existed :" + screenName);
        }
    }

    public TreeTraverser<Element> getTreeTraverser() {
        return new TreeTraverser<Element>() {
            @Override
            public Iterable<Element> children(Element root) {
                return root.getElements();
            }
        };
    }

    public void clearScreen() {
        if (currentScreen != null) {
            screenNode.removeControl(currentScreen);
            screenNode.removeFromParent();
//            app.getInputManager().removeRawInputListener(currentScreen);
//            this.currentScreen.onEndScreen();
            System.out.println("Remove screen!");
        }
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }
    
    
}
