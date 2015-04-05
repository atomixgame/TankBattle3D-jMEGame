package sg.games.tank.stage.intro;

import com.google.common.base.Predicate;
import com.jme3.app.state.AppState;

/**
 * InstructionStep.
 * 
 * @author cuong.nguyenmanh2
 */
public class InstructionStep {
    String title;
    String desc;
    String text;
    Predicate<AppState> condition;
    int order;
    int status;
    boolean skipable;
    boolean interactive;
    boolean started;
    boolean finished;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Predicate<AppState> getCondition() {
        return condition;
    }

    public void setCondition(Predicate<AppState> condition) {
        this.condition = condition;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isSkipable() {
        return skipable;
    }

    public void setSkipable(boolean skipable) {
        this.skipable = skipable;
    }

    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
    
    
}