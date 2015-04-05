package sg.atom.corex.ui.tonegod.controls;

import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.Vector2f;
import java.util.HashMap;
import sg.atom.corex.ui.tonegod.Command;
import sg.atom.corex.ui.tonegod.layouts.QuickLayouts;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.core.ElementManager;
import tonegod.gui.core.Screen;

/**
 *
 * @author cuong.nguyen
 */
public class ExButtonAdapter extends ButtonAdapter {

    HashMap<State, Command> commands = new HashMap<State, Command>(State.values().length);

    public ExButtonAdapter(ElementManager screen) {
        super(screen, new Vector2f());
    }

    public ExButtonAdapter(ElementManager screen, String UID) {
        super(screen, UID, new Vector2f());
    }

    public void decorate(State state, Command command) {
        commands.put(state, command);
    }

    public static enum State {

        Release, Press
    }

    @Override
    public void onButtonMouseLeftDown(MouseButtonEvent evt, boolean toggled) {
        super.onButtonMouseLeftDown(evt, toggled);
        Command c = commands.get(State.Press);
        if (c != null) {
            c.execulte((Screen) screen, this);
        }
    }
    
    public static class Builder extends ExElementBuilder{
        public Builder(ExButtonAdapter newButton, QuickLayouts layout) {
            super(newButton, layout);
        }
        
        public Builder onClick(Command command) {
            ((ExButtonAdapter)element).decorate(ExButtonAdapter.State.Press, command);
            return this;
        }
        
        public ExButtonAdapter getElement(){
            return (ExButtonAdapter)element;
        }
    }
}
