# CPT204 Group Project

## 项目概述 | Project Overview

本项目是 CPT204 小组课程作业的实现，主题是一个 **城市基础设施巡检系统（Urban Infrastructure Inspection System）**。  
This project is an implementation of the CPT204 group coursework. Its theme is an **Urban Infrastructure Inspection System**.

通俗来说，这个系统要先从三个候选地点数据集中选出最值得优先巡检的地点，再在城市道路网络中计算这些重要地点之间的最短路径。  
In simple terms, the system first selects the most important inspection locations from three candidate datasets, and then computes the shortest routes between those important locations in a city road network.

当前仓库中的版本已经把：

- `Task A` 排序算法
- `Task B` 图算法
- `Task C` 整体 Java 应用整合

组合成了一个可以直接运行的完整项目。  
The current version in this repository integrates:

- `Task A` Sorting Algorithm
- `Task B` Graph Algorithm
- `Task C` Integrated Java Application

into one complete runnable project.

## 课程任务背景 | Coursework Context

根据作业要求，项目基于以下四个输入文件：  
According to the coursework brief, the project is based on the following four input files:

- `data/candidates_A.csv`
- `data/candidates_B.csv`
- `data/candidates_C.csv`
- `data/paths.csv`

三个 `candidates` 文件包含候选巡检地点及其优先级分数。  
The three `candidates` files contain candidate inspection locations and their priority scores.

`paths.csv` 表示一个无向加权图，每一条记录代表城市中两个地点之间的路径以及对应成本或距离。  
`paths.csv` represents an undirected weighted graph, where each record describes a path between two locations and its cost or distance.

项目整体目标如下：  
The overall goals of the project are:

1. 按题目规定的规则对候选地点进行排序。  
   Rank candidate locations using the required rule.
2. 从每个数据集中选出 `Top 10` 地点。  
   Select the `Top 10` locations from each dataset.
3. 在完整道路网络中完成题目要求的最短路径查询。  
   Perform the required shortest-path queries on the full road network.
4. 使用清晰的面向对象设计将整个流程整合为一个 Java 程序。  
   Integrate the whole workflow into a clear object-oriented Java application.

## 核心功能 | Main Features

### Task A：排序算法 | Sorting Algorithm

本项目实现了三种排序算法：  
This project implements three sorting algorithms:

- `Bubble Sort`
- `Quick Sort`
- `Merge Sort`

三种算法都使用同一套排序规则：  
All three algorithms use the same ranking rule:

1. 按 `priority_score` **降序** 排列。  
   Sort by `priority_score` in **descending** order.
2. 如果分数相同，则按 `location_id` **升序** 排列。  
   If two scores are equal, sort by `location_id` in **ascending** order.

对于每个数据集，程序会：  
For each dataset, the program will:

- 读取 CSV 文件  
  read the CSV file
- 运行三种排序算法  
  run the three sorting algorithms
- 记录平均运行时间  
  record the average running time
- 输出前 10 个地点  
  print the Top 10 locations

### Task B：图算法 | Graph Algorithm

图部分使用 **Dijkstra 算法** 计算最短路径。  
The graph part uses **Dijkstra's Algorithm** to compute shortest paths.

程序支持课程任务要求的四个 case：  
The program supports the four required coursework cases:

1. `A1 -> A1`
2. `A1 -> A10`
3. `A1 -> B1` via `B5`
4. `A1 -> C1` via `B5` and `C5`

对于带中间点的情况，程序通过“分段最短路径 + 路径拼接”的方式完成。  
For cases with required waypoints, the program solves them by segmented shortest-path computation and path merging.

### Task C：整体系统整合 | Integrated Application

Task C 的重点不是发明新的算法，而是把 Task A 和 Task B 用清晰的 Java 结构整合起来。  
The focus of Task C is not inventing a new algorithm, but integrating Task A and Task B into a clear Java structure.

本项目当前版本具备以下特点：  
The current version has the following features:

- 使用一个中心控制类统一调度流程  
  one central controller class coordinates the workflow
- Task A 的输出会被 Task B 直接复用  
  Task A results are reused directly by Task B
- 支持“完整系统运行”和“只运行 Task A”两种入口  
  supports both a full-system entry and a Task-A-only entry

## 项目结构 | Project Structure

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

## 核心类说明 | Core Class Design

### Task A 相关类 | Task A Classes

- `Location`  
  保存地点编号和优先级分数。  
  Stores the location ID and priority score.

