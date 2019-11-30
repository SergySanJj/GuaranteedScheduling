package com.sched;// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.

import java.util.Vector;
import java.io.*;

public class GuaranteedSchedulingAlgorithm implements SchedulingAlgorithm {

    public GuaranteedSchedulingAlgorithm() {
    }

    @Override
    public Results run(int runtime, Vector<ProcessSimulation> processVector) {
        AutoSortedList<ProcessSimulation> processes = new AutoSortedList<>(processVector);
        Results result = new Results("Interactive", "Guaranteed", 0);

        int comptime = 0;
        int currentProcess = 0;
        int previousProcess = 0;
        int size = processVector.size();
        int completed = 0;
        String resultsFile = "Summary-Processes";

        try {
            File procFile = new File("res/" + resultsFile);
            procFile.createNewFile();
            FileOutputStream procOut = new FileOutputStream(procFile, false);
            PrintStream out = new PrintStream(procOut);

            ProcessSimulation process = processVector.elementAt(currentProcess);
            logState(out, process, REGISTERED);
            while (comptime < runtime) {
                if (process.getCpuDone() == process.getCpuTime()) {
                    completed++;
                    logState( out, process, COMPLETED);
                    if (completed == size) {
                        result.compuTime = comptime;
                        out.close();
                        return result;
                    }
                    for (int i = size - 1; i >= 0; i--) {
                        process = processVector.elementAt(i);
                        if (process.getCpuDone() < process.getCpuTime()) {
                            currentProcess = i;
                        }
                    }
                    process = processVector.elementAt(currentProcess);
                    logState( out, process, REGISTERED);
                }
                if (process.getIoBlocking() == process.getIoNext()) {
                    logState( out, process, I_O_BLOCKED);
                    process.setNumBlocked(process.getNumBlocked() + 1);
                    process.setIoNext(0);
                    previousProcess = currentProcess;
                    for (int i = size - 1; i >= 0; i--) {
                        process = processVector.elementAt(i);
                        if (process.getCpuDone() < process.getCpuTime() && previousProcess != i) {
                            currentProcess = i;
                        }
                    }
                    process = processVector.elementAt(currentProcess);
                    logState( out, process, REGISTERED);
                }
                process.setCpuDone(process.getCpuDone() + 1);
                if (process.getIoBlocking() > 0) {
                    process.setIoNext(process.getIoNext() + 1);
                }
                comptime++;
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result.compuTime = comptime;
        return result;
    }

    private static void logState(PrintStream out, ProcessSimulation process, String state) {
        out.println("Process: "
                + process.getPID() + "  "
                + state + "  " +
                process.getCpuTime() + " " +
                process.getIoBlocking() + " " +
                process.getCpuDone() + " " +
                process.getCpuDone() + ")");
    }
}
