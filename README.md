# Guaranteed scheduling
Guaranteed scheduling

## How to run

To run use similar configuration:

<img src="https://github.com/SergySanJj/GuaranteedScheduling/blob/master/readmefiles/config.png">

As commandline arguments pass path to config file.

## Changes in config structure

Each process has 2 configurable values through parameters: time after which process goes into IO block and time which is spent blocked

Example of config
```
// # of Process	
numprocess 10

// mean deivation
meandev 200

// standard deviation
standdev 100

// process    # I/O block after  # Time spent in block
process 100 200
process 10 32
process 20 62
process 150 100
process 50 120
process 400 10
process 450 20
process 10 100
process 10 100
process 1000 1000

// duration of the simulation in milliseconds
runtime 3500
```
## Changed output files

Output can be found at ```...GuaranteedScheduling\Scheduling\res```

#### Summary-Processes structure

Each 2 lines show tick: 
- 1st line selected currently process (if not changed - does not print again)
- 2nd line status after tick

Process: [process id] [process state] [cpu time needed for process to finish]
[process burst time] [total time process worked outside block] [scheduling ratio]

#### Summary-Results structure

Structure is fully described by output file

Example
```
Scheduling Type: Interactive
Scheduling Name: Guaranteed
Simulation Available Time: N
Simulation Run Time: N
Mean: N
Standard Deviation: 100
Process         CPU Time        Block after     Time in block   CPU Completed   CPU Blocked     Final state     Last ratio      
PID             N               N               N               N               N times         N               DOUBLE           
...

Total CPU Needed N
```

# Algorithm explanation

<img src="https://github.com/SergySanJj/GuaranteedScheduling/blob/master/readmefiles/algorithm.jpg">

