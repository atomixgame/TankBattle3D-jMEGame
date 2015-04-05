/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.lifecycle;

import com.jme3.app.Application;
import com.jme3.asset.AssetManager;
import org.apache.commons.configuration.Configuration;

/**
 * Beside of Entity, the second citizen of the framework.
 *
 * <p>This is the simplest form of Agent in Actor framework. This is the bridge
 * for Atom Object to be built, config and run exactly like one.
 *
 * <p>ManagableObject is Bean an also can be marked with Atom annotation to
 * retrieve most efficient performance.
 * 
 * @author CuongNguyen
 */
public interface ManagableObject {

    public void init(Application app);
    
    public void initManagers(IGameCycle... managers);

    public void load(AssetManager assetManager);

    public void config(Configuration configuration);

    public void update(float tpf);

    public void finish();
}
