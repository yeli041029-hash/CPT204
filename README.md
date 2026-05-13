# CPT204 Group Project

## Project Overview

This project implements an **Urban Infrastructure Inspection System** for the CPT204 group coursework.
The system combines:

- **Task A: Sorting Algorithm**
- **Task B: Graph Algorithm**
- **Task C: Integrated Java Application**

In simple terms, the program first selects the most important inspection locations from three candidate datasets, and then calculates the shortest routes between selected locations in a city road network.

The current integrated version keeps the original Task A and Task B logic, while providing a cleaner Task C structure so the whole coursework can be run as one complete Java application.

## Coursework Context

According to the assignment brief, the project is based on four input files:

- `candidates_A.csv`
- `candidates_B.csv`
- `candidates_C.csv`
- `paths.csv`

The three candidate files contain inspection locations and their priority scores.
The `paths.csv` file represents an undirected weighted graph of the city network.

The overall goal is:

1. Rank the candidate locations using the required sorting rule.
2. Select the **Top 10** locations from each dataset.
3. Use those selected locations in shortest-path queries on the full graph.
4. Integrate everything into a clear object-oriented Java program.

## Main Features

### Task A: Sorting

The program implements three sorting algorithms:

- `Bubble Sort`
- `Quick Sort`
- `Merge Sort`

All sorting algorithms follow the same ranking rule:

1. Sort by `priority_score` in **descending** order.
2. If two locations have the same `priority_score`, sort by `location_id` in **ascending** order.

For each dataset, the program:

- reads the CSV file
- runs all three sorting algorithms
- records the average running time
- prints the Top 10 selected locations

### Task B: Shortest Path

The graph part uses **Dijkstra's Algorithm** on the full city network.

It supports the four required coursework cases:

1. `A1 -> A1`
2. `A1 -> A10`
3. `A1 -> B1` via `B5`
4. `A1 -> C1` via `B5` and `C5`

For cases with waypoints, the system uses segmented shortest-path computation and combines the partial paths.

### Task C: Integrated Application

Task C is represented by the integrated system design:

- one central controller class coordinates the workflow
- Task A results are reused by Task B
- the program can be run either as a **full system** or as **Task A only**

This makes the project easier to explain in the report and easier to demonstrate in the presentation.

## Project Structure

```text
CPT204/
├── data/
│   ├── candidates_A.csv
│   ├── candidates_B.csv
│   ├── candidates_C.csv
│   └── paths.csv
├── src/
│   ├── Main.java
│   ├── TaskAMain.java
│   ├── InspectionSystem.java
│   ├── InspectionCase.java
│   ├── Location.java
│   ├── CSVReader.java
│   ├── Sorter.java
│   ├── BubbleSort.java
│   ├── QuickSort.java
│   ├── MergeSort.java
│   ├── SortingResult.java
│   ├── Edge.java
│   └── Graph.java
├── Final_Console_Output_TaskA_TaskB.txt
├── TaskA_Final_Result_Table.csv
├── TaskA_Final_Result_Table.txt
└── README.md
```

## Core Class Design

### Task A classes

- `Location`
  - stores `locationId` and `priorityScore`
- `CSVReader`
  - reads candidate CSV files and converts rows into `Location` objects
- `Sorter`
  - defines the shared sorting interface and the common ranking rule
- `BubbleSort`, `QuickSort`, `MergeSort`
  - three sorting implementations
- `SortingResult`
  - stores dataset name, algorithm name, average time, and Top 10 results

### Task B classes

- `Edge`
  - represents one weighted connection in the graph
- `Graph`
  - stores the graph using an adjacency list
  - provides shortest-path and waypoint-based path queries

### Task C classes

- `InspectionCase`
  - represents one coursework path query case
- `InspectionSystem`
  - the central workflow controller
  - runs Task A
  - prepares final selected targets
  - runs Task B queries
- `Main`
  - full system entry
- `TaskAMain`
  - Task A only entry

## How To Run

