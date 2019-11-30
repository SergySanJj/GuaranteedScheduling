package com.sched;
// This file contains the main() function for the Scheduling
// simulation.  Init() initializes most of the variables by
// reading from a provided file.  SchedulingAlgorithm.Run() is
// called from main() to run the simulation.  Summary-Results
// is where the summary results are written, and Summary-Processes
// is where the process scheduling summary is written.

// Created by Alexander Reeder, 2001 January 06

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Scheduling {

    private static int processNum;
    private static int meanDev;
    private static int standardDev;
    private static int runTime;
    private static Vector<ProcessSimulation> processVector = new Vector<>();
    private static Results result;
    private static String resultsFile = "Summary-Results";

    public static void main(String[] args) {
        canBeRead(args);
        init(args[0]);
        if (processVector.size() < processNum) {
            addProcessesUpToProcessNum();
        }
        SchedulingAlgorithm algorithm = new GuaranteedSchedulingAlgorithm();

        Logger.getLogger("main").log(Level.INFO, "Working...");
        result = algorithm.run(runTime, processVector);
        printResultsToFile();
        Logger.getLogger("main").log(Level.INFO, "Completed");
    }

    private static void printResultsToFile() {
        try {
            PrintStream out = new PrintStream(new FileOutputStream(resultsFile));
            out.println("Scheduling Type: " + result.schedulingType);
            out.println("Scheduling Name: " + result.schedulingName);
            out.println("Simulation Run Time: " + result.compuTime);
            out.println("Mean: " + meanDev);
            out.println("Standard Deviation: " + standardDev);
            out.println("Process #\tCPU Time\tIO Blocking\tCPU Completed\tCPU Blocked");
            for (int i = 0; i < processVector.size(); i++) {
                ProcessSimulation process = processVector.elementAt(i);
                out.print(i);
                if (i < 100) {
                    out.print("\t\t");
                } else {
                    out.print("\t");
                }
                out.println(process);
            }
            out.close();
        } catch (IOException e) { /* Handle exceptions */ }
    }

    private static void addProcessesUpToProcessNum() {
        int i = 0;
        while (processVector.size() < processNum) {
            double X = Common.R1();
            while (X == -1.0) {
                X = Common.R1();
            }
            X = X * standardDev;
            int cputime = (int) X + meanDev;
            processVector.addElement(new ProcessSimulation(cputime, i * 100, 0, 0, 0));
            i++;
        }
    }

    private static void init(String fileName) {
        File f = new File(fileName);
        try {
            initFromFile(f);
        } catch (IOException e) { /* Handle exceptions */ }
    }

    private static void initFromFile(File f) throws IOException {
        String line;
        FileInputStream fileStream = new FileInputStream(f);
        Scanner in = new Scanner(fileStream);
        while ((line = in.nextLine()) != null) {
            if (line.startsWith("numprocess")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                processNum = Common.s2i(st.nextToken());
            }
            if (line.startsWith("meandev")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                meanDev = Common.s2i(st.nextToken());
            }
            if (line.startsWith("standdev")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                standardDev = Common.s2i(st.nextToken());
            }
            if (line.startsWith("process")) {
                ProcessSimulation newProcess = generateProcessFromLine(line);
                processVector.addElement(newProcess);
            }
            if (line.startsWith("runtime")) {
                StringTokenizer st = new StringTokenizer(line);
                st.nextToken();
                runTime = Common.s2i(st.nextToken());
            }
        }
        in.close();
    }

    private static ProcessSimulation generateProcessFromLine(String line) {
        int ioblocking;
        double X;
        int cputime;
        StringTokenizer st = new StringTokenizer(line);
        st.nextToken();
        ioblocking = Common.s2i(st.nextToken());
        X = Common.R1();
        while (X == -1.0) {
            X = Common.R1();
        }
        X = X * standardDev;
        cputime = (int) X + meanDev;
        return new ProcessSimulation(cputime, ioblocking, 0, 0, 0);
    }

    private static void debug() {
        int i = 0;

        System.out.println("processnum " + processNum);
        System.out.println("meandevm " + meanDev);
        System.out.println("standdev " + standardDev);
        int size = processVector.size();
        for (i = 0; i < size; i++) {
            ProcessSimulation process = (ProcessSimulation) processVector.elementAt(i);
            System.out.println("process " + i + " " + process.getCputime() + " " + process.getIoblocking() + " " + process.getCpudone() + " " + process.getNumblocked());
        }
        System.out.println("runtime " + runTime);
    }


    private static void canBeRead(String[] args) {
        if (args.length != 1) {
            Logger.getLogger("main").
                    log(Level.WARNING, "Usage: 'java Scheduling <INIT FILE>'");
            System.exit(-1);
        }
        File f = new File(args[0]);
        if (!(f.exists())) {
            Logger.getLogger("main").
                    log(Level.WARNING, "Scheduling: error, file '" + f.getName() + "' does not exist.");
            System.exit(-1);
        }
        if (!(f.canRead())) {
            Logger.getLogger("main").
                    log(Level.WARNING, "Scheduling: error, read of " + f.getName() + " failed.");
            System.exit(-1);
        }
    }
}

