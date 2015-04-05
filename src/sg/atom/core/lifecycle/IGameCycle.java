/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.lifecycle;

import org.apache.commons.configuration.Configuration;

/**
 * A GameCycle interface for attendant who join a strict routine of
 * init/load/config/update/finish.
 *
 * <p>GameCycle is a concept of Game programming.
 *
 * <p>Open hook to use Atom's Progress which can ultilize GPar Task or Guava
 * Service and also JME3's AppState. Compare to AppState, this interface are wel
 * defined for higher level, and they can also used together.
 * http://hub.jmonkeyengine.org/wiki/doku.php/jme3:advanced:application_states
 *
 * <p>FIXME: Change Properties to Common's AbstractConfiguration!
 * <p>FIXME: Find the similarity between IGameCycle and Service
 *
 * @author atomix
 */
public interface IGameCycle {

    public static final int PHASE_INIT = 0;
    public static final int PHASE_LOAD = 1;
    public static final int PHASE_CONFIG = 2;
    public static final int PHASE_UPDATE = 3;
    public static final int PHASE_FINISH = 4;
    public static final int PHASE_ERROR = 5;
    public static final int PHASE_NONE = -1;

    public void init();

    public void load();

    public void config(Configuration props);

    public void update(float tpf);

    public void finish();
}
