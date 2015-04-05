/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.sound;

import com.jme3.cinematic.events.AbstractCinematicEvent;
import com.jme3.export.Savable;

/**
 * Warpter for AudioNode.
 * @author cuong.nguyenmanh2
 */
public class SoundClip  extends AbstractCinematicEvent implements Savable{
    String name;
    String path;
    int id;
    int status;
    float duration;
    float volume;
    float fade;
    int type;
    int musicType;
    int playingType;

    @Override
    protected void onPlay() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onUpdate(float tpf) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void onStop() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onPause() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