- `CSVReader`  
  负责读取候选地点 CSV 文件，并转换成 `Location` 对象列表。  
  Reads candidate CSV files and converts them into lists of `Location` objects.

- `Sorter`  
  定义统一的排序接口，并集中管理排序规则。  
  Defines the common sorting interface and centralizes the ranking rule.

- `BubbleSort` / `QuickSort` / `MergeSort`  
  三种具体排序算法实现。  
  Three concrete sorting implementations.

- `SortingResult`  
  保存一次排序实验的结果，例如数据集名称、算法名称、平均时间和 Top 10。  
  Stores the result of one sorting experiment, such as dataset name, algorithm name, average time, and Top 10.

### Task B 相关类 | Task B Classes

- `Edge`  
  表示图中的一条加权边。  
  Represents one weighted edge in the graph.

- `Graph`  
  使用邻接表存储图，并提供最短路径和带 waypoint 的路径查询。  
  Stores the graph using an adjacency list and provides shortest-path and waypoint-based path queries.

### Task C 相关类 | Task C Classes

- `InspectionCase`  
  表示一个课程要求中的路径查询 case。  
  Represents one coursework path query case.

- `InspectionSystem`  
  整个项目的核心控制器，负责执行 Task A、准备整合结果并运行 Task B。  
  The central controller of the whole project. It runs Task A, prepares integrated results, and executes Task B.

- `Main`  
  完整系统入口。  
  Full system entry point.

- `TaskAMain`  
  只运行 Task A 的入口。  
  Task-A-only entry point.

## 如何运行 | How To Run

### 重要说明 | Important Note

程序必须在**项目根目录**下运行。  
The program must be run from the **project root directory**.

不要把 `src/` 当作工作目录，否则程序可能找不到 `data/` 中的文件。  
Do not use `src/` as the working directory, otherwise the program may not find the files inside `data/`.

### 方式一：运行完整系统 | Option 1: Run the Full Integrated System

这个入口会依次运行：  
This entry will run:

- `Task A`
- `Task C`
- `Task B`

```bash
cd /Users/apple/IDEA/CPT204
javac src/*.java
java -cp src Main
```

### 方式二：只运行 Task A | Option 2: Run Task A Only

这个入口适合：  
This entry is useful for:

- 调试排序算法  
  testing sorting algorithms
- 截图 Chapter 1 结果  
  taking screenshots for Chapter 1
- 单独生成 Task A 输出  
  generating Task A output only

```bash
cd /Users/apple/IDEA/CPT204
javac src/*.java
java -cp src TaskAMain
```

### IntelliJ IDEA 运行方式 | Running in IntelliJ IDEA

如果在 IntelliJ 中运行：  
If you run the project in IntelliJ:

- 选择 `Main` 可以运行完整系统  
  choose `Main` to run the full system
- 选择 `TaskAMain` 可以只运行 Task A  
  choose `TaskAMain` to run Task A only
- `Working directory` 必须设置为：  
  the `Working directory` must be:

```text
/Users/apple/IDEA/CPT204
```

## 程序运行流程 | Program Workflow

### 完整系统流程 | Full System Flow

1. 读取三个候选数据集。  
   Read the three candidate datasets.
2. 在每个数据集上运行三种排序算法。  
   Run the three sorting algorithms on each dataset.
3. 输出平均运行时间和 Top 10 结果。  
   Print average running times and Top 10 results.
4. 选出最终用于系统整合的目标地点。  
   Select the final targets used by the integrated system.
5. 读取 `paths.csv` 并构建图。  
   Read `paths.csv` and build the graph.
6. 执行四个最短路径 case。  
   Execute the four shortest-path cases.
7. 输出每个 case 的路径和总成本。  
   Print the path and total cost for each case.

### Task A 单独流程 | Task A Only Flow

1. 读取三个候选数据集。  
   Read the three candidate datasets.
2. 对每个数据集应用三种排序算法。  
   Apply the three sorting algorithms to each dataset.
3. 输出平均运行时间。  
   Print the average running times.
4. 输出 Top 10 地点。  
   Print the Top 10 locations.

## 输出文件说明 | Output Files

项目中当前包含以下结果文件：  
The project currently includes the following output files:

- `TaskA_Final_Result_Table.csv`  
  Task A 结果总表 CSV 版本。  
  CSV summary table for Task A results.

- `TaskA_Final_Result_Table.txt`  
  Task A 结果总表文本版本。  
  Text version of the Task A summary table.

