/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import sg.atom.core.lifecycle.AbstractManager;
import com.jme3.audio.AudioNode;
import com.jme3.input.FlyByCamera;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import sg.atom.core.AtomMain;

/**
 *
 * @author cuong.nguyen
 */
public class StageManager extends AbstractManager {

    public StageManager(AtomMain app) {
        super(app);
    }

    @Override
    public void init() {
        setupBackground();
        setupCamera();
//        setupSounds();
    }

    public void setupBackground() {
        ViewPort viewPort = app.getViewPort();
        viewPort.setBackgroundColor(ColorRGBA.Gray);
    }

    public void setupSounds() {
        AudioNode music = new AudioNode(assetManager, "");
        music.setPositional(false);
        music.setLooping(true);
        music.setVolume(1f);
        music.play();
    }

    public void setupCamera() {
        Camera cam = app.getCamera();
        FlyByCamera flyCam = app.getFlyByCamera();
        flyCam.setMoveSpeed(40);
        cam.setLocation(new Vector3f(0, 3, 0));
//        cam.lookAt(new Vector3f(0, 10, 0), Vector3f.UNIT_Y);

    }

    public void onStageReady() {
        app.getInputManager().setCursorVisible(false);
        //        flyCam.setEnabled(false);
    }
}
