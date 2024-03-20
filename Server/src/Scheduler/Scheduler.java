package Scheduler;

import java.util.*;
//Class chứa các giải thuật định thời biểu CPU
public class Scheduler {
//  giải thuật định thời biểu FCFS trả về đối tượng để vẽ giản đồ gantt
    public static List<GanttChartItem> scheduleFCFS(List<Process> processes) {
        Collections.sort(processes, (a, b) -> a.arrivalTime - b.arrivalTime);

        int currentTime = 0;
        List<GanttChartItem> ganttChart = new ArrayList<>();

        for (Process process : processes) {
            process.startTime = Math.max(currentTime, process.arrivalTime);
            process.finishTime = process.startTime + process.burstTime;
            GanttChartItem item = new GanttChartItem(process.name, process.startTime, process.finishTime);
            ganttChart.add(item);

            process.waitingTime = process.startTime - process.arrivalTime;
            process.turnaroundTime = process.finishTime - process.arrivalTime;

            currentTime = process.finishTime;
        }

        return ganttChart;
    }
//  giải thuật định thời biểu SJF trả về đối tượng để vẽ giản đồ gantt
    public static List<GanttChartItem> scheduleSJF(List<Process> processes) {
        List<Process> scheduled = new ArrayList<>();
        List<GanttChartItem> ganttChart = new ArrayList<>();
        int time = 0;
        List<Process> tempProcesses = new ArrayList<>(processes);
        while (!tempProcesses.isEmpty()) {
            Process shortest = null;
            int shortestTime = Integer.MAX_VALUE;

            for (Process process : tempProcesses) {
                if (process.arrivalTime <= time && process.remainingTime < shortestTime) {
                    shortest = process;
                    shortestTime = process.remainingTime;
                }
            }

            if (shortest == null) {
                time++;
            } else {
                int startTime = time;
                int finishTime = time + shortestTime;
                GanttChartItem item = new GanttChartItem(shortest.name, startTime, finishTime);
                ganttChart.add(item);

                time = finishTime;
                shortest.remainingTime = 0;
                scheduled.add(shortest);
                tempProcesses.remove(shortest);

                shortest.waitingTime = startTime - shortest.arrivalTime;
                shortest.turnaroundTime = finishTime - shortest.arrivalTime;
            }
        }

        Map<String, GanttChartItem> itemMap = new HashMap<>();
        for (GanttChartItem item : ganttChart) {
            itemMap.put(item.getProcessName(), item);
        }

        for (Process originalProcess : processes) {
            GanttChartItem item = itemMap.get(originalProcess.name);
            originalProcess.waitingTime = item.getStartTime() - originalProcess.arrivalTime;
            originalProcess.turnaroundTime = item.getFinishTime() - originalProcess.arrivalTime;
        }

        return ganttChart;

    }
//  giải thuật định thời biểu Priority trả về đối tượng để vẽ giản đồ gantt
    public static List<GanttChartItem> schedulePriority(List<Process> processes) {
        List<Process> scheduled = new ArrayList<>();
        List<GanttChartItem> ganttChart = new ArrayList<>();
        int time = 0;
        List<Process> tempProcesses = new ArrayList<>(processes);
        while (!tempProcesses.isEmpty()) {
            Process highestPriority = null;
            int highestPriorityValue = Integer.MAX_VALUE;

            for (Process process : tempProcesses) {
                if (process.arrivalTime <= time && process.priority < highestPriorityValue) {
                    highestPriority = process;
                    highestPriorityValue = process.priority;
                }
            }

            if (highestPriority == null) {
                time++;
            } else {
                int startTime = time;
                int finishTime = time + highestPriority.burstTime;
                GanttChartItem item = new GanttChartItem(highestPriority.name, startTime, finishTime);
                ganttChart.add(item);

                time = finishTime;
                highestPriority.remainingTime = 0;
                scheduled.add(highestPriority);
                tempProcesses.remove(highestPriority);

                // Tính toán waitingTime và turnaroundTime
                highestPriority.waitingTime = startTime - highestPriority.arrivalTime;
                highestPriority.turnaroundTime = finishTime - highestPriority.arrivalTime;
            }
        }

        Map<String, GanttChartItem> itemMap = new HashMap<>();
        for (GanttChartItem item : ganttChart) {
            itemMap.put(item.getProcessName(), item);
        }

        for (Process originalProcess : processes) {
            GanttChartItem item = itemMap.get(originalProcess.name);
            originalProcess.waitingTime = item.getStartTime() - originalProcess.arrivalTime;
            originalProcess.turnaroundTime = item.getFinishTime() - originalProcess.arrivalTime;
        }

        return ganttChart;

    }
//  giải thuật định thời biểu RR trả về đối tượng để vẽ giản đồ gantt
    public static List<GanttChartItem> scheduleRR(List<Process> processes, int timeQuantum) {
        List<GanttChartItem> ganttChart = new ArrayList<>();
        Queue<Process> queue = new LinkedList<>();
        List<Process> existsProcess = new ArrayList<>();
        int time = 0;
        List<Process> tempProcesses = new ArrayList<>(processes);
        while (existsProcess.size() != tempProcesses.size()  || !queue.isEmpty()) {
            boolean processExecuted = false;

         for (Process process : tempProcesses) {
            if (process.arrivalTime <= (time + timeQuantum) && !containsProcess(existsProcess, process)) {
                System.out.println(time + process.name);
                queue.add(process);
                existsProcess.add(process);
                processExecuted = true;
            }
        }
            if (!queue.isEmpty()) {
                Process current = queue.poll();
                int startTime = time;
                int remainingTime = current.remainingTime;

                if (remainingTime <= timeQuantum) {
                    time += remainingTime;
                    current.remainingTime = 0;
                } else {
                    time += timeQuantum;
                    current.remainingTime -= timeQuantum;
                    queue.add(current); 
                }

                int finishTime = time;
                GanttChartItem item = new GanttChartItem(current.name, startTime, finishTime);
                ganttChart.add(item);
                current.waitingTime = startTime - current.arrivalTime;
                current.turnaroundTime = finishTime - current.arrivalTime;
            }
           
        }

        return ganttChart;
    }
    
    private static boolean containsProcess(List<Process> queue, Process process) {
    for (Process p : queue) {
        if (p.name.equals(process.name)) {
            return true;
        }
    }
    return false;
}
//Tính thời gian chờ trung bình
    public static double calculateAverageWaitingTime(List<Process> processes) {
        int totalWaitingTime = 0;
        for (Process process : processes) {
            totalWaitingTime += process.getWaitingTime();
        }
        return (double) totalWaitingTime / processes.size();
    }
//Tính thời gian thoát trung bình
    public static double calculateAverageTurnaroundTime(List<Process> processes) {
        int totalTurnaroundTime = 0;
        for (Process process : processes) {
            totalTurnaroundTime += process.getTurnaroundTime();
        }
        return (double) totalTurnaroundTime / processes.size();
    }

}
