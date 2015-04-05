/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.atom.core.execution;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author cuong.nguyen
 */
public class CombinedProgressMeter extends SelfProgressMeter {
    private Set<ProgressMeter> children = new HashSet<ProgressMeter>();

    public CombinedProgressMeter() {
        super(1);
    }

    public void start() {
        super.start();
        if (!children.isEmpty()) {
            children.clear();
        }
    }

    @Override
    public float getTotalProgress() {
        if (!children.isEmpty()) {
            float currentProgress = 0;
            for (ProgressMeter c : getSubProgresses()) {
                float childContribution = c.getPercentage() * c.getTotalProgress();
//                System.out.println("ChildContribution" + childContribution +" from percentage " + c.getPercentage()+ " progress "+ c.getTotalProgress());
                currentProgress += childContribution;
            }
            totalProgress = currentProgress;
            
        }
//        System.out.println(" Total progress : " + totalProgress);
        return totalProgress;
    }

    public void increaseProgress(float value) {
        totalProgress += value;
    }

    public Set<ProgressMeter> getSubProgresses() {
        return children;
    }

    public void addSub(ProgressMeter meter) {
        if (isStarted() && !isFinished()) {
            children.add(meter);
        } else {
            throw new RuntimeException("Can not add sub while not started or already finished. Started: " + isStarted() + " Finished : "+ isFinished());
        }
    }

    public boolean hasSub() {
        return true;
    }
}
