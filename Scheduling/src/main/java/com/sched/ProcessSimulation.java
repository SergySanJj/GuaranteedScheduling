package com.sched;

import java.util.Objects;

public class ProcessSimulation implements Comparable<ProcessSimulation> {
    private int PID;
    private int cpuTime;
    private int ioBlocking;
    private int cpuDone;
    private int ioNext;
    private int numBlocked;
    private double ratio;

    public ProcessSimulation(int cpuTime, int ioBlocking, int cpuDone, int ioNext, int numBlocked) {
        this.setCpuTime(cpuTime);
        this.setIoBlocking(ioBlocking);
        this.setCpuDone(cpuDone);
        this.setIoNext(ioNext);
        this.setNumBlocked(numBlocked);
        this.PID = 0;
        ratio = 0.0;
    }


    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(getPID()).append("  ");
        res.append(getCpuTime());
        if (getCpuTime() < 100) {
            res.append(" (ms)\t\t");
        } else {
            res.append(" (ms)\t");
        }
        res.append(getIoBlocking());
        if (getIoBlocking() < 100) {
            res.append(" (ms)\t\t");
        } else {
            res.append(" (ms)\t");
        }
        res.append(getCpuDone());
        if (getCpuDone() < 100) {
            res.append(" (ms)\t\t");
        } else {
            res.append(" (ms)\t");
        }
        res.append(getNumBlocked()).append(" times");

        return res.toString();
    }

    public int getCpuTime() {
        return cpuTime;
    }

    public void setCpuTime(int cpuTime) {
        this.cpuTime = cpuTime;
    }

    public int getIoBlocking() {
        return ioBlocking;
    }

    public void setIoBlocking(int ioBlocking) {
        this.ioBlocking = ioBlocking;
    }

    public int getCpuDone() {
        return cpuDone;
    }

    public void setCpuDone(int cpuDone) {
        this.cpuDone = cpuDone;
    }

    public int getIoNext() {
        return ioNext;
    }

    public void setIoNext(int ioNext) {
        this.ioNext = ioNext;
    }

    public int getNumBlocked() {
        return numBlocked;
    }

    public void setNumBlocked(int numBlocked) {
        this.numBlocked = numBlocked;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProcessSimulation))
            return false;
        if (o.hashCode() == this.hashCode())
            return true;
        return this.compareTo((ProcessSimulation) (o)) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpuTime, ioBlocking, cpuDone, ioNext, numBlocked, ratio);
    }

    @Override
    public int compareTo(ProcessSimulation other) {
        return Double.compare(ratio, other.ratio);
    }

    public int getPID() {
        return PID;
    }

    public void setPID(int PID) {
        this.PID = PID;
    }
}
