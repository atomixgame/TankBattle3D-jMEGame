package sg.games.tank.stage.tutorials;

import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AppState;
import java.util.ArrayList;
import sg.games.tank.stage.intro.InstructionStep;

/**
 *
 * @author cuong.nguyenmanh2
 */
public class Tutorial extends ArrayList<InstructionStep> {

    SimpleApplication app;
    String instructor;
    int currentIndex;
    private AppState state;

    public void start(AppState state) {
//        this.app = state.;
        this.state = state;
        this.currentIndex = 0;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public InstructionStep getCurrent() {
        return get(currentIndex);
    }

    public void skip() {
    }

    public void next() {
        System.out.println(" Tutorial next" + currentIndex);
        if (currentIndex == size() - 1) {
            end();
        } else {
            currentIndex++;
        }
    }

    public void end() {
    }

    public void update(float tpf) {
        //Check for step condition if its interactive
        if (getCurrent().isInteractive()) {
            if (getCurrent().getCondition().apply(state)) {
            }
        }
    }
    
    public static class Builder{
        private Tutorial result;

        public Builder() {
            this.result = new Tutorial();
        }
        
        public Builder dialogue(String text) {
            InstructionStep step = new InstructionStep();
            step.setText(text);
            result.add(step);
            return this;
        }
         
        public Tutorial build(){
            return result;
        }

        public Builder  instructor(String name) {
            result.instructor = name;
            return this;
        }
        
    }
}