- `TaskA_Output_With_IDs.txt`  
  只包含地点编号的 Task A 输出。  
  Task A output containing location IDs only.

- `TaskA_Output_With_Scores.txt`  
  包含地点编号和分数的 Task A 输出。  
  Task A output containing both IDs and scores.

- `Final_Console_Output_TaskA_TaskB.txt`  
  完整整合版程序的控制台输出结果。  
  Full console output of the integrated program.

## 关于运行时间波动 | Notes on Timing Variations

如果你多次运行 Task A，平均时间出现轻微变化是正常的。  
If you run Task A multiple times, slight variations in average running time are normal.

常见原因包括：  
Common reasons include:

- Java 的 JIT 即时编译  
  Java JIT compilation
- 操作系统调度  
  operating system scheduling
- 内存分配与垃圾回收  
  memory allocation and garbage collection
- 后台程序占用资源  
  background applications using system resources

因此，本项目使用“平均时间”而不是单次时间来做比较。  
For this reason, the project uses average execution time rather than relying on a single run.

只要总体趋势稳定，轻微波动不会影响结论。  
As long as the overall trend remains stable, small fluctuations do not affect the final conclusion.

## 算法总结 | Algorithm Summary

### 排序算法 | Sorting Algorithms

- `Bubble Sort`  
  实现简单；当数据接近有序时可能表现不错。  
  Simple to implement; can perform well when the data is already nearly sorted.

- `Quick Sort`  
  在随机分布数据上通常很快；但在某些输入顺序下可能退化。  
  Usually very fast on random-like input; however, it can degrade on certain input orders.

- `Merge Sort`  
  整体更稳定，时间复杂度通常保持在 `O(n log n)`。  
  More stable overall, with time complexity generally staying at `O(n log n)`.

### 图算法 | Graph Algorithm

- `Dijkstra's Algorithm`  
  适用于非负权重图，符合本次课程任务的最短路径需求。  
  Suitable for graphs with non-negative weights and fits the coursework shortest-path requirements.

## 本项目的优点 | Design Strengths

- Task A、Task B、Task C 结构分离清晰。  
  Clear separation between Task A, Task B, and Task C.

- 排序规则集中在统一接口中，减少逻辑不一致的风险。  
  The sorting rule is centralized in one shared interface, reducing the risk of inconsistent logic.

- 增加了两个运行入口，便于测试和展示。  
  Two entry points are provided for easier testing and demonstration.

- 保留了原有 Task A 和 Task B 的核心逻辑，整合方式尽量最小侵入。  
  The original Task A and Task B core logic is preserved, with integration kept minimally invasive.

- 更适合在报告、PPT 和答辩中讲清楚系统流程。  
  The structure is easier to explain in the report, PPT, and presentation.

## 当前限制 | Limitations

- 文件路径依赖项目根目录作为工作目录。  
  File paths currently rely on the project root as the working directory.

- 运行时间会有轻微波动。  
  Running times may vary slightly across executions.

- 当前项目是控制台程序，不包含 GUI。  
  The current project is console-based and does not include a GUI.

- CSV 读取逻辑默认输入文件格式符合题目要求。  
  The CSV reader assumes the input files follow the expected coursework format.

## 报告撰写建议 | Recommended Use in the Report

- **Chapter 1**  
  解释三种排序算法，并比较它们在 A、B、C 三个数据集上的表现。  
  Explain the three sorting algorithms and compare their performance on datasets A, B, and C.

- **Chapter 2**  
  解释图结构设计、Dijkstra 算法以及四个 shortest-path cases。  
  Explain the graph structure, Dijkstra's Algorithm, and the four shortest-path cases.

- **Chapter 3**  
  重点说明 `InspectionSystem` 如何把 Task A 和 Task B 整合起来，并体现 OOP 设计。  
  Focus on how `InspectionSystem` integrates Task A and Task B and demonstrates object-oriented design.

## 最后说明 | Final Remark

这个项目不仅是为了“跑出结果”，也尽量让结果更容易被解释、展示和写进课程作业文档。  
This project is designed not only to produce the required results, but also to make those results easier to explain, demonstrate, and include in the coursework documentation.

如果你只需要排序实验，请运行 `TaskAMain`。  
If you only need the sorting experiment, run `TaskAMain`.

如果你需要展示完整整合系统，请运行 `Main`。  
If you want to demonstrate the full integrated system, run `Main`.
