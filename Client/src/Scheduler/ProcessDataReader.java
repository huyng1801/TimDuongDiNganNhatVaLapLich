package Scheduler;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessDataReader {

    public static List<Process> readFCFSDataFromFile(String filePath) {
        List<Process> processes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0].trim();
                    int arrivalTime = Integer.parseInt(data[1].trim());
                    int burstTime = Integer.parseInt(data[2].trim());
                    Process process = new Process(name, arrivalTime, burstTime, 0);
                    processes.add(process);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processes;
    }

    public static List<Process> readPriorityDataFromFile(String filePath) {
        List<Process> processes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    String name = data[0].trim();
                    int arrivalTime = Integer.parseInt(data[1].trim());
                    int burstTime = Integer.parseInt(data[2].trim());
                    int priority = Integer.parseInt(data[3].trim());
                    Process process = new Process(name, arrivalTime, burstTime, priority);
                    processes.add(process);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processes;
    }

}
