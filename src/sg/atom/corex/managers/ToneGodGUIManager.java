/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import com.google.common.collect.BiMap;
import com.google.common.collect.TreeTraverser;
import com.jme3.scene.Node;
import sg.atom.core.AtomMain;
import sg.atom.core.lifecycle.AbstractManager;
import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 *
 * @author CuongNguyen
 */
public class ToneGodGUIManager extends AbstractManager {

    private Screen currentScreen;
    Node screenNode;
    protected BiMap<String, Screen> commonScreens;

    public ToneGodGUIManager(AtomMain app) {
        super(app);
    }

    public void setupGUI() {
    }

    public void load() {
        createScreens();
    }

    public void createScreens() {
    }

    public void goToScreen(String screenName) {
        Screen nextScreen = (Screen) commonScreens.get(screenName);
        if (nextScreen != null) {
            if (nextScreen != currentScreen) {
                if (currentScreen != null) {
                    screenNode.removeControl(currentScreen);
                    screenNode.removeFromParent();
                }
                screenNode = new Node(screenName);
                guiNode.attachChild(screenNode);
                screenNode.addControl(nextScreen);
                this.currentScreen = nextScreen;
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

    private void clearScreen(Screen currentScreen) {
//        Iterator<Element> iterator = currentScreen.getElements().iterator();
//        while (iterator.hasNext()) {
//            iterator.remove();
//        }
//        currentScreen.
    }
}
