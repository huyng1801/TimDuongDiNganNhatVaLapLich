package Scheduler;

import java.io.Serializable;


public class GanttChartItem implements Serializable{
    private String processName;
    private int startTime;
    private int finishTime;

    public GanttChartItem(String processName, int startTime, int finishTime) {
        this.processName = processName;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public String getProcessName() {
        return processName;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getFinishTime() {
        return finishTime;
    }
}
