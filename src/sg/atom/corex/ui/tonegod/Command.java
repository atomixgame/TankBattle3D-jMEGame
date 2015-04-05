/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.corex.ui.tonegod;

import tonegod.gui.core.Element;
import tonegod.gui.core.Screen;

/**
 *
 * @author cuong.nguyen
 */
public interface Command {

    void execulte(Screen screen, Element element);
    
}
