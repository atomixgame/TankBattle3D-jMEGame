package sg.atom.core.execution;

import java.util.Set;

/**
 * ProgressMeter calculate its current Progess by its self or contributions of its children.
 * @author cuong.nguyen
 */
public interface ProgressMeter {

    long getId();

    float getPercentage();

    float getTotalProgress();

    void increaseProgress(float value);
    
    void start();

    void onFinish();
    
    boolean isStarted();
    
    boolean isFinished();
}