### Important

Run the program from the **project root directory**.
Do not run it with `src/` as the working directory, otherwise the program may not find the files inside `data/`.

### Option 1: Run the full integrated system

This runs:

- Task A
- Task C integrated selection flow
- Task B shortest-path cases

```bash
cd /Users/apple/IDEA/CPT204
javac src/*.java
java -cp src Main
```

### Option 2: Run Task A only

This is useful when:

- testing sorting performance
- taking screenshots for Chapter 1
- generating Task A tables without Task B output

```bash
cd /Users/apple/IDEA/CPT204
javac src/*.java
java -cp src TaskAMain
```

### Running in IntelliJ IDEA

If you run the program in IntelliJ:

- choose `Main` for the full system
- choose `TaskAMain` for Task A only
- set the **Working directory** to the project root:

```text
/Users/apple/IDEA/CPT204
```

## Program Workflow

### Full system flow

1. Read `candidates_A.csv`, `candidates_B.csv`, and `candidates_C.csv`
2. Run Bubble Sort, Quick Sort, and Merge Sort on each dataset
3. Print average running times and Top 10 locations
4. Use the final selected Top 10 locations for integration
5. Read `paths.csv`
6. Build the graph
7. Execute the required shortest-path cases
8. Print the path and total cost for each case

### Task A only flow

1. Read the three candidate datasets
2. Apply all three sorting algorithms
3. Print the average times
4. Print the Top 10 selected locations

## Output Files

The project currently includes several result files:

- `TaskA_Final_Result_Table.csv`
  - summary table of Task A timing and Top 10 results
- `TaskA_Final_Result_Table.txt`
  - text version of the Task A result table
- `TaskA_Output_With_IDs.txt`
  - Task A output with location IDs
- `TaskA_Output_With_Scores.txt`
  - Task A output with scores
- `Final_Console_Output_TaskA_TaskB.txt`
  - final full console output of the integrated program

## Notes On Performance Results

When running Task A multiple times, the average time may change slightly between runs.
This is normal in Java and can be caused by:

- JIT compilation
- operating system scheduling
- memory allocation and garbage collection
- background applications running at the same time

For this reason, the project uses **average execution time** rather than relying on a single run.
Small timing fluctuations are expected, but the overall performance pattern should remain consistent.

## Algorithm Summary

### Sorting

- `Bubble Sort`
  - simple to understand
  - can perform well when the data is already nearly sorted
- `Quick Sort`
  - usually very fast on random-like input
  - can become slower on nearly sorted input when pivot choice is poor
- `Merge Sort`
  - generally stable
  - consistent `O(n log n)` behavior

### Graph

- `Dijkstra's Algorithm`
  - suitable for weighted graphs with non-negative edge weights
  - appropriate for the coursework shortest-path cases

## Design Strengths

This integrated version has several practical strengths:

- clear separation between sorting, graph, and integration logic
- shared ranking rule to avoid inconsistent sorting behavior
- reusable object-oriented structure for explanation in the report
- two program entry points for easier testing and demonstration
- direct support for the coursework cases without changing the core Task A and Task B logic

## Limitations

- file paths are currently based on the project root working directory
- execution times may vary slightly across runs
- the project is console-based and does not include a GUI
- CSV parsing assumes the provided coursework files follow the expected format

## Recommended Use In The Report

This repository can support the coursework report in the following way:

- **Chapter 1**
  - explain the three sorting algorithms
  - compare their running times across datasets A, B, and C
- **Chapter 2**
  - explain graph construction and Dijkstra's Algorithm
  - report the four shortest-path cases
- **Chapter 3**
  - explain how `InspectionSystem` integrates Task A and Task B
  - describe the object-oriented structure and data flow

## Final Remark

This project is designed not only to produce the required results, but also to present them in a way that is easy to explain in a coursework report, PPT, and video presentation.

If you only need sorting analysis, use `TaskAMain`.
If you want to demonstrate the whole integrated application, use `Main`.
