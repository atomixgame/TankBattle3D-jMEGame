package sg.atom.core.execution;

/**
 *
 * @author cuong.nguyen
 */
public class SelfProgressMeter implements ProgressMeter {

    protected float totalProgress = 0;
    protected boolean started;
    protected float percentage;

    public SelfProgressMeter(float percentage) {
        this.percentage = percentage;
    }

    public void start() {
        totalProgress = 0;
        started = true;
    }

    public long getId() {
        return -1;
    }

    public float getPercentage() {
        return percentage;
    }

    public float getTotalProgress() {
        return totalProgress;
    }

    public void increaseProgress(float value) {
        if (totalProgress + value < 1) {
            totalProgress += value;
        } else {
            totalProgress = 1;
//            onFinish();
        }
    }

    public void onFinish() {
        started = false;
    }

    public boolean isStarted() {
        return started;
    }

    public boolean isFinished() {
        return totalProgress >= 1;
    }
}
