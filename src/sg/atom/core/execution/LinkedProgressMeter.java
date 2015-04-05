package sg.atom.core.execution;

/**
 * Delegation for a link from child task's progress meter (TPM) to parent TPM.
 * @author cuong.nguyen
 */
public class LinkedProgressMeter extends SelfProgressMeter {

    private CombinedProgressMeter parentProgressMeter;
    private float contributedPercentage = 0;

    public LinkedProgressMeter(CombinedProgressMeter parentProgressMeter, float percentage) {
        super(percentage);
        this.parentProgressMeter = parentProgressMeter;
    }
    
    @Override
    public void onFinish() {
        float remainPercentage = getPercentage() - contributedPercentage;
        parentProgressMeter.increaseProgress(remainPercentage);
        System.out.println(" Contribute to parent " + remainPercentage);
        super.onFinish();
    }

    @Override
    public void increaseProgress(float value) {
        contributedPercentage += value;
        super.increaseProgress(value);
        
        System.out.println("Increase progress of child" + value + " contributed " + contributedPercentage);
    }

    public CombinedProgressMeter getParentProgressMeter() {
        return parentProgressMeter;
    }
}
