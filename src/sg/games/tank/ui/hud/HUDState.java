package sg.games.tank.ui.hud;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import com.jme3.ui.Picture;
import sg.atom.core.AtomMain;
import sg.games.tank.gameplay.GamePlayManager;

/**
 *
 * @author cuong.nguyen
 */
public class HUDState extends AbstractAppState {

    Node hudNode;
    //HUD
    Picture crossHairPic;
    float crossHairWidth = 100;
    float crossHairHeight = 100;
    BitmapText instructionText;
    BitmapText ammoText;
    BitmapText hpText;
    BitmapText scoreText;
    private AtomMain app;
    private GamePlayManager gamePlayManager;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.app = (AtomMain) app;
        
        setEnabled(true);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            createHUD();
            start();
        }
    }

    public void start() {
        Node guiNode = getApp().getGuiNode();
        guiNode.attachChild(ammoText);
        guiNode.attachChild(instructionText);
        guiNode.attachChild(hpText);
        guiNode.attachChild(scoreText);
//        guiNode.attachChild(crossHairPic);
    }

    public void finish() {
    }

    public void createHUD() {
        gamePlayManager = getApp().getGamePlayManager();

        BitmapFont guiFont = getApp().getAssetManager().loadFont("Interface/Fonts/Default.fnt");

        instructionText = new BitmapText(guiFont, false);
        instructionText.setText("Hello World");

        scoreText = new BitmapText(guiFont, false);
        hpText = new BitmapText(guiFont, false);
        ammoText = new BitmapText(guiFont, false);


//        crossHairPic = new Picture("CrossHair1");
//        crossHairPic.setImage(getApp().getAssetManager(), "Interface/HUD/Crosshair/crosshairs.png", true);
//        crossHairPic.setLocalScale(crossHairWidth, crossHairHeight, 1);
    }

    @Override
    public void update(float tpf) {
        AppSettings settings = getApp().getSettings();
        float sw = settings.getWidth();
        float sh = settings.getHeight();
        ammoText.setText("Ammo:" + Integer.toString(gamePlayManager.getAmmo()));
        hpText.setText("HP:" + Integer.toString(gamePlayManager.getHealth()));
        scoreText.setText("Score:" + Integer.toString(gamePlayManager.getScore()));
        instructionText.setLocalTranslation(sw / 2 - instructionText.getLineWidth() / 2, 120, 0);
        scoreText.setLocalTranslation(sw / 2 - scoreText.getLineWidth() / 2, sh - 40, 0);
//        crossHairPic.setLocalTranslation((sw - crossHairWidth) / 2, (sh - crossHairHeight) / 2, 1);
        ammoText.setLocalTranslation(sw - 120, 80, 0);

        hpText.setLocalTranslation(sw - 120, 120, 0);
    }

    public AtomMain getApp() {
        return app;
    }
}
