package sg.atom.core.execution;

import com.google.common.util.concurrent.FutureCallback;

/**
 *
 * @author cuong.nguyen
 */
public class ProgressRecord<V> implements FutureCallback<V> {

    private FutureCallback<V> callback;
    private LinkedProgressMeter taskProgressMeter;

    public ProgressRecord(FutureCallback<V> callback, float percentage, CombinedProgressMeter parentProgressMeter) {
        this.callback = callback;
        this.taskProgressMeter = new LinkedProgressMeter(parentProgressMeter, percentage);
        parentProgressMeter.addSub(taskProgressMeter);
        taskProgressMeter.start();
    }

    public void onSuccess(V result) {
        taskProgressMeter.onFinish();
        if (callback != null) {
            callback.onSuccess(result);
        }
    }

    public void onFailure(Throwable thrown){
        if (callback != null) {
            callback.onFailure(thrown);
        }
    }

    public LinkedProgressMeter getTaskProgressMeter() {
        return taskProgressMeter;
    }
}
