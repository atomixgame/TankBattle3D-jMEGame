/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.managers;

import sg.atom.core.lifecycle.AbstractManager;
import sg.atom.corex.sound.SoundClip;
import java.util.ArrayList;
import org.apache.commons.configuration.Configuration;
import sg.atom.core.AtomMain;


/**
 *
 * @author cuong.nguyenmanh2
 */
public class SoundManager  extends AbstractManager{
    protected ArrayList<SoundClip> fxSounds;
    protected ArrayList<SoundClip> musicSounds;

    public SoundManager(AtomMain app) {
        super(app);
    }
    
    public void playSound(String soundName) {
    }
    
    public void playSound(SoundClip clip){
        
    }
    //Cycle -------------------------------------------------------------------
    public void init(){
        
    }
    
    public void config(Configuration configuration){
        
    }
    
    public void load(){
        
    }
    
    public void update(float tpf){
        
    }
    
    public void finish(){
        
    }
    
    //GETTER & SETTER
}
